
app.controller('FormController', function($scope,$http, $location) {
  $scope.schema=null;
  $scope.form=[];

   /*[
    "*",
    {
      type: "submit",
      title: "Save"
    }
  ];*/
  $scope.loadData= function(formName){
    $http.get(formName)
    .then(function onSuccess(response){
      $scope.schema=response.data.schema;
      $scope.form=response.data.form;
    },
    function onError(response){
        alert(response.status+"")
    }
    )
  };
  $scope.loadData("https://api.myjson.com/bins/lbzsc");
$scope.onSubmit = function(form) {
    // First we broadcast an event so all fields validate themselves
    $scope.$broadcast('schemaFormValidate');

    // Then we check if the form is valid
    if (form.$valid) {
	      alert("submitted");
    }
  }
  $scope.model = {};
  $scope.onSubmit = function(form) {
    // First we broadcast an event so all fields validate themselves
    $scope.$broadcast('schemaFormValidate');

    // Then we check if the form is valid
    if (form.$valid) {
      // ... do whatever you need to do with your data.
      alert("Success");
    }
  }
})
app.controller('DiagramCtrl',['$scope', 'fileUpload','$q' , function($scope,fileUpload,$http,$location) {
  $scope.schema=null;
  $scope.form=[];
$scope.workflow={ "class": "go.GraphLinksModel",
		  "linkFromPortIdProperty": "fromPort",
		  "linkToPortIdProperty": "toPort",
		  
		  "nodeDataArray": [ 

		{"key":-1, "category":"Start", "loc":"175 0", "text":"Start","config":{"className":"Main","name":"Start","file":null},"input":{"url":"https://api.myjson.com/bins/lbzsc"},"output":[]},
		{"key":1, "loc":"175 100", "text":"CSVReader","config":{"className":"CSVReader","name":"CSV Reader","file":null},"input":{"url":"https://api.myjson.com/bins/lbzsc"},"output":[]},
		{"key":2, "loc":"175 200", "text":"MongoMapper","config":{"className":"MongoMapper","name":"CSV Reader","file":null},"input":{"url":"https://api.myjson.com/bins/lbzsc"},"output":[]},
		{"key":3, "loc":"175 290", "text":"MongoDb","config":{"className":"MongoDb","name":"CSV Reader","file":null},"input":{"url":"https://api.myjson.com/bins/lbzsc"},"output":[]},
		{"key":-2, "category":"End", "loc":"175 407", "text":"Stop!","config":{"className":"Main","name":"End","file":null},"input":{"url":"https://api.myjson.com/bins/lbzsc"},"output":[]}
		 ],
		  "linkDataArray": [ 
		{"from":1, "to":2, "fromPort":"B", "toPort":"T", "points":[175,115.9357009887696,175,125.9357009887696,175,150.00000000000003,174.99999999999994,150.00000000000003,174.99999999999994,174.06429901123045,174.99999999999994,184.06429901123045]},
		{"from":2, "to":3, "fromPort":"B", "toPort":"T", "points":[174.99999999999994,215.9357009887695,174.99999999999994,225.9357009887695,174.99999999999994,245.00000000000003,174.99999999999986,245.00000000000003,174.99999999999986,264.06429901123056,174.99999999999986,274.06429901123056]},
		{"from":-1, "to":1, "fromPort":"B", "toPort":"T", "points":[175,24.267174476800964,175,34.26717447680096,175,54.165736744015746,175,54.165736744015746,175,74.06429901123053,175,84.06429901123053]},
		{"from":3, "to":-2, "fromPort":"B", "toPort":"T", "points":[174.99999999999986,305.9357009887696,174.99999999999986,315.9357009887696,174.99999999999986,340.19438522693724,175.0000000000001,340.19438522693724,175.0000000000001,364.45306946510493,175.0000000000001,374.45306946510493]}
		 ]};
$scope.json=JSON.stringify($scope.workflow, undefined, 4);
$scope.content=$scope.json;


	  // Show the diagram's model in JSON format that the user may edit
	  function save() {
	    $scope.workflow = $scope.myDiagram.model.toJson();
	    $scope.myDiagram.isModified = false;
	  }
	  function load() {
	    $scope.myDiagram.model = go.Model.fromJson($scope.workflow);
	  }
	  
	  // print the diagram by opening a new window holding SVG images of the diagram contents for each page
	  function printDiagram() {
	    var svgWindow = window.open();
	    if (!svgWindow) return;  // failure to open a new Window
	    var printSize = new go.Size(700, 960);
	    var bnds = $scope.myDiagram.documentBounds;
	    var x = bnds.x;
	    var y = bnds.y;
	    while (y < bnds.bottom) {
	      while (x < bnds.right) {
	        var svg = $scope.myDiagram.makeSVG({ scale: 1.0, position: new go.Point(x, y), size: printSize });
	        svgWindow.document.body.appendChild(svg);
	        x += printSize.width;
	      }
	      x = bnds.x;
	      y += printSize.height;
	    }
	    setTimeout(function() { svgWindow.print(); }, 1);
	  }
	  
/*------------------------------------------------------------------------
 * INIT FUNCTION WHICH Setups palate and handles all events related to workflow
 */	  
	  $scope.init=function() {
		    //if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
		    var GO = go.GraphObject.make;  // for conciseness in defining templates

		    $scope.myDiagram =
		      GO(go.Diagram, "myDiagramDiv",  // must name or refer to the DIV HTML element
		        {
		          initialContentAlignment: go.Spot.Center,
		          allowDrop: true,  // must be true to accept drops from the Palette
		          "LinkDrawn": showLinkLabel,  // this DiagramEvent listener is defined below
		          "LinkRelinked": showLinkLabel,
		          scrollsPageOnFocus: false,
		          "undoManager.isEnabled": true  // enable undo & redo
		        });

		    // when the document is modified, add a "*" to the title and enable the "Save" button
		    $scope.myDiagram.addDiagramListener("Modified", function(e) {
		      var button = document.getElementById("SaveButton");
		      if (button) button.disabled = !$scope.myDiagram.isModified;
		      var idx = document.title.indexOf("*");
		      if ($scope.myDiagram.isModified) {
		        if (idx < 0) document.title += "*";
		      } else {
		        if (idx >= 0) document.title = document.title.substr(0, idx);
		      }
		      //alert(JSON.stringify($scope.myDiagram.model.nodeDataArray[1].config));
		    });

		    //Listener when diagram component is changed
			$scope.myDiagram.addDiagramListener("ObjectDoubleClicked",function(e) {
				var part = e.subject.part;
		        if (!(part instanceof go.Link)) { //alert("Clicked on " + JSON.stringify(part.data));
		        $scope.content=part.data;
		      //  console.log($scope.content);
		        $('#myModal').modal('show');
		        }
		        }
		  );
		    // helper definitions for node templates

		    function nodeStyle() {
		      return [
		        // The Node.location comes from the "loc" property of the node data,
		        // converted by the Point.parse static method.
		        // If the Node.location is changed, it updates the "loc" property of the node data,
		        // converting back using the Point.stringify static method.
		        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
		        {
		          // the Node.location is at the center of each node
		          locationSpot: go.Spot.Center
		        }
		      ];
		    }

		    // Define a function for creating a "port" that is normally transparent.
		    // The "name" is used as the GraphObject.portId,
		    // the "align" is used to determine where to position the port relative to the body of the node,
		    // the "spot" is used to control how links connect with the port and whether the port
		    // stretches along the side of the node,
		    // and the boolean "output" and "input" arguments control whether the user can draw links from or to the port.
		    function makePort(name, align, spot, output, input) {
		      var horizontal = align.equals(go.Spot.Top) || align.equals(go.Spot.Bottom);
		      // the port is basically just a transparent rectangle that stretches along the side of the node,
		      // and becomes colored when the mouse passes over it
		      return GO(go.Shape,
		        {
		          fill: "transparent",  // changed to a color in the mouseEnter event handler
		          strokeWidth: 0,  // no stroke
		          width: horizontal ? NaN : 8,  // if not stretching horizontally, just 8 wide
		          height: !horizontal ? NaN : 8,  // if not stretching vertically, just 8 tall
		          alignment: align,  // align the port on the main Shape
		          stretch: (horizontal ? go.GraphObject.Horizontal : go.GraphObject.Vertical),
		          portId: name,  // declare this object to be a "port"
		          fromSpot: spot,  // declare where links may connect at this port
		          fromLinkable: output,  // declare whether the user may draw links from here
		          toSpot: spot,  // declare where links may connect at this port
		          toLinkable: input,  // declare whether the user may draw links to here
		          cursor: "pointer",  // show a different cursor to indicate potential link point
		          mouseEnter: function(e, port) {  // the PORT argument will be this Shape
		            if (!e.diagram.isReadOnly) port.fill = "rgba(255,0,255,0.5)";
		          },
		          mouseLeave: function(e, port) {
		            port.fill = "transparent";
		          }
		        });
		    }

		    function textStyle() {
		      return {
		        font: "bold 11pt Helvetica, Arial, sans-serif",
		        stroke: "whitesmoke"
		      }
		    }

		    // define the Node templates for regular nodes

		    $scope.myDiagram.nodeTemplateMap.add("",  // the default category
		      GO(go.Node, "Table", nodeStyle(),
		        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
		        GO(go.Panel, "Auto",
		          GO(go.Shape, "Rectangle",
		            { fill: "#00A9C9", strokeWidth: 0 },
		            new go.Binding("figure", "figure")),
		          GO(go.TextBlock, textStyle(),
		            {
		              margin: 8,
		              maxSize: new go.Size(160, NaN),
		              wrap: go.TextBlock.WrapFit,
		              editable: false
		            },
		            new go.Binding("text").makeTwoWay())
		        ),
		        // four named ports, one on each side:
		        makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
		        makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
		        makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
		        makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
		      ));

		    $scope.myDiagram.nodeTemplateMap.add("Conditional",
		      GO(go.Node, "Table", nodeStyle(),
		        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
		        GO(go.Panel, "Auto",
		          GO(go.Shape, "Diamond",
		            { fill: "#00A9C9", strokeWidth: 0 },
		            new go.Binding("figure", "figure")),
		          GO(go.TextBlock, textStyle(),
		            {
		              margin: 8,
		              maxSize: new go.Size(160, NaN),
		              wrap: go.TextBlock.WrapFit,
		              editable: true
		            },
		            new go.Binding("text").makeTwoWay())
		        ),
		        // four named ports, one on each side:
		        makePort("T", go.Spot.Top, go.Spot.Top, false, true),
		        makePort("L", go.Spot.Left, go.Spot.Left, true, true),
		        makePort("R", go.Spot.Right, go.Spot.Right, true, true),
		        makePort("B", go.Spot.Bottom, go.Spot.Bottom, true, false)
		      ));

		    $scope.myDiagram.nodeTemplateMap.add("Start",
		      GO(go.Node, "Table", nodeStyle(),
		        GO(go.Panel, "Auto",
		          GO(go.Shape, "Circle",
		            { minSize: new go.Size(40, 40), fill: "#79C900", strokeWidth: 0 }),
		          GO(go.TextBlock, "Start", textStyle(),
		            new go.Binding("text"))
		        ),
		        // three named ports, one on each side except the top, all output only:
		        makePort("L", go.Spot.Left, go.Spot.Left, true, false),
		        makePort("R", go.Spot.Right, go.Spot.Right, true, false),
		        makePort("B", go.Spot.Bottom, go.Spot.Bottom, true, false)
		      ));

		    $scope.myDiagram.nodeTemplateMap.add("End",
		      GO(go.Node, "Table", nodeStyle(),
		        GO(go.Panel, "Auto",
		          GO(go.Shape, "Circle",
		            { minSize: new go.Size(40, 40), fill: "#DC3C00", strokeWidth: 0 }),
		          GO(go.TextBlock, "End", textStyle(),
		            new go.Binding("text"))
		        ),
		        // three named ports, one on each side except the bottom, all input only:
		        makePort("T", go.Spot.Top, go.Spot.Top, false, true),
		        makePort("L", go.Spot.Left, go.Spot.Left, false, true),
		        makePort("R", go.Spot.Right, go.Spot.Right, false, true)
		      ));

		    $scope.myDiagram.nodeTemplateMap.add("Comment",
		      GO(go.Node, "Auto", nodeStyle(),
		        GO(go.Shape, "File",
		          { fill: "#EFFAB4", strokeWidth: 0 }),
		        GO(go.TextBlock, textStyle(),
		          {
		            margin: 5,
		            maxSize: new go.Size(200, NaN),
		            wrap: go.TextBlock.WrapFit,
		            textAlign: "center",
		            editable: true,
		            font: "bold 12pt Helvetica, Arial, sans-serif",
		            stroke: '#454545'
		          },
		          new go.Binding("text").makeTwoWay())
		        // no ports, because no links are allowed to connect with a comment
		      ));


		    // replace the default Link template in the linkTemplateMap
		    $scope.myDiagram.linkTemplate =
		      GO(go.Link,  // the whole link panel
		        {
		          routing: go.Link.AvoidsNodes,
		          curve: go.Link.JumpOver,
		          corner: 5, toShortLength: 4,
		          relinkableFrom: true,
		          relinkableTo: true,
		          reshapable: true,
		          resegmentable: true,
		          // mouse-overs subtly highlight links:
		          mouseEnter: function(e, link) { link.findObject("HIGHLIGHT").stroke = "rgba(30,144,255,0.2)"; },
		          mouseLeave: function(e, link) { link.findObject("HIGHLIGHT").stroke = "transparent"; }
		        },
		        new go.Binding("points").makeTwoWay(),
		        GO(go.Shape,  // the highlight shape, normally transparent
		          { isPanelMain: true, strokeWidth: 8, stroke: "transparent", name: "HIGHLIGHT" }),
		        GO(go.Shape,  // the link path shape
		          { isPanelMain: true, stroke: "gray", strokeWidth: 2 }),
		        GO(go.Shape,  // the arrowhead
		          { toArrow: "standard", strokeWidth: 0, fill: "gray"}),
		        GO(go.Panel, "Auto",  // the link label, normally not visible
		          { visible: false, name: "LABEL", segmentIndex: 2, segmentFraction: 0.5},
		          new go.Binding("visible", "visible").makeTwoWay(),
		          GO(go.Shape, "RoundedRectangle",  // the label shape
		            { fill: "#F8F8F8", strokeWidth: 0 }),
		          GO(go.TextBlock, "Yes",  // the label
		            {
		              textAlign: "center",
		              font: "10pt helvetica, arial, sans-serif",
		              stroke: "#333333",
		              editable: true
		            },
		            new go.Binding("text").makeTwoWay())
		        )
		      );

		    // Make link labels visible if coming out of a "conditional" node.
		    // This listener is called by the "LinkDrawn" and "LinkRelinked" DiagramEvents.
		    function showLinkLabel(e) {
		      var label = e.subject.findObject("LABEL");
		      if (label !== null) label.visible = (e.subject.fromNode.data.figure === "Diamond");
		    }

		    // temporary links used by LinkingTool and RelinkingTool are also orthogonal:
		    $scope.myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
		    $scope.myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;

		    load();  // load an initial diagram from some JSON text

		    // initialize the Palette that is on the left side of the page
		    myPalette =
		      GO(go.Palette, "myPaletteDiv",  // must name or refer to the DIV HTML element
		        {
		          scrollsPageOnFocus: false,
		          nodeTemplateMap: $scope.myDiagram.nodeTemplateMap,  // share the templates used by $scope.myDiagram
		          model: new go.GraphLinksModel([  // specify the contents of the Palette
		            { category: "Start", text: "Start" },
		            { text: "Step" },
		            { category: "Conditional", text: "???" },
		            { category: "End", text: "End" },
		            { category: "Comment", text: "Comment" }
		          ])
		        });
		  }; // end init

/**************************************************************************************
 * 	File Uploading
 * **********************************************************************************/
		  $scope.dataUpload = true;
		  $scope.errVisibility = false;
		  $scope.uploadFile = function(){
		      var file = $scope.myFile;
		      console.log('file is ' );
		      console.dir(file);
		       var uploadUrl = "/WorkflowManager/uploadfile";
		       console.log(fileUpload);
		        fileUpload.uploadFileToUrl(file, uploadUrl).then(function(result){
		        $scope.errors = fileUpload.getResponse();
		        console.log($scope.errors);
		        $scope.errVisibility = true;
		        }, function(error) {
		        alert('error');
		        })
		 };

}]);


app.filter('prettify', function () {
    
    function syntaxHighlight(json) {
    	//console.log(json);
        json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
            var cls = 'number';
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = 'key';
                } else {
                    cls = 'string';
                }
            } else if (/true|false/.test(match)) {
                cls = 'boolean';
            } else if (/null/.test(match)) {
                cls = 'null';
            }
            return '<span class="' + cls + '">' + match + '</span>';
        });
    }
    
    return syntaxHighlight;
});