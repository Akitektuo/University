<?php

include_once "database/Database.php";

const CIPHER_ALGORITHM = "AES-128-CTR";
const KEY = "Recipes";
const IV = "FBSbO2fKrvAOGUkS";

function addHeaders()
{
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: GET,POST,PUT,DELETE,PATCH,OPTIONS");
    header("Access-Control-Allow-Headers: Access-Control-Allow-Origin,Access-Control-Allow-Headers,Token,Content-Type,X-Auth-Token");
//    header("Content-Type: application/json");
//    header("Access-Control-Allow-Headers: " .
//        "Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With");
}

function getHttpMethod()
{
    return $_SERVER['REQUEST_METHOD'];
}

function getBody()
{
    return json_decode(file_get_contents("php://input"));
}

function getToken(): string
{
    return decrypt($_SERVER["HTTP_TOKEN"]);
}

function getDatabase(): Database
{
    return $GLOBALS["database"];
}

function encrypt(string $string): string
{
    return openssl_encrypt($string, CIPHER_ALGORITHM, KEY, iv: IV);
}

function decrypt(string $string): string
{
    return openssl_decrypt($string, CIPHER_ALGORITHM, KEY, iv: IV);
}

function getQueryParam(string $paramKey): string|null
{
    if (isset($_GET[$paramKey])) {
        return $_GET[$paramKey];
    }
    return null;
}