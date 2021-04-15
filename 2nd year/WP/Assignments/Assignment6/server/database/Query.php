<?php

use JetBrains\PhpStorm\Pure;

include_once "utils.php";

#[Pure] function query(string $table): Query
{
    return new Query($table);
}

class Query
{
    public const COUNT_KEY = "COUNT(*)";

    public const FETCH_OBJECT = "object";
    public const FETCH_ARRAY = "array";
    public const FETCH_ONCE = "once";

    private string $table;
    private string $query = "";

    public function __construct(string $table)
    {
        $this->table = $table;
    }

    public function select(): Query
    {
        $this->query = "SELECT * FROM $this->table";

        return $this;
    }

    public function count(): Query
    {
        $this->query = "SELECT COUNT(*) FROM $this->table";

        return $this;
    }

    public function ifId(string $value): Query
    {
        return $this->by("id", $value);
    }

    public function by(string $column, string $value): Query
    {
        $formattedValue = $this->formatString($value);
        $this->query .= " WHERE $column = $formattedValue";

        return $this;
    }

    public function andBy(string $column, string $value): Query
    {
        $formattedValue = $this->formatString($value);
        $this->query .= " AND $column = $formattedValue";

        return $this;
    }

    public function insert(...$values): Query
    {
        $columns = [];
        $formattedValues = [];
        for ($i = 0; $i < count($values); $i += 2) {
            $columns[] = $values[$i];
            $formattedValues[] = $this->formatString($values[$i + 1]);
        }
        $concatenatedColumns = implode(", ", $columns);
        $concatenatedValues = implode(", ", $formattedValues);
        $this->query = "INSERT INTO $this->table($concatenatedColumns) VALUES ($concatenatedValues)";

        return $this;
    }

    public function update(...$values): Query
    {
        $columnWithValues = [];
        for ($i = 0; $i < count($values); $i += 2) {
            $columnWithValues[] = $values[$i] . "=" . $this->formatString($values[$i + 1]);
        }
        $concatenatedValues = implode(", ", $columnWithValues);
        $this->query = "UPDATE $this->table SET $concatenatedValues";

        return $this;
    }

    public function delete(): Query
    {
        $this->query = "DELETE FROM $this->table";

        return $this;
    }

    public function get(): string
    {
        return $this->query;
    }

    private function formatString(mixed $string): string
    {
        $escapedString = getDatabase()->escapeString($string);

        if (is_string($escapedString)) {
            return "'$escapedString'";
        }
        return $escapedString;
    }
}