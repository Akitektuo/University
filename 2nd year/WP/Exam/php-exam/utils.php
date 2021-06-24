<?php

include_once "database/Database.php";

function addHeaders()
{
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: GET,POST,PUT,DELETE,PATCH,OPTIONS");
    header("Access-Control-Allow-Headers: Access-Control-Allow-Origin,Access-Control-Allow-Headers,Token,Content-Type,X-Auth-Token");
}

function getHttpMethod()
{
    return $_SERVER['REQUEST_METHOD'];
}

function getBody()
{
    return json_decode(file_get_contents("php://input"));
}

function getDatabase(): Database
{
    return $GLOBALS["database"];
}

function getQueryParam(string $paramKey): string|null
{
    if (isset($_GET[$paramKey])) {
        return $_GET[$paramKey];
    }
    return null;
}