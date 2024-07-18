<?php
header('Content-Type: application/json');

// Database connection
$con = mysqli_connect('lesterintheclouds.com', 'enrolment_user', 'youHNNM_Wm=}', 'db_enrolment_app');
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

// Query to fetch data
$sql = "SELECT * FROM crud_table";
$result = mysqli_query($con, $sql);

// Check if there are results
if (mysqli_num_rows($result) > 0) {
    $data = array();
    while ($row = mysqli_fetch_assoc($result)) {
        // Add each row to the data array
        $data[] = $row['data']; // Assuming 'data' is the column you want to display
    }
    echo json_encode($data, JSON_PRETTY_PRINT); // Encode the array as JSON and output it
} else {
    echo "No results found";
}

mysqli_close($con); // Close the connection
?>
