var stage;
var canvas;
var screePosition=[];
var soil_blocks = [];
var city;
var cursor;
var typesProd;
var mineType;
var country; 
var fireManPoliceArea = null;


var create = false;
var createWhat = "";

$( document ).ready(function() {
	init();
});


function initializeScreePosition() {
	max = 20;
	i = 0;
	j=0;	
	for(i=1;i<=max;i++) {
		for(j=1;j<=max;j++) {
			x_ = i * 32;
			y_= j * 32;
			screePosition.push({x:x_, y:y_});
		}
	}
}

function init() {
	
	initializeScreePosition();
	
	stage = new createjs.Stage("mainCanvas");
	stage.enableMouseOver(20);
	canvas = document.getElementById("mainCanvas");
  	createjs.Ticker.addEventListener("tick", tick);
  	
  	createCursor();
}

function showPoliceFiremanArea(x, y) {
	
	if(!fireManPoliceArea) {
		var g = new createjs.Graphics(); 
		g.setStrokeStyle(1); 
		g.beginStroke(createjs.Graphics.getRGB(255,211,255)); 
		g.beginFill(createjs.Graphics.getRGB(255,211,255, 0.5)); 
		g.drawCircle(0,0,96);
		
		fireManPoliceArea = new createjs.Shape(g);
		fireManPoliceArea.x = x + 16;
		fireManPoliceArea.y = y + 16;
		fireManPoliceArea.mouseEnabled = false;
		stage.addChild(fireManPoliceArea);
	} else {
		fireManPoliceArea.x = x + 16;
		fireManPoliceArea.y = y + 16;		
		stage.addChild(fireManPoliceArea);
	}	
}

function hidePoliceFiremanArea() {
	
	if(fireManPoliceArea)
	{
		stage.removeChild(fireManPoliceArea);
	}
}

function tick(event) {
	calculateCursorPosition(stage.mouseX, stage.mouseY);
	
	stage.update(event);	
}

function createCursor() {
	var g = new createjs.Graphics(); 
	g.setStrokeStyle(1); 
	g.beginStroke(createjs.Graphics.getRGB(0,0,0)); 
	g.beginFill(createjs.Graphics.getRGB(255,255,0)); 
	g.drawRect(0,0,32,32);
	
	cursor = new createjs.Shape(g);
	cursor.x = 0;
	cursor.y = 0;
    
	cursor.addEventListener("click", function (event) {
		if(create) {			
			sendCreateConstruction([event.target.y/32, event.target.x/32]);
			create = false;
			createWhat = "";
			adjustCursor(32,32,[255,255,0]);
		}		
    });	
	
    stage.addChild(cursor);
}

function adjustCursor(w,h, color) {
	var g = new createjs.Graphics(); 
	g.setStrokeStyle(1); 
	g.beginStroke(createjs.Graphics.getRGB(0,0,0)); 
	g.beginFill(createjs.Graphics.getRGB(color[0],color[1],color[2])); 
	g.drawRect(0,0,w,h);
	
    cursor.set({graphics:g});    
}

function calculateCursorPosition(x, y) {
	
	var x_ = Math.floor(x/32)*32;
	var y_ = Math.floor(y/32)*32;
	
	cursor.x = x_;
	cursor.y = y_;
}

function createBlank(x, y, t) {	
	
	var px = x*32;
	var py = y*32;
	var color = [255,255,255];
	
	if(t == "v") {
		color = [190,200,183];
	} else if(t == "c") {
		color = [255,200,183];
	} else if(t == "r") {
		color = [145,124,111];
	} else if(t == "s") {
		color = [255,246,213];
	} else if(t == "w") {
		color = [0,102,255];
	}
	
	
	var g = new createjs.Graphics(); 
	g.setStrokeStyle(1); 
	g.beginStroke(createjs.Graphics.getRGB(color[0],color[1],color[2])); 
	g.beginFill(createjs.Graphics.getRGB(color[0],color[1],color[2])); 
	g.drawRect(0,0,32,32);
	
	var s = new createjs.Shape(g);
    
	s.x = px;
    s.y = py;
    
	s.addEventListener("rollover", function (event) {
    });		
	s.addEventListener("rollout", function (event) {
    });
	s.addEventListener("click", function (event) {
    });
	
	s.set({
		col: x,
		row: y
	});
	
  	stage.addChild(s);
  	
  	if(!soil_blocks[y])
  		soil_blocks[y] = [];

  	soil_blocks[y].push(s);
}

function showCityState() {
	document.getElementById("txtMaxPopulation").value = city.maxPopulation;      		
	document.getElementById("txtCPopulation").value = city.population;      		
	document.getElementById("txtContribution").value = city.treasureContribution;
	document.getElementById("txtExpenses").value = city.treasureExpenses;
	document.getElementById("txtCUnemployed").value = city.uneployed;		    
	document.getElementById("txtCJobs").value = city.jobs;
	document.getElementById("txtCEntertainment").value = city.ceramics;
    document.getElementById("txtCWine").value = city.wine;
    document.getElementById("txtCFurniture").value = city.furniture;
    document.getElementById("txtCMeat").value = city.meat;
    document.getElementById("txtCBeans").value = city.beans;
    document.getElementById("txtCRice").value = city.rice;
    document.getElementById("txtCCloths").value = city.cloths;
    document.getElementById("txtDEntertainment").value = city.demandEntertainment;
    document.getElementById("txtDWine").value = city.demandWine;
    document.getElementById("txtDFurniture").value = city.demandFurniture;
    document.getElementById("txtDMeat").value = city.demandMeat;
    document.getElementById("txtDBeans").value = city.demandBeans;
    document.getElementById("txtDRice").value = city.demandRice;
    document.getElementById("txtDCloths").value = city.demandCloths;
}

function showCountryState() {
	document.getElementById("txtGMaxPopulation").value = country.maxPopulation;      		
	document.getElementById("txtGPopulation").value = country.population;      		
	document.getElementById("txtGTreasure").value = country.treasure;
	document.getElementById("txtGExpenses").value = country.totalExpenses;
	//document.getElementById("txtGUnemployed").value = country.uneployed;		    
	//document.getElementById("txtGJobs").value = country.jobs;
	document.getElementById("txtGEntertainment").value = country.ceramics;
    document.getElementById("txtGWine").value = country.wine;
    document.getElementById("txtGFurniture").value = country.furniture;
    document.getElementById("txtGMeat").value = country.meat;
    document.getElementById("txtGBeans").value = country.beans;
    document.getElementById("txtGRice").value = country.rice;
    document.getElementById("txtGCloths").value = country.cloths;
    
    document.getElementById("txtGIron").value = country.iron;
    document.getElementById("txtGSulfur").value = country.sulfur;
    document.getElementById("txtGRock").value = country.rock;
    document.getElementById("txtGCotton").value = country.cotton;
}

function showEstadoConstruction(event) {
		var constru = null;
		hidePoliceFiremanArea();
	
		if(event.target.construction_type == "House") {
		try {
				constru = city.constructions.houses[event.target.construction_index];
			} catch (e) {
			}
		} else 
		if (event.target.construction_type == "Farm") {
			try {
				constru = city.constructions.farms[event.target.construction_index];
			} catch (e) {
			}
		}else
		if (event.target.construction_type == "PolicePost") {
			try {
				showPoliceFiremanArea(event.target.x, event.target.y);
				constru = city.constructions.policePosts[event.target.construction_index];
			} catch (e) {
			}
		} else
		if (event.target.construction_type == "FiremanPost") {
			try {
				showPoliceFiremanArea(event.target.x, event.target.y);
				constru = city.constructions.firemanPosts[event.target.construction_index];
			} catch (e) {
			}
		} else
		if (event.target.construction_type == "Cloths") {
			try {
				constru = city.constructions.cloths[event.target.construction_index];
			} catch (e) {
			}
		}
		 else
		if (event.target.construction_type == "Mine") {
			try {
				constru = city.constructions.mines[event.target.construction_index];
			} catch (e) {
			}
		}
		 else
		if (event.target.construction_type == "Furniture") {
			try {
				constru = city.constructions.funitures[event.target.construction_index];
			} catch (e) {
			}
		}
		 else
		if (event.target.construction_type == "LumberJack") {
			try {
				constru = city.constructions.lumbers[event.target.construction_index];
			} catch (e) {
			}
		}
		else
		if (event.target.construction_type == "WineCellar") {
			try {
				constru = city.constructions.wineCellars[event.target.construction_index];
			} catch (e) {
			}
		}
		else
		if (event.target.construction_type == "Market") {
			try {
				constru = city.constructions.markets[event.target.construction_index];
			} catch (e) {
			}
		}
		else
		if (event.target.construction_type == "Barracks") {
			try {
				constru = city.constructions.barracks;
			} catch (e) {
			}
		}
		
		/*document.getElementById("txtType").value = constru.type;
		document.getElementById("txtOwnerType").value = constru.ownerType;
		document.getElementById("txtJobs").value = constru.avaliableJobs;
		document.getElementById("txtEmployed").value = constru.employed.length;
		document.getElementById("txtConservation").value = constru.conservation;
		document.getElementById("txtBuilt").value = constru.built;
		document.getElementById("txtClass").value = constru.familyClass;
		document.getElementById("txtSavings").value = constru.savings;
		document.getElementById("txtCost").value = constru.generalCost + constru.conservationCost;

		document.getElementById("houseDiv").style.display = "none";
		document.getElementById("farmDiv").style.display = "none";
		if(constru.type == "House") {
			
			document.getElementById("txtEntertainment").value = constru.ceramics;
			document.getElementById("txtWine").value = constru.wine;
			document.getElementById("txtFurniture").value = constru.furniture;
			document.getElementById("txtMeat").value = constru.meat;
			document.getElementById("txtBeans").value = constru.beans;
			document.getElementById("txtRice").value = constru.rice;
			document.getElementById("txtCloths").value = constru.cloths;
			document.getElementById("txtCitizenActive").value = constru.citizens.length;
			document.getElementById("txtCitizenChild").value = constru.citizenChild;
			document.getElementById("txtWithaJob").value = constru.withaJob;
			document.getElementById("txtSatisfaction").value = constru.satisfaction;
			document.getElementById("houseDiv").style.display = "block";
			
		} else if(constru.type == "PolicePost") {
			
		} else if(constru.type == "FiremanPost") {
			
		} else if(constru.type == "Farm") {
			
			document.getElementById("txtProduced").value = constru.produced;
			document.getElementById("txtMaxProduction").value = constru.production;
			document.getElementById("txtTypeProduction").value = constru.typeProduction;
			
		    document.getElementById("farmDiv").style.display = "block";
		    
		} else if(constru.type == "Cloths") {
			
			document.getElementById("txtProduced").value = constru.produced;
			document.getElementById("txtMaxProduction").value = constru.production;
			document.getElementById("txtTypeProduction").value = constru.typeProduction;
			
		    document.getElementById("farmDiv").style.display = "block";
		}*/
		//constru = city.constructions.houses[event.target.construction_index];
		var pan = document.getElementById("pane1");
		pan.innerHTML = "";
		
		var ulpan = document.createElement("ul");
		pan.appendChild(ulpan);
		
		var liitem;
		for(fd in constru) {
			liitem = document.createElement("li");
			liitem.innerHTML = fd + " = " + JSON.stringify(constru[fd]); 
			ulpan.appendChild(liitem);
		}
		
	}

	function createShape(constru, index, color1, color2, onclick, w, h) {

		var w_ = w;
		var h_ = h;
		
		if(!w) {
			w_ = 32;
			h_ = 32;
		}
		
		var building = [255,255,0]
		var g = new createjs.Graphics();
		g.setStrokeStyle(1);
		g.beginStroke(createjs.Graphics.getRGB(0, 0, 0));
		
		if(constru.built < 100) {
			color1 = building;
		}
		
		g.beginFill(createjs.Graphics.getRGB(color1[0], color1[1], color1[2]));
		g.drawRect(0, 0, w_, h_);

		var s = new createjs.Shape(g);
		s.x = px;
		s.y = py;
		s.addEventListener("rollover", function(event) {
			var g = new createjs.Graphics();
			g.setStrokeStyle(1);
			g.beginStroke(createjs.Graphics.getRGB(0, 0, 0));
			g.beginFill(createjs.Graphics.getRGB(color2[0], color2[1],
					color2[2]));
			g.drawRect(0, 0, w_, h_);

			event.target.set({
				graphics : g
			});
		});
		s.addEventListener("rollout", function(event) {
			var g = new createjs.Graphics();
			g.setStrokeStyle(1);
			g.beginStroke(createjs.Graphics.getRGB(0, 0, 0));
			g.beginFill(createjs.Graphics.getRGB(color1[0], color1[1], color1[2]));
			g.drawRect(0, 0, w_, h_);

			event.target.set({
				graphics : g
			});
		});

		s.addEventListener("click", function(event) {
			onclick(event)
		});

		s.set({
			construction_index : index,
			construction_type: constru.type
		});

		return s;
	}

	function createGenericItem(data, index, color, w, h) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		
		var text = new createjs.Text(data.type, "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, w, h);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}	
	
	function createMine(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 113, 113, 113 ];

		if(data.mineType == "clay") {
			color = [ 219,147,112 ];
		} else if(data.mineType == "sulfur") {
			color = [ 181,165,66 ];
		} else if(data.mineType == "rock") {
			color = [ 230,232,250 ];
		} 
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		
		var text = new createjs.Text(data.mineType, "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}

	function createBank(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 13, 255, 113 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Bank", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}	
	
	function createMarket(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 113, 255, 113 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Market", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createSchool(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 113, 155, 213 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("School", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createUniversity(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 113, 155, 213 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Univesity", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 96, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createMansion(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 255, 213, 213 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Mansion", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createPalace(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 219,219,112 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Palace", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 96, 96);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}	
	
	function createBarracks(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 153,204,50 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Barracks", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 96, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}	
	
	function createFurniture(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 233,194,166 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Furn", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createWine(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 147,112,219 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Wine", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}
	
	function createLumber(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 165,128,100 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("Lumber", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}	
	
	function createHouse(data, index) {

		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 213, 255, 213 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text(data.familyClass, "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 255, 159, 159 ], showEstadoConstruction);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);
	}

	function createRoad(data, index) {
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;

		var g = new createjs.Graphics();
		g.setStrokeStyle(1);
		g.beginStroke(createjs.Graphics.getRGB(255, 204, 102));
		g.beginFill(createjs.Graphics.getRGB(255, 204, 102));
		g.drawRect(0, 0, 32, 32);

		var s = new createjs.Shape(g);
		s.x = px;
		s.y = py;
		s.addEventListener("rollover", function(event) {
			var g = new createjs.Graphics();
			g.setStrokeStyle(1);
			g.beginStroke(createjs.Graphics.getRGB(0, 0, 0));
			g.beginFill(createjs.Graphics.getRGB(255, 231, 182));
			g.drawRect(0, 0, 32, 32);

			event.target.set({
				graphics : g
			});
		});
		s.addEventListener("rollout", function(event) {
			var g = new createjs.Graphics();
			g.setStrokeStyle(1);
			g.beginStroke(createjs.Graphics.getRGB(255, 204, 102));
			g.beginFill(createjs.Graphics.getRGB(255, 204, 102));
			g.drawRect(0, 0, 32, 32);

			event.target.set({
				graphics : g
			});
		});

		stage.addChild(s);
	}

	function createPolice(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 91, 113, 255 ];
		
		if(data.conservation < 50) {
			color = [ 91, 113, 255 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("PO", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 169, 181, 255 ], showEstadoConstruction);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);		
	}

	function createFireMan(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 230, 255, 0 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text("FM", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 230, 255, 255 ], showEstadoConstruction, 32, 32);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);		
	}

	function createFarm(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 36, 182, 0 ];
		var name = "Farm ";
		
		if(data.typeProduction == "meat") {
			color = [ 255, 182, 0 ];
			name = name + " Meat";
		} else if(data.typeProduction == "beans") {
			color = [ 95, 211, 95 ];
			name = name + " Beans";
		} else if(data.typeProduction == "cotton") {
			color = [ 255, 255, 255];
			name = name + " Cotton";
		} else if(data.typeProduction == "grapes") {
			color = [ 155, 200, 0];
			name = name + " Grapes";
		} else {
			name = name + " Rice";
		}
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 249, 249, 249 ];
			}
		}
		
		var text = new createjs.Text(name, "10px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 169, 181, 255 ], showEstadoConstruction, 64, 96);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);			
	}

	function createCloths(data, index) {
		
		px = data.mapSlots[0].x * 32;
		py = data.mapSlots[0].y * 32;
		var color = [ 255, 85, 85 ];
		
		if(data.conservation < 50) {
			color = [ 255, 0, 0 ];
		} else if(data.conservation <= 0) {
			if(!data.cleaned) {
				color = [ 0, 0, 0 ];
			} else {
				color = [ 255, 51, 204 ];
			}
		}
		
		var text = new createjs.Text("Cloths", "16px Arial", "#000000");
		text.x = px;
		text.y = py;		
		
		var s = createShape(data, index, color, [ 80, 80, 80 ], showEstadoConstruction, 64, 64);
		s.x = px;
		s.y = py;
		stage.addChild(s);
		stage.addChild(text);			
	}	
	
	function processMap() {
		
		showCityState();
		
		soil_blocks = [];
		stage.removeAllChildren();		
		
		var i = 0;
		var j = 0;

		for(i = 0; i < city.map.soil.length; i++) {
			for(j = 0; j < city.map.soil[i].length; j++) {				
				createBlank(j, i, city.map.soil[i][j]);
			}
		}
		
		createCursor();

		for (i = 0; i < city.constructions.roads.length; i++) {
			createRoad(city.constructions.roads[i], i);
		}

		for (i = 0; i < city.constructions.houses.length; i++) {
			createHouse(city.constructions.houses[i], i);
		}

		for (i = 0; i < city.constructions.farms.length; i++) {
			createFarm(city.constructions.farms[i], i);
		}
		
		for (i = 0; i < city.constructions.cloths.length; i++) {
			createCloths(city.constructions.cloths[i], i);
		}

		for (i = 0; i < city.constructions.firemanPosts.length; i++) {
			createFireMan(city.constructions.firemanPosts[i], i);
		}

		for (i = 0; i < city.constructions.policePosts.length; i++) {
			createPolice(city.constructions.policePosts[i], i);
		}
		
		for (i = 0; i < city.constructions.furnitures.length; i++) {
			createFurniture(city.constructions.furnitures[i], i);
		}
		
		for (i = 0; i < city.constructions.mines.length; i++) {
			createMine(city.constructions.mines[i], i);
		}
		
		for (i = 0; i < city.constructions.wineCellars.length; i++) {
			createWine(city.constructions.wineCellars[i], i);
		}
		
		for (i = 0; i < city.constructions.lumbers.length; i++) {
			createLumber(city.constructions.lumbers[i], i);
		}
		
		for (i = 0; i < city.constructions.markets.length; i++) {
			createMarket(city.constructions.markets[i], i);
		}
		
		for (i = 0; i < city.constructions.banks.length; i++) {
			createBank(city.constructions.banks[i], i);
		}
		
		for (i = 0; i < city.constructions.universities.length; i++) {
			createUniversity(city.constructions.universities[i], i);
		}
		
		for (i = 0; i < city.constructions.mansions.length; i++) {
			createMansion(city.constructions.mansions[i], i);
		}		
		
		for (i = 0; i < city.constructions.schools.length; i++) {
			createSchool(city.constructions.schools[i], i);
		}		
		
		if(city.constructions.shipyard) {
			createGenericItem(city.constructions.shipyard, 0, [30,230,30], 64, 64);
		}
		if(city.constructions.infirmary) {
			createGenericItem(city.constructions.infirmary, 0, [30,230,30], 64, 64);
			showInfirmaryBtn(city.constructions.infirmary);
		}
		if(city.constructions.machineryFactory) {
			createGenericItem(city.constructions.machineryFactory, 0, [30,230,30], 64, 64);
			showCannonBtn(city.constructions.machineryFactory);
			showCatapultBtn(city.constructions.machineryFactory);
		}
		if(city.constructions.archerCamp) {
			createGenericItem(city.constructions.archerCamp, 0, [30,230,30], 64, 64);
			showArcherBtn(city.constructions.archerCamp);			
		}
		
		if(city.constructions.knightsCamp) {
			createGenericItem(city.constructions.knightsCamp, 0, [30,230,30], 64, 64);
			showKnightBtn(city.constructions.knightsCamp);
		}
		if(city.constructions.airport) {
			createGenericItem(city.constructions.airport, 0, [130,230,50], 96, 96);
		}
		
		if(city.constructions.barracks) {
			createBarracks(city.constructions.barracks);
			showBarracksButtons(city.constructions.barracks);
		}
		
		if(city.constructions.palace)
			createPalace(city.constructions.palace);
				
	}

	function requestMap() {
		showmap = {
			name : "showmap"
		}

		ws.send(JSON.stringify(showmap));
	}
	
	function endTurn() {
		endturn = {
			name : "end_turn"
		}

		ws.send(JSON.stringify(endturn));
	}
	
//CREATE NEW CONSTRUCTION
	
	function newHouse() {
		create = true;
		createWhat = "House";
		adjustCursor(32,32,[100,100,100]);
		typesProd = "none";
	}
	
	function newFiremanPost() {
		create = true;
		createWhat = "FiremanPost";
		adjustCursor(32,32,[100,100,100]);
		typesProd = "none";
	}	

	function newPolicePost() {
		create = true;
		createWhat = "PolicePost";
		adjustCursor(32,32,[100,100,100]);
		typesProd = "none";
	}
	
	function newCloths() {
		create = true;
		createWhat = "Cloths";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none";
	}
	
	function newFurniture() {
		create = true;
		createWhat = "Furniture";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none";
	}	
	
	function newWine() {
		create = true;
		createWhat = "WineCellar";
		adjustCursor(32,32,[100,100,100]);
		typesProd = "none";
	}		
	
	function newFarmCotton() {
		create = true;
		createWhat = "Farm";
		adjustCursor(64,96,[100,100,100]);
		typesProd = "cotton"; 
	}	
	
	function newFarmRice() {
		create = true;
		createWhat = "Farm";
		adjustCursor(64,96,[100,100,100]);
		typesProd = "rice"; 
	}
	
	function newFarmGrapes() {
		create = true;
		createWhat = "Farm";
		adjustCursor(64,96,[100,100,100]);
		typesProd = "grapes"; 
	}
	
	function newFarmMeat() {
		create = true;
		createWhat = "Farm";
		adjustCursor(64,96,[100,100,100]);
		typesProd = "meat"; 
	}
	
	function newFarmBeans() {
		create = true;
		createWhat = "Farm";
		adjustCursor(64,96,[100,100,100]);
		typesProd = "beans"; 
	}
	
	function newRoad() {
		create = true;
		createWhat = "Road";
		adjustCursor(32,32,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newFurniture() {
		create = true;
		createWhat = "Furniture";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newWineCellar() {
		create = true;
		createWhat = "WineCellar";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newMineRock() {
		create = true;
		createWhat = "Mine";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
		mineType = "rock"; 
	}
	
	function newMineIron() {
		create = true;
		createWhat = "Mine";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
		mineType = "iron";
	}
	
	function newMineSulfur() {
		create = true;
		createWhat = "Mine";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
		mineType = "sulfur";
	}
	
	function newMineClay() {
		create = true;
		createWhat = "Mine";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
		mineType = "clay";
	}
	
	function newMarket() {
		create = true;
		createWhat = "Market";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newBank() {
		create = true;
		createWhat = "Bank";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newLumberJack() {
		create = true;
		createWhat = "LumberJack";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newMansion() {
		create = true;
		createWhat = "Mansion";
		adjustCursor(64,64,[100,100,100]);
		typesProd = "none"; 
	}
	
	function newPalace() {
		create = true;
		createWhat = "Palace";
		adjustCursor(96,96,[100,100,100]);
		typesProd = "none";
	}
	
	function newUniversity() {
		create = true;
		createWhat = "University";
		adjustCursor(96,64,[100,100,100]);
		typesProd = "none";
	}
	
	function newBarracks() {
		create = true;
		createWhat = "Barracks";
		adjustCursor(96, 64,[100,100,100]);
		typesProd = "none";
	}	
	
	function newSchool() {
		create = true;
		createWhat = "School";
		adjustCursor(64, 64,[100,100,100]);
		typesProd = "none";		
	}

	function newShipyard(){
		create = true;
		createWhat = "Shipyard";
		adjustCursor(64, 64,[100,100,100]);
		typesProd = "none";		
	}
	function newArchersCamp(){
		create = true;
		createWhat = "ArcherCamp";
		adjustCursor(64, 64,[100,100,100]);
		typesProd = "none";		
	}
	function newInfirmary(){
		create = true;
		createWhat = "Infirmary";
		adjustCursor(64, 64,[100,100,100]);
		typesProd = "none";		
	}
	function newMachinery(){
		create = true;
		createWhat = "Machinery";
		adjustCursor(64, 64,[100,100,100]);
		typesProd = "none";		
	}
	function newKnights(){
		create = true;
		createWhat = "Knights";
		adjustCursor(64, 96,[100,100,100]);
		typesProd = "none";		
	}
	function newAirport(){
		create = true;
		createWhat = "Airport";
		adjustCursor(96, 96,[100,100,100]);
		typesProd = "none";
	}
	
	function sendCreateConstruction(pos) {
		var create = {
			name: "create",
			type: createWhat,
			position:pos,
			id: city.id,
			prodType: typesProd,
			mineType: mineType
		}
		
		ws.send(JSON.stringify(create));
	}
	
	function increaseIndustryLevel() {
		var create = {
				name: "increase_industry_level",
			}
			
			ws.send(JSON.stringify(create));	
	}
	
//------------------WARRIORS FUNCTIONS----------------
	
function processCreateWarrior(message) {
	city = message.city;
	country = message.gamer;
	var constru = message.construction;
	
	if(!message.created) {
		if(message.full) {
			alert("Queue Full");
		}
	} else {
		alert("Created");
	}
}
	
function showBarracksButtons(barracks) {
	var elms = document.getElementsByClassName("level1_barracks");
	var idx = 0;
	
	for(idx =0; idx<elms.length; idx++) {
		elms[idx].style.display="block";
	}
	
	if(barracks.level > 1) {
		elms = document.getElementsByClassName("level2_barracks");
		
		for(idx =0; idx<elms.length; idx++) {
			elms[idx].style.display="block";
		}		
	}
	
	if(barracks.level > 4) {
		if(country.researchs.gunpowderLevel > 0 && country.researchs.conductCodeLevel > 0) {
			elms = document.getElementsByClassName("level5_barracks");
			
			for(idx =0; idx<elms.length; idx++) {
				elms[idx].style.display="block";
			}
		}
	}
}

function showKnightBtn(constru) {
	if(country.research.conductCodeLevel > 0) {
		displayElementByClassName("knight");
	}
}

function showArcherBtn(constru) {
	if(country.research.conductCodeLevel > 0) {	
		displayElementByClassName("archer");
	}
}

function showInfirmaryBtn(constru){
	if(country.research.medicineLevel > 0 && country.research.conductCodeLevel > 0) {
		displayElementByClassName("infirmary");
	}
}

function showCannonBtn(constru) {
	if(country.research.steamEngineLevel > 0 && country.research.gunpowderLevel > 0 && constru.level > 3) {
		displayElementByClassName("cannon");
	}
}

function showCatapultBtn(constru){
	if(country.research.steamEngineLevel > 0 && country.research.gunpowderLevel > 0) {
		displayElementByClassName("catapult");	
	}
}

function displayElementByClassName(classname) {
	
	var elms = document.getElementsByClassName(classname);
	
	for(idx =0; idx<elms.length; idx++) {
		elms[idx].style.display="block";
	}	
}

/*
<hr/>
    	<button style="display:none" class="knight" onclick="newKnight()">New Knight</button>
    	<button style="display:none" class="archer" onclick="newArcher()">New Archer</button>
    	<button style="display:none" class="infirmary" onclick="newInfirmary()">New Infirmary</button>
    	<button style="display:none" class="catapult" onclick="newCatapult()">New Catapult</button>
    	<button style="display:none" class="cannon" onclick="newCannon()">New Cannon</button>
 * */
	
function sendCreateWarrios(type, amnt) {
	var create = {
		name: "create_warrior",
		type: type,
		amount:amnt,
		cityId: city.id
	}
	
	ws.send(JSON.stringify(create));
}

function newSwordMan() {
	sendCreateWarrios(1, 1);
}

function newSpearMan() {
	sendCreateWarrios(2, 1);
}

function newSling() {
	sendCreateWarrios(3, 1);
}