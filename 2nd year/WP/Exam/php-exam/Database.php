<?php

$GLOBALS["database"] = new Database();

class Database
{
    private mysqli $connection;

    public const FETCH_OBJECT = "object";
    public const FETCH_ARRAY = "array";
    public const FETCH_ONCE = "once";

    function __construct()
    {
        $this->connection = mysqli_connect("localhost", "root", "", "exam");

        if (!$this->connection) {
            die("Connection failed!");
        }
    }

    function escapeString($string): string
    {
        return $this->connection->real_escape_string($string);
    }

    function executeFromString(string $query)
    {
        $this->connection->query($query);
    }

    function executeAndGetFromString(string $query, string $fetchAll = Database::FETCH_OBJECT): mixed
    {
        $result = $this->connection->query($query);

        switch ($fetchAll) {
            case Database::FETCH_OBJECT:
                return (object)$result->fetch_assoc();
            case Database::FETCH_ARRAY:
                return $result->fetch_all(MYSQLI_ASSOC);
            case Database::FETCH_ONCE:
                return $result->fetch_row()[0];
        }
    }

    function getLastId(): int
    {
        return mysqli_insert_id($this->connection);
    }
}