
var startNode = null;
var endNode = null;

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

    console.log('End node:', x, y);
}

function createObstacle(element, x, y) {
    element.setAttribute('data-isObstacle', 'true');
    element.classList.add('obstacle');
    element.style.backgroundColor = 'black';

    console.log('Obstacle node:', x, y);
}
function createPathNode(element, x, y) {
  element.setAttribute('data-isPath', 'true');
  element.classList.add('path');
  element.style.backgroundColor = 'yellow';

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
  // Get the selected node coordinates map
  var nodeMap = getSelectedNodeCoordinates();
  var algorithm = document.getElementById('algorithm-selector').value;
  console.log('Algorithm:', algorithm);
  // Create the request payload
  var payload = {
    start: nodeMap.get('start') || [],
    end: nodeMap.get('end') || [],
    obstacles: nodeMap.get('obstacles') || [],
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
  setTimeout(function() {
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
  }, 0);
}
