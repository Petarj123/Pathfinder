
var startNode = null;
var endNode = null;
var gridContainer = document.getElementById('grid-container');
var containerWidth = gridContainer.offsetWidth;
var containerHeight = gridContainer.offsetHeight;
var nodeSize = 20; // Change the desired node size as needed

// Calculate the number of rows and columns based on the container size and node size
var numOfRows = Math.floor(containerHeight / nodeSize);
var numOfCols = Math.floor(containerWidth / nodeSize);

function createDynamicGrid() {
  

  // Clear the grid container
  gridContainer.innerHTML = '';

  // Create the grid table dynamically
  var table = document.createElement('table');
  for (var row = 0; row < numOfRows; row++) {
    var tr = document.createElement('tr');
    for (var col = 0; col < numOfCols; col++) {
      var td = document.createElement('td');
      var div = document.createElement('div');
      div.className = 'grid-node';
      div.setAttribute('data-x', col);
      div.setAttribute('data-y', row);
      div.setAttribute('data-isStart', 'false');
      div.setAttribute('data-isEnd', 'false');
      div.setAttribute('data-isObstacle', 'false');
      div.setAttribute('data-isPath', 'false');
      div.style.width = nodeSize + 'px';
      div.style.height = nodeSize + 'px';
      div.addEventListener('click', function (event) {
        toggleNodes(this, event);
      });
      td.appendChild(div);
      tr.appendChild(td);
    }
    table.appendChild(tr);
  }
  gridContainer.appendChild(table);
}

// Call the function to create the grid initially
createDynamicGrid();

// Add an event listener to window resize to recreate the grid dynamically when the screen size changes
window.addEventListener('resize', createDynamicGrid);


function toggleNodes(element, event) {
    var x = parseInt(element.getAttribute('data-x'));
    var y = parseInt(element.getAttribute('data-y'));

    if (event.shiftKey && event.button === 0) { // Shift key + Left click
        createStartNode(element, x, y);
    } else if (event.ctrlKey && event.button === 0) { // Ctrl key + Left click
        createEndNode(element, x, y);
    } else if (event.button === 0) { // Left click
        createObstacle(element, x, y);
    } 
}

function createStartNode(element, x, y) {
    if (startNode) {
        // Reset the previous start node
        startNode.setAttribute('data-isStart', 'false');
        startNode.classList.remove('start');
        startNode.style.backgroundColor = '';
    }

    startNode = element;
    startNode.setAttribute('data-isStart', 'true');
    startNode.classList.add('start');
    startNode.style.backgroundColor = 'red';

    // Reset other attributes
    startNode.setAttribute('data-isEnd', 'false');
    startNode.setAttribute('data-isObstacle', 'false');
    startNode.setAttribute('data-isPath', 'false');

    console.log('Start node:', x, y);
}

function createEndNode(element, x, y) {
    if (endNode) {
        // Reset the previous end node
        endNode.setAttribute('data-isEnd', 'false');
        endNode.classList.remove('end');
        endNode.style.backgroundColor = '';
    }

    endNode = element;
    endNode.setAttribute('data-isEnd', 'true');
    endNode.classList.add('end');
    endNode.style.backgroundColor = 'green';

    // Reset other attributes
    endNode.setAttribute('data-isStart', 'false');
    endNode.setAttribute('data-isObstacle', 'false');
    endNode.setAttribute('data-isPath', 'false');

    console.log('End node:', x, y);
}

function createObstacle(element, x, y) {
    element.setAttribute('data-isObstacle', 'true');
    element.classList.add('obstacle');
    element.style.backgroundColor = 'black';

    // Reset other attributes
    element.setAttribute('data-isStart', 'false');
    element.setAttribute('data-isEnd', 'false');
    element.setAttribute('data-isPath', 'false');

    console.log('Obstacle node:', x, y);
}

function createPathNode(element, x, y) {
  const isStart = element.getAttribute('data-isStart');
  const isEnd = element.getAttribute('data-isEnd');

  if (!(isStart === 'true' || isEnd === 'true')) {
    element.setAttribute('data-isPath', 'true');
    element.classList.add('path');
    element.style.backgroundColor = 'yellow';
  }

  console.log('Path node:', x, y);
}


function getSelectedNodeCoordinates() {
  var nodeMap = new Map();

  var startCoordinates = [];
  var endCoordinates = [];
  var obstacleCoordinates = [];

  var startNodes = document.getElementsByClassName('start');
  var endNodes = document.getElementsByClassName('end');
  var obstacleNodes = document.getElementsByClassName('obstacle');

  for (var i = 0; i < startNodes.length; i++) {
    var node = startNodes[i];
    var x = parseInt(node.getAttribute('data-x'));
    var y = parseInt(node.getAttribute('data-y'));
    startCoordinates.push({ x: x, y: y });
  }

  for (var i = 0; i < endNodes.length; i++) {
    var node = endNodes[i];
    var x = parseInt(node.getAttribute('data-x'));
    var y = parseInt(node.getAttribute('data-y'));
    endCoordinates.push({ x: x, y: y });
  }

  for (var i = 0; i < obstacleNodes.length; i++) {
    var node = obstacleNodes[i];
    var x = parseInt(node.getAttribute('data-x'));
    var y = parseInt(node.getAttribute('data-y'));
    obstacleCoordinates.push({ x: x, y: y });
  }

  nodeMap.set('start', startCoordinates);
  nodeMap.set('end', endCoordinates);
  nodeMap.set('obstacles', obstacleCoordinates);

  return nodeMap;
}
// Global variable to store the current path
var pathNodes = [];

function visualize() {
  clearPath();
  // Get the selected node coordinates map
  var nodeMap = getSelectedNodeCoordinates();
  var algorithm = document.getElementById('algorithm-selector').value;
  console.log('Algorithm:', algorithm);
  // Create the request payload
  var payload = {
    start: nodeMap.get('start') || [],
    end: nodeMap.get('end') || [],
    obstacles: nodeMap.get('obstacles') || [],
    height: numOfRows,
    width: numOfCols,
  };

  // Convert the payload to a JSON string
  var jsonData = JSON.stringify(payload);

  // Send the data to the backend using fetch
  fetch('http://localhost:8080/pathfinder/' + algorithm, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: jsonData,
  })
    .then(function (response) {
      if (response.ok) {
        // Request successful, parse the response
        return response.json();
      } else {
        // Request failed, handle the error
        throw new Error('Visualization request failed');
      }
    })
    .then(function (path) {

      // Store the new path nodes
      pathNodes = path.map(function (coord) {
        var node = document.querySelector(
          '[data-x="' + coord.x + '"][data-y="' + coord.y + '"]'
        );
        createPathNode(node, coord.x, coord.y);
        return node;
      });

      console.log('Visualization request successful');
    })
    .catch(function (error) {
      // Request or parsing failed, handle the error
      console.error('Visualization request failed', error);
    });
}

function clearGrid() {
  // Reset start nodes
  var startNodes = document.getElementsByClassName('start');
  for (var i = 0; i < startNodes.length; i++) {
    var node = startNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isStart', 'false');
  }

  // Reset end nodes
  var endNodes = document.getElementsByClassName('end');
  for (var i = 0; i < endNodes.length; i++) {
    var node = endNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isEnd', 'false');
  }

  // Reset obstacle nodes
  var obstacleNodes = document.getElementsByClassName('obstacle');
  for (var i = 0; i < obstacleNodes.length; i++) {
    var node = obstacleNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isObstacle', 'false');
  }

  // Reset path nodes
  var pathNodes = document.getElementsByClassName('path');
  for (var i = 0; i < pathNodes.length; i++) {
    var node = pathNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isPath', 'false');
  }
}

function clearPath() {
  var pathNodes = document.getElementsByClassName('path');

  for (var i = 0; i < pathNodes.length; i++) {
    var node = pathNodes[i];
    var isStart = node.getAttribute('data-isStart');
    var isEnd = node.getAttribute('data-isEnd');
    var isObstacle = node.getAttribute('data-isObstacle');

    if (isStart !== 'true' && isEnd !== 'true' && isObstacle !== 'true') {
      // Reset the background color and data attribute only for non-start, non-end, and non-obstacle nodes
      node.style.backgroundColor = 'white';
      node.setAttribute('data-isPath', 'false');
    }
  }
}


function clearMaze(){
  var pathNodes = document.getElementsByClassName('path');
  var obstacleNodes = document.getElementsByClassName('obstacle');
  for(var i = 0; i < pathNodes.length; i++){
    var node = pathNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isPath', 'false');
  }
  for(var i = 0; i < obstacleNodes.length; i++){
    var node = obstacleNodes[i];
    node.style.backgroundColor = 'white';
    node.setAttribute('data-isObstacle', 'false');
  }
}
function generateMaze() {
  clearMaze();
  var startNode = document.querySelector('.start');
  var endNode = document.querySelector('.end');

  var requestBody = {
    startNode: startNode,
    endNode: endNode,
    height: numOfRows,
    width: numOfCols
  };

  fetch('http://localhost:8080/pathfinder/maze', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestBody),
  })
    .then(function (response) {
      if (response.ok) {
        // Request successful, parse the response
        return response.json();
      } else {
        // Request failed, handle the error
        throw new Error('Maze generation request failed');
      }
    })
    .then(function (mazeCoordinates) {
      // Create obstacles using the received coordinates
      createObstacles(mazeCoordinates);

      console.log('Maze generation request successful');
    })
    .catch(function (error) {
      // Request or parsing failed, handle the error
      console.error('Maze generation request failed', error);
    });
}

function createObstacles(mazeCoordinates) {
  var startNode = document.querySelector('.start');
  var endNode = document.querySelector('.end');

  for (var i = 0; i < mazeCoordinates.length; i++) {
    var coordinate = mazeCoordinates[i];
    var node = document.querySelector(
      '[data-x="' + coordinate.x + '"][data-y="' + coordinate.y + '"]'
    );
    
    if (node && !node.classList.contains('start') && !node.classList.contains('end')) {
      createObstacle(node, coordinate.x, coordinate.y);
    }
  }
}
var isMouseDown = false;

// Add event listener to the grid container for mouse events
document.getElementById('grid-container').addEventListener('mousedown', function(event) {
  if (event.button === 0) {
    // Left mouse button is pressed
    isMouseDown = true;
  }
});

document.addEventListener('mouseup', function(event) {
  if (event.button === 0) {
    // Left mouse button is released
    isMouseDown = false;
  }
});

document.getElementById('grid-container').addEventListener('mousemove', function(event) {
  if (isMouseDown) {
    // Get the node being crossed by the cursor
    var targetNode = event.target;

    if (targetNode.classList.contains('grid-node')) {
      // Create an obstacle on the target node
      createObstacle(targetNode, targetNode.dataset.x, targetNode.dataset.y);
    }
  }
});

