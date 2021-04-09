<?php

use JetBrains\PhpStorm\Pure;

include_once "utils.php";

#[Pure] function query(string $table): Query
{
    return new Query($table);
}

class Query
{
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

    public function insert(...$values): Query
    {
        $mapped_values = array_map("formatString", $values);
        $concatenatedValues = implode(", ", $mapped_values);
        $this->query = "INSERT INTO $this->table VALUES ($concatenatedValues)";

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
        $escaped_string = getDatabase()->escapeString($string);

        if (is_string($escaped_string)) {
            return "'$escaped_string'";
        }
        return $escaped_string;
    }
}