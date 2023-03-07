'use strict';

var background, game;
var gridSize = 50;
var flashingStat = 0;
var gameStart = false;
var gamePause = false;
var gameLevel = 1;

function createStaticCanvas(canvas) {
    let c = canvas.getContext("2d");

    let drawWall = function(startX, startY, endX, endY, color) {
        c.strokeStyle = color;
        c.lineWidth = 2;
        c.beginPath();
        c.moveTo(startX, startY);
        c.lineTo(endX, endY);
        c.stroke();
    };

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawWall,
        clear,
        dims: {height: canvas.height, width: canvas.width}
    }
}

function createDynamicCanvas(canvas) {
    let c = canvas.getContext("2d");

    let drawDot = function(x, y, radius, color, isLarge) {
        if (isLarge && flashingStat)
            return;
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);
        c.closePath();
        c.fill();
    };

    let drawFruit = function(x, y, size, type) {
        var img = fruitImages[type];
        c.drawImage(img, x - size / 2, y - size / 2, size, size);
    };

    let drawGhost = function(x, y, dirx, diry, faceDir, type, status) {
        var img;
        if (status == 0)
            img = ghostImages[type];
        else if (status == 1)
            img = darkBlueGhostImages[0];
        else if (status == 2)
            img = darkBlueGhostImages[flashingStat];
        else if (status == 3)
            img = eyesImages[0];
        c.save();
        c.translate(x, y);
        c.translate(gridSize / 2, gridSize / 2);
        if (status != 3) {
            if (faceDir == true) {
                c.scale(-1, 1);
            }
            if (diry > 0 && faceDir == false) {
                c.rotate(-Math.PI / 2);
            }
            else if (diry < 0 && faceDir == false) {
                c.rotate(Math.PI / 2);
            }
            else if (diry > 0 && faceDir == true) {
                c.rotate(-Math.PI / 2);
            }
            else if (diry < 0 && faceDir == true) {
                c.rotate(Math.PI / 2);
            }
        }
        c.drawImage(img, -gridSize / 2, -gridSize / 2, gridSize, gridSize);
        c.restore();
    };

    let drawCharacter = function(x, y, dirx, diry, faceDir) {
        var img = characterImages[flashingStat];
        c.save();
        c.translate(x, y);
        c.translate(gridSize / 2, gridSize / 2);
        if (faceDir == true) {
            c.scale(-1, 1);
        }
        if (diry > 0 && faceDir == false) {
            c.rotate(-Math.PI / 2);
        }
        else if (diry < 0 && faceDir == false) {
            c.rotate(Math.PI / 2);
        }
        else if (diry > 0 && faceDir == true) {
            c.rotate(-Math.PI / 2);
        }
        else if (diry < 0 && faceDir == true) {
            c.rotate(Math.PI / 2);
        }
        c.drawImage(img, -gridSize / 2, -gridSize / 2, gridSize, gridSize);
        c.restore();
    };

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawDot,
        drawFruit,
        drawGhost,
        drawCharacter,
        clear,
        dims: {height: canvas.height, width: canvas.width}
    }
}

function loadImage(src) {
    var img = new Image();
    img.src = src;
    return img;
}

function preloadImages() {
    window.characterImages = [loadImage('img/character0.png'), loadImage('img/character1.png')];
    window.ghostImages = [loadImage('img/ghost0.png'), loadImage('img/ghost1.png'), loadImage('img/ghost2.png'), loadImage('img/ghost3.png')];
    window.fruitImages = [loadImage('img/fruit0.png'), loadImage('img/fruit1.png'), loadImage('img/fruit2.png')];
    window.darkBlueGhostImages = [loadImage('img/darkblueghost0.png'), loadImage('img/darkblueghost1.png')];
    window.eyesImages = [loadImage('img/eyes.png')];
}

function drawBackgroundMap() {

    $.post("/initmap", {}, function (data) {
        data.forEach(function(po) {
            if (po.exit == true)
                background.drawWall(po.loc.x, po.loc.y, po.locEnd.x, po.locEnd.y,'tomato');
            else
                background.drawWall(po.loc.x, po.loc.y, po.locEnd.x, po.locEnd.y,'deepskyblue');
        });
    }, "json");
}

/**
 * Pass along the canvas dimensions
 */
function canvasDims() {
    $.get("/canvas/dims", function(data) {
        var canvas_static = document.getElementById("canvas-static");
        var canvas_dynamic = document.getElementById("canvas-dynamic");
        const obj = JSON.parse(data);
        canvas_static.width = obj.x;
        canvas_static.height = obj.y;
        canvas_dynamic.width = obj.x;
        canvas_dynamic.height = obj.y;
    });
}

function updateGameMap() {
    if (gamePause)
        return;

    $.get("/update", function(data) {
        game.clear();

        data.forEach(function(po) {
            if (po.name === "character")
                game.drawCharacter(po.loc.x, po.loc.y, po.vel.x, po.vel.y, po.faceDir);
            else if (po.name === "dot")
                game.drawDot(po.loc.x, po.loc.y, po.size, 'yellow', po.isLarge);
            else if (po.name === "fruit")
                game.drawFruit(po.loc.x, po.loc.y, po.size, po.type);
            else if (po.name === "ghost")
                game.drawGhost(po.loc.x, po.loc.y, po.vel.x, po.vel.y, po.faceDir, po.type, po.status);
        });
        if (gameStart) flashingStat = 1 - flashingStat; // alternate between 0 and 1
    }, "json");


    $.get("/getstatistics", function(data) {
        const obj = JSON.parse(data);
        document.getElementById("level").innerHTML = 'Level: ' + obj.level;
        document.getElementById("life").innerHTML = 'Life: ' + obj.life;
        document.getElementById("score").innerHTML = 'Score: ' + obj.score;
        if (obj.level == 4) {
            document.getElementById("level").innerHTML = 'Level: 3';
            window.alert("Congratulations");
            gamePause = true;
            return;
        }
        if (obj.level != gameLevel) {
            gameLevel = obj.level;
            canvasDims();
            preloadImages();
            drawBackgroundMap();
        }
        if (obj.life == 0) {
            window.alert("Game Over");
            gamePause = true;
            return;
        }
    });
}

function loadArrowKey() {
    $('body').keydown(e => {
        if (gameStart && !gamePause) {
            let key = parseInt(e.keyCode ? e.keyCode : e.which) - 37;
            var velx, vely;
            if (key >= 0 && key < 4) {
                switch (key) {
                    case 0:
                        velx = -gridSize / 2;
                        vely = 0;
                        break;
                    case 1:
                        velx = 0;
                        vely = -gridSize / 2;
                        break;
                    case 2:
                        velx = gridSize / 2;
                        vely = 0;
                        break;
                    case 3:
                        velx = 0;
                        vely = gridSize / 2;
                        break;
                    default:
                        break;
                }
                $.post("/switchvel", {velx: velx, vely: vely});
            }
        }
    });
}

window.onload = function() {
    background = createStaticCanvas(document.getElementById("canvas-static"));
    game = createDynamicCanvas(document.getElementById("canvas-dynamic"));
    canvasDims();
    preloadImages();
    drawBackgroundMap();
    setInterval(updateGameMap, 200);

    $('#btn-start').click(() => {
        $.post("/restart");
        gameStart = true;
        gamePause = false;
    });
    $('#btn-pause').click(() => {
        if (gameStart) {
            var btn = document.getElementById('btn-pause');
            if (!gamePause) {
                btn.className = 'btn btn-warning btn-lg';
                btn.innerHTML = 'Resume Game';
                gamePause = true;
            } else {
                btn.className = 'btn btn-danger btn-lg';
                btn.innerHTML = 'Pause Game';
                gamePause = false;
            }
        }
    });
    $('#level1').click(() => { $.post("/switchlevel/1"); });
    $('#level2').click(() => { $.post("/switchlevel/2"); });
    $('#level3').click(() => { $.post("/switchlevel/3"); });

    loadArrowKey();
};