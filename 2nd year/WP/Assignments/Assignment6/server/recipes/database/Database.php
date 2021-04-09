<?php

$GLOBALS["database"] = new Database();

class Database
{
    private mysqli $connection;

    public const TABLE_USERS = "users";
    public const TABLE_RECIPES = "recipes";
    public const TABLE_INGREDIENTS = "ingredients";
    public const TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    function __construct()
    {
        $this->connection = mysqli_connect("localhost", "root", "", "recipes");

        if (!$this->connection) {
            die("Connection failed!");
        }
    }

    function escapeString($string): string
    {
        return $this->connection->real_escape_string($string);
    }

    function execute(Query $query)
    {
        $this->connection->query($query->get());
    }

    function executeAndGet(Query $query): object
    {
        $result = $this->connection->query($query->get());

        return (object) $result->fetch_assoc();
    }
}