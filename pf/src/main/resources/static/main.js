var nodeCoordinates = [];
function toggleNodes(element, event) {
    var isStart = element.classList.contains('start');
    var isEnd = element.classList.contains('end');

    if (!isStart && !isEnd) {
        createObstacle(element, event);
    }

    if (!isStart) {
        createStartNode(element, event);
    }

    if (!isEnd) {
        createEndNode(element, event);
    }
}

function createStartNode(element, event) {
    if (event.shiftKey && event.button === 0) { // Shift key + Left click
        var startNode = document.querySelector('.start');
        if (startNode) {
            // Reset the previous start node
            startNode.classList.remove('start');
            startNode.style.backgroundColor = '';
        }

        // Get the Node object associated with the clicked element
        var node = JSON.parse(element.dataset.node);

        // Set the new start node
        node.isStart = true;
        element.classList.add('start');
        element.style.backgroundColor = 'red';

        console.log('Start node:', node);
    }
}

function createEndNode(element, event) {
    if (event.ctrlKey && event.button === 0) { // Ctrl key + Left click
        var endNode = document.querySelector('.end');
        if (endNode) {
            // Reset the previous end node
            endNode.classList.remove('end');
            endNode.style.backgroundColor = '';
        }

        // Get the Node object associated with the clicked element
        var node = JSON.parse(element.dataset.node);

        // Set the new end node
        node.isEnd = true;
        element.classList.add('end');
        element.style.backgroundColor = 'green';

        console.log('End node:', node);
    }
}
function createObstacle(element, event) {
    if (event.button === 0) { // Left click
        // Get the x and y values from the dataset attributes
         var x = parseInt(element.getAttribute('data-x'));
          var y = parseInt(element.getAttribute('data-y'));

            console.log('x:', x);
            console.log('y:', y);


        // Set the new obstacle node
        var node = { x: x, y: y, isObstacle: true };
        element.classList.add('obstacle');
        element.style.backgroundColor = 'black';

        console.log('Obstacle node:', x, y);
    }
}


function printNodeCoordinates() {
    console.log('Node Coordinates:');
    nodeCoordinates.forEach(function(coord) {
        console.log('x:', coord.x, 'y:', coord.y);
    });
}