<?php 

//define("API_KEY", "AIzaSyCTmst6SvsOAQanZKNt-2pt6nuLoFf2kSA");


//define("API_KEY", "AIzaSyDFhjkKdYs3f6ij_ktyRsFq8L8i89lMTbk");

define("API_KEY", "AIzaSyDCxT4SoE1Xtl475mkb7Up4Raqx-k3OrSY");


$type = "bar";

// $x1 = "48.132986";
// $y1 = "11.566126";

// $x2 = "48.142199";
// $y2 = "11.580047";


$x1 = "37.763954161155425";
$y1 = "-122.41661516952513";

$x2 = "37.75716107026401";
$y2 = "-122.42462522888184";


$command = escapeshellcmd('python3 /home/forge/default/public/populartimes-api/test.py ' . API_KEY . " $type $x1 $y1 $x2 $y2");

// echo($command);

$output = shell_exec($command . ' 2>&1');


echo $output;

/*
AIzaSyCTmst6SvsOAQanZKNt-2pt6nuLoFf2kSA", ["bar"],(48.132986, 11.566126), (48.142199, 11.580047
*/


?>