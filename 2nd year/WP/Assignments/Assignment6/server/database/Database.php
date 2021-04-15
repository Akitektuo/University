<?php

$GLOBALS["database"] = new Database();

class Database
{
    private mysqli $connection;

    public const TABLE_USERS = "users";
    public const TABLE_RECIPES = "recipes";
    public const TABLE_TYPES = "types";
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
        $this->executeFromString($query->get());
    }

    function executeFromString(string $query)
    {
        $this->connection->query($query);
    }

    function executeAndGet(Query $query, string $fetchAll = Query::FETCH_OBJECT): mixed
    {
        return $this->executeAndGetFromString($query->get(), $fetchAll);
    }

    function executeAndGetFromString(string $query, string $fetchAll = Query::FETCH_OBJECT): mixed
    {
        $result = $this->connection->query($query);

        switch ($fetchAll) {
            case Query::FETCH_OBJECT:
                return (object)$result->fetch_assoc();
            case Query::FETCH_ARRAY:
                return $result->fetch_all(MYSQLI_ASSOC);
            case Query::FETCH_ONCE:
                return $result->fetch_row()[0];
        }
    }

    function getLastId(): int
    {
        return mysqli_insert_id($this->connection);
    }
}