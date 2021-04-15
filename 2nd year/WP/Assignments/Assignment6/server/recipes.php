<?php
include_once "utils.php";
include_once "database/Query.php";

addHeaders();

switch (getHttpMethod()) {
    case "POST":
        handlePost();
        return;
    case "GET":
        handleGet();
        return;
    case "PUT":
        handlePut();
        return;
    case "DELETE":
        handleDelete();
        return;
}

function handlePost()
{
//    $token = getToken();
    $body = getBody();

    if (!$body || !isset($body->title) || !isset($body->description) || !isset($body->typeId) || !isset($body->author)) {
        http_response_code(400);
        die();
    }
    $database = getDatabase();
//
//    $isTokenValid = $database->executeAndGet(
//        query(Database::TABLE_USERS)
//            ->count()
//            ->ifId($token), Query::FETCH_ONCE);

    if (!$body->author || !$body->typeId || !$body->title) {
        http_response_code(401);
        die();
    }

    $database->execute(
        query(Database::TABLE_RECIPES)
            ->insert("typeId", $body->typeId,
                "title", $body->title,
                "author", $body->author,
                "description", $body->description));

//    $recipeId = $database->getLastId();

//    foreach ($body->ingredients as $ingredient) {
//        $isRegistered = $database->executeAndGet(
//            query(Database::TABLE_INGREDIENTS)
//                ->count()
//                ->by("label", $ingredient), Query::FETCH_ONCE);
//
//        if (!$isRegistered) {
//            $database->execute(
//                query(Database::TABLE_INGREDIENTS)
//                    ->insert("label", $ingredient));
//        }
//
//        $label = $database->executeAndGet(
//            query(Database::TABLE_INGREDIENTS)
//                ->select()
//                ->by("label", $ingredient));
//
//        $database->execute(
//            query(Database::TABLE_RECIPE_INGREDIENTS)
//                ->insert("recipeId", $recipeId,
//                    "ingredientId", $label->id));
//    }
}

function handleGet()
{
    $database = getDatabase();

    $typeId = getQueryParam("type");
    $id = getQueryParam("id");

    if (!$typeId && !$id) {
        http_response_code(401);
        die();
    }

    $query = "SELECT recipes.id, typeId, title, author, description, types.name as typeName"
        . " FROM recipes"
        . " JOIN types ON recipes.typeId = types.id";

    $query .= $typeId ? " WHERE typeId = $typeId" : " WHERE recipes.id = $id";

//    $recipes = $database->executeAndGetFromString(
//        "SELECT "
//        . "recipes.id, "
//        . "recipes.userId, "
//        . "recipes.title, "
//        . "recipes.description, "
//        . "ingredients.label "
//        . "FROM recipes "
//        . "JOIN recipe_ingredients ON recipes.id = recipe_ingredients.recipeId "
//        . "JOIN ingredients ON recipe_ingredients.ingredientId = ingredients.id", Query::FETCH_ARRAY);

    $recipes = $database->executeAndGetFromString($query, Query::FETCH_ARRAY);

//    $map = [];
//
//    foreach ($recipes as $recipe) {
//        $recipe = (object)$recipe;
//
//        if (array_key_exists($recipe->id, $map)) {
//            $map[$recipe->id]->labels[] = $recipe->label;
//            continue;
//        }
//
//        $recipeObject = new stdClass();
//
//        $recipeObject->id = $recipe->id;
//        $recipeObject->userId = $recipe->userId;
//        $recipeObject->title = $recipe->title;
//        $recipeObject->description = $recipe->description;
//        $recipeObject->labels = [$recipe->label];
//
//        $map[$recipe->id] = $recipeObject;
//    }

//    echo json_encode(array_values($map));
    echo json_encode($recipes);
}

function handlePut()
{
//    $token = getToken();
    $body = getBody();

    if (!$body ||
//        !$token ||
        !isset($body->id) ||
        !isset($body->title) ||
        !isset($body->description) ||
//        !isset($body->ingredients)
        !isset($body->typeId) ||
        !isset($body->author)) {
        http_response_code(400);
        die();
    }
    $database = getDatabase();

//    $isTokenValid = $database->executeAndGet(
//        query(Database::TABLE_RECIPES)
//            ->count()
//            ->ifId($body->id)
//            ->andBy("userId", $token), Query::FETCH_ONCE);

    $isTokenValid = $database->executeAndGet(
        query(Database::TABLE_RECIPES)
            ->count()
            ->ifId($body->id), Query::FETCH_ONCE);

    if (!$isTokenValid) {
        http_response_code(401);
        die();
    }

    $database->execute(
        query(Database::TABLE_RECIPES)
            ->update("title", $body->title,
                "description", $body->description,
                "typeId", $body->typeId,
                "author", $body->author)
            ->ifId($body->id));

//    $labels = [];
//    foreach ($body->ingredients as $ingredient) {
//        $escapedString = $database->escapeString($ingredient);
//        $labels[] = "'$escapedString'";
//    }
//    $concatenatedLabels = implode(", ", $labels);
//    $database->executeFromString(
//        "DELETE FROM recipes WHERE label NOT IN ($concatenatedLabels)");
//
//    foreach ($body->ingredients as $ingredient) {
//        $isRegistered = $database->executeAndGet("SELECT COUNT(*) FROM recipe_ingredients"
//            . " JOIN ingredients ON recipe_ingredients.ingredientId = ingredients.id"
//            . " WHERE recipeId = $body->id AND label = $ingredient", Query::FETCH_ONCE);
//
//        if (!$isRegistered) {
//            $database->execute(
//                query(Database::TABLE_INGREDIENTS)
//                    ->insert("label", $ingredient));
//        }
//
//        $label = $database->executeAndGet(
//            query(Database::TABLE_INGREDIENTS)
//                ->select()
//                ->by("label", $ingredient));
//
//        $database->execute(
//            query(Database::TABLE_RECIPE_INGREDIENTS)
//                ->insert("recipeId", $recipeId,
//                    "ingredientId", $label->id));
//    }
}

function handleDelete()
{
    $id = getQueryParam("id");

    if (!$id) {
        http_response_code(400);
        die();
    }
    $database = getDatabase();

    $isTokenValid = $database->executeAndGet(
        query(Database::TABLE_RECIPES)
            ->count()
            ->ifId($id), Query::FETCH_ONCE);

    if (!$isTokenValid) {
        http_response_code(401);
        die();
    }

    $database->execute(
        query(Database::TABLE_RECIPES)
            ->delete()
            ->ifId($id));
}