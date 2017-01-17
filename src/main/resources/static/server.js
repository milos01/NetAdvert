/**
 * Created by milosandric on 12/12/2016.
 */
var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(3000);
users = {};

io.on('connection', function (socket) {
    socket.on('userLoad', function(data){
        users[data.email] = socket.id;
        console.log(users);
    });

    socket.on('notifyUserNewStaff', function (data) {
        console.log(data);
        // if(data.email in users){
            socket.broadcast.to(users["milossm94@hotmail.com"]).emit('newStaff', data);
        // }
    })
});



