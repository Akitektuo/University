<?php
include_once "utils.php";
include_once "database/Query.php";

addHeaders();

$database = getDatabase();

$types = $database->executeAndGet(query(Database::TABLE_TYPES)->select(), Query::FETCH_ARRAY);

echo json_encode($types);