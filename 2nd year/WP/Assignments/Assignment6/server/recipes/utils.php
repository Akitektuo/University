<?php

use JetBrains\PhpStorm\Pure;

include_once "database/Database.php";

const CIPHER_ALGORITHM = "AES-128-CTR";
const KEY = "Recipes";
const IV = "FBSbO2fKrvAOGUkS";

function addHeaders(string $httpMethod)
{
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: $httpMethod");
    header("Access-Control-Allow-Headers: " .
        "Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With");
}

function getBody()
{
    return json_decode(file_get_contents("php://input"));
}

#[Pure] function getToken(): string
{
    return utf8_decode($_SERVER["HTTP_TOKEN"]);
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