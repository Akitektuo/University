<?php
include_once "utils.php";
include_once "database/Query.php";

addHeaders();

if (getHttpMethod() != "POST") {
    http_response_code(200);
    die();
}

$data = getBody();

if (!$data || !isset($data->username) || !isset($data->password) || !$data->username || !$data->password) {
    http_response_code(400);
    die();
}
$database = getDatabase();

$user = $database->executeAndGet(
    query(Database::TABLE_USERS)
        ->select()
        ->by("username", $data->username));

if (!isset($user->id)) {
    echo "user wrong";
    http_response_code(404);
    die();
}

if (!password_verify($data->password, $user->password)) {
    http_response_code(404);
    die();
}

echo encrypt($user->id);