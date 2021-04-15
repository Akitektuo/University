<?php
include_once "utils.php";
include_once "database/Query.php";

addHeaders();

if (getHttpMethod() != "POST") {
    http_response_code(400);
    die();
}

$data = getBody();
if (!$data || !isset($data->username) || !isset($data->password)) {
    http_response_code(400);
    die();
}
$database = getDatabase();

$usernameCount = $database->executeAndGet(
    query(Database::TABLE_USERS)
        ->count()
        ->by("username", $data->username), Query::FETCH_ONCE);

if ($usernameCount) {
    http_response_code(409);
    die();
}

$database->execute(
    query(Database::TABLE_USERS)
        ->insert("username", $data->username,
            "password", password_hash($data->password, PASSWORD_DEFAULT)));