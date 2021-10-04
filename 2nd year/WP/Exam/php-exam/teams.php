<?php
include_once "utils.php";

addHeaders();

switch (getHttpMethod()) {
    case "GET":
        handleGet();
        return;
}

function handleGet() {
    $userName = getQueryParam("userName");

    if ($userName) {
        handleGetForPlayer($userName);
    } else {
        handleGetAll();
    }
}

function handleGetAll() {
    $database = getDatabase();

    $query = "SELECT * FROM teams";

    $teams = $database->executeAndGetFromString($query, Database::FETCH_ARRAY);

    echo json_encode($teams);
}

function handleGetForPlayer($userName) {

}