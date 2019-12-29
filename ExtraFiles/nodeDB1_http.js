// filename: nodeDB1_http.js

var mysql = require('mysql');
var fs = require("fs");
var express = require('express')
var bodyParser = require('body-parser');

var app = express();
var port = 8000 ;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname));

var server = app.listen(port, () => {
    console.log('server is listening on port', server.address().port)
})

var connection = mysql.createPool({
        connectionLimit: 5,
        host     : 'localhost',
        user     : 'root',
        password : 'password',
        database : 'FinalProjectDB'
    });

app.get('/', function (req, res) {
    res.send("Hello");
});

 
app.post('/', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req.body.loginOrRegister);
    if(req.body.loginOrRegister=='login')
{
    let qString = "SELECT * from LoginInfo where UserName='" + req.body.UserName + "'"  
   //  let qString = "INSERT INTO LoginInfo VALUES('" + req.body.UserName + "','"+ req.body.Password + "','"+ req.body.FirstName + "','"+ req.body.LastName + "','"+ req.body.Role + "');"
  //  let qString = "SELECT * from HotelsInfo where Location ='Surrey'";
    console.log(req.body.UserName);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    
    });
}
else if(req.body.loginOrRegister=='register')
{
    
   let qString = "INSERT INTO LoginInfo VALUES('" + req.body.UserName + "','"+ req.body.Password + "','"+ req.body.FirstName + "','"+ req.body.LastName + "','"+ req.body.Role + "');"
  //  let qString = "SELECT * from HotelsInfo where Location ='Surrey'";
    console.log(req.body.UserName);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("New User Added!!");
        
        res.send(String('Success'));
    }
    else
        console.log('Error while performing Query.');
    });
} 
else  if(req.body.loginOrRegister=='tourist')
{
    let qString = "SELECT * from TouristAttractions where Location='" + req.body.Location + "'"  

    console.log(req.body.Location);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    
    });
}
else  if(req.body.loginOrRegister=='tourist_all')
{
    let qString = "SELECT * from TouristAttractions"  
    console.log(req.body.Location);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    
    });
}
 else  if(req.body.loginOrRegister=='TaxiService')
{
    // let qString = "SELECT * from TaxiDB"
     let qString = "SELECT * from TaxiDB WHERE SeatingCapacity>="+req.body.SeatingCapacity 
    console.log(req.body.SeatingCapacity);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    
    });
}  
 else  if(req.body.loginOrRegister=='EnterReservation')
{
    // let qString = "SELECT * from TaxiDB"
     let qString = "INSERT INTO TaxiReservationInfo VALUES('" + req.body.ID + "','"+ req.body.name + "','"+ req.body.passengers + "','"+ req.body.pickup + "','"+ req.body.address1+ "','"+ req.body.address2+ "','"+ req.body.taxi+ "','"+ req.body.priceperhour + "');"
    console.log(req.body.address1);
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Data entered successfully");
        
        res.send("Success");
    }
    else
        console.log('Error while performing Query.');
    
    });
}  
 })
