(function (lib, img, cjs, ss) {

var p; // shortcut to reference prototypes
lib.webFontTxtFilters = {}; 

// library properties:
lib.properties = {
	/*width: 1000,
	height: 640,*/
	fps: 24,
	color: "#FFFFFF",
	webfonts: {},
	manifest: []
};



lib.webfontAvailable = function(family) { 
	lib.properties.webfonts[family] = true;
	var txtFilters = lib.webFontTxtFilters && lib.webFontTxtFilters[family] || [];
	for(var f = 0; f < txtFilters.length; ++f) {
		txtFilters[f].updateCache();
	}
};
// symbols:



(lib.shape47 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAgkgPQABgRALgNQAOgMAPACQATACALAPQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRgAAQA2IgJACIgGgCIgCgJIACgDIAIgFIAJAFQACABAAAFQAAAEgEACg");
	this.shape.setTransform(-18.9,-52.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_1.setTransform(-18.6,-52.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_2.setTransform(-18.9,-52.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAAjgmQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRQABgRALgNQAOgMAPACQATACALAPgAABAqIAIgFIAJAFQACABAAAFQAAAEgEACIgJACIgGgCIgCgJg");
	this.shape_3.setTransform(-88.3,-50);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_4.setTransform(-88,-50.1);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_5.setTransform(-88.3,-50);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkliTQAZAfAAAqQAAArgZAeQgYAcgiAAQgjAAgYgcQgZgeAAgrQAAgqAZgfQAYgeAjAAQAiAAAYAegAGZivQAbAgAAAtQAAAtgbAfQgbAegnAAQgmAAgbgeQgcgfAAgtQAAgtAcggQAbggAmAAQAnAAAbAggAhvBTQgIAAgGgGQgGgFAAgHQAAgIAGgFQAGgFAIAAQAIAAAFAFQAGAFAAAIQAAAHgGAFQgFAGgIAAgAj9DPQA/AIBLg/QBpBPBogs");
	this.shape_6.setTransform(-51.8,-40.9);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiQgvQCKAaCWgIQgtBIhvAFQhsgwgYgvg");
	this.shape_7.setTransform(-47,29.6);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("Ah+CMQgFgFAAgIQAAgHAFgGQAGgFAJAAQAHAAAGAFQAGAGAAAHQAAAIgGAFQgGAFgHAAQgJAAgGgFgAmaA7QgZgeAAgoQAAgrAZgeQAYgeAiAAQAjAAAZAeQAYAeAAArQAAAogYAeQgZAfgjAAQgiAAgYgfgAEVAnQgbggAAgrQAAgtAbgfQAbggAnAAQAmAAAcAgQAbAfAAAtQAAArgbAgQgcAfgmAAQgnAAgbgfg");
	this.shape_8.setTransform(-51.8,-47.2);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("rgba(255,102,153,0.859)").s().p("ApCBnQgPgVgGgbIgEgSIgBgYQAAgzAaglQAbglAmAAQAmAAAbAlQAbAlAAAzQAAA1gbAlQgbAmgmAAQgmAAgbgmgAGEBMQglglAAgzQAAg1AlgmQAmglA0AAQA1AAAlAlQAlAmAAA1QAAAzglAlQglAlg1AAQg0AAgmglg");
	this.shape_9.setTransform(-51,-14.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AM2sCQChiJCBizQiYAaiHAlQAvB7gyCCIgBACQi8CfjkBlQA0B2ARCRIIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhAGVn8IgtATAHTEEQgBhxALiOQAKiGgNh0AMMQXIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7AJ5PPQBfgPA0BXQhZBmhCB1Atq30IAAgFAtkzXQBrgPCBBdQhni/iLisAtkzXQgQiVAKiIAqNpeQgXAQgUAVQiMlsggkyAqNFdQh7hPgciKIgDgSQgQhjAdh9IgBgGIACAFIgBABAsbh0QgSlFB1iAACeqyIgbAUACeqyQkQiBlLByQgtAPgtAUAm9rBQhIj1hzjTAq0NWQBxCsDOB1QBngFAOg7ABpHhQiNDxgJFMQAiBCBqADQDoiQB/iWAmwHmQCkEMARFFAqNFdQAxAfBAAWArHXdIAEAPQgpAcgcgcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzArSQBQgmgzgjgRArHXdIgUhFAupQZQBaBxA0BxAIUXiIzbgFAM5v/QmpB0jyDZ");
	this.shape_10.setTransform(-30.5,-22);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("ArOVcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzQh7hPgciIIgDgSQgQhjAdh+IABgCIgCgEQgSlGB1h/QAUgWAXgQQgXAQgUAWQiMltggkyQBrgOCBBcQBzDUBID0QgtAQgtATQAtgTAtgQQFLhyEQCBIgbAUIAbgUQDyjYGph0QAvB6gyCDIgBABQi8CfjkBlQA0B2ARCRIIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhQA6ghAwgHQBfgPA0BXQhZBmhCB1QBCh1BZhmIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7IAFA7IzbgFIgUhFIAUBFIAEAPQgUAOgSAAQgRAAgOgOgArhRrQg0hxhahxQBaBxA0BxgAk7PnQBngFAOg7QgOA7hnAFQjOh1hxisQBxCsDOB1IAAAAgACZPTQDoiQB/iWQh/CWjoCQQhsgDgihCQAJlMCPjxQiPDxgJFMQAiBCBsADIAAAAgAjBOnQgRlFikkMQCkEMARFFgAqYNxQgmgzgjgRQAjARAmAzgAniECQhAgWgxgfQAxAfBAAWgAINB0IAAgLQAAhrAKiHQAFg7AAg4QAAhHgIhCQAIBCAABHQAAA4gFA7QgKCHAABrIAAALgAGip5IAtgTg");
	this.shape_11.setTransform(-36.3,-7.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("ALFB8QCGgmCZgaQiCC0igCJQAyiDgvh6gAvZhbQgQiVAKiIQCLCtBnC+QiBhdhrAPg");
	this.shape_12.setTransform(-18.9,-136.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-142.8,-176,224.6,308);


(lib.shape44 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAEg6IAPADQANAFAMAOQAOARgDAYQgCAXgRASQgKAIgMADIgRACIgTgDQgLgIgIgKQgOgTABgXQAEgYARgQQALgLAPgDgAAbgeQAJAKgCANQAAAPgMAIQgIAJgOAAQgLgDgLgKQgIgLADgMQABgOAIgKQALgKALABQAPADAIALgAAQAmQAAADgEABIgGABIgFgBIgBgHIABgDIAHgEIAGAEQACACAAAEg");
	this.shape.setTransform(-100.2,-44.5);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAArIgDgHIADgDIAFgEIAHAEQABACAAAEQAAACgDACIgHABgAgYANQgIgLACgNQABgNAJgKQALgKALABQAPACAIAMQAIAKgCANQAAAPgLAIQgIAIgNAAQgNgCgKgKg");
	this.shape_1.setTransform(-99.9,-44.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgWA4QgLgIgIgJQgOgUABgXQAEgYARgPQALgMAPgDIALAAIAPADQANAFAMANQAOATgDAWQgCAYgRASQgKAIgMADIgRACgAABAgIgBADIABAHIAFABIAGgBQAEgBAAgDQAAgEgCgCIgGgDgAgSgjQgIAKgBANQgDANAIALQALALALABQAOABAIgJQAMgIAAgPQACgNgJgKQgIgLgPgCIgCgBQgKAAgKAJg");
	this.shape_2.setTransform(-100.2,-44.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgIhDIANAAIARADQAPAGAOAQQAQAUgDAbQgDAcgUATQgLAKgOADIgUACIgWgEQgMgHgKgMQgRgWACgbQAEgcAVgRQAMgOASgDgAAggjQAJAMgCAPQAAARgNAJQgKAKgQAAQgOgCgMgMQgJgNADgPQABgPAKgMQANgLANACQARACAKANgAAQAlIACAGQAAADgEACIgHACQgEAAgCgCQgCgEAAgEIACgDIAIgEg");
	this.shape_3.setTransform(-30.8,-48.3);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAxQgEgEAAgEIAEgDIAGgEIAHAEIACAGQAAADgEACIgHACQgEAAAAgCgAgdAPQgJgNADgPQABgPAKgMQANgLANACQARACAKANQAJAMgCAPQAAARgNAJQgKAKgPAAQgPgCgMgMg");
	this.shape_4.setTransform(-30.5,-48.4);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgaBAQgMgIgKgLQgRgXACgbQAEgcAVgRQAMgOASgDIANAAIARAEQAPAFAOAQQAQAVgDAaQgDAcgUAUQgLAJgOADIgUACgAABAlIgCADQAAAFACADQACACAEAAIAHgCQAEgCAAgCIgCgHIgHgEgAgVgpQgKAMgBAPQgDAPAJANQAMAMAOACQAQAAAKgJQANgKAAgRQACgPgJgLQgKgOgRgCIgEAAQgLAAgLAJg");
	this.shape_5.setTransform(-30.8,-48.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkvgKQgXAZggAAQgfAAgXgZQgKgMgFgOQgHgSAAgVQAAgVAHgSQAFgOAKgMQAXgaAfAAQAgAAAXAaQAWAcAAAlQAAAmgWAbgAFUjSQAoAAAbAfQAcAeAAArQAAAsgcAeQgbAegoAAQgnAAgcgeQgcgeAAgsQAAgrAcgeQAcgfAnAAgAioAyQgHgEAAgGQAAgGAHgEQAGgEAJAAQAJAAAGAEQAGAEAAAGQAAAGgGAEQgGAEgJAAQgJAAgGgEgAjlDKQB4hMCEBU");
	this.shape_6.setTransform(-63.1,-35.4);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AidgxQCRAUCqgHQhUBKhSAMIgEAAQgnAAhqhjg");
	this.shape_7.setTransform(-54.6,30.7);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("rgba(255,102,153,0.859)").s().p("Ao8hqIALgBQA2AAAnAnQASATAKAXQAKAYAAAaQAAAcgKAZQgKAWgSATQgTATgWAKQhShvATiOgAGLBoIgQgJIgSgQIgDgEQgjgmAAgyQAAgtAagjIAMgOQAmgnA3AAQA2AAAmAnIANAOQALAQAHATIAABZQgKAbgVAVQgPAQgSAJg");
	this.shape_8.setTransform(-61.8,-11.3);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AipCAQgFgEgBgGQABgGAFgEQAHgEAJAAQAJAAAGAEQAGAEAAAGQAAAGgGAEQgGAEgJAAQgJAAgHgEgAmcBCQgKgLgFgOQgHgSAAgVQAAgTAHgSQAFgOAKgMQAXgbAfAAQAgAAAWAbQAXAbAAAkQAAAmgXAaQgWAbggAAQgfAAgXgbgAEQAsQgbgeAAgpQAAgrAbgfQAcgeAoAAQAoAAAbAeQAcAfAAArQAAApgcAeQgbAfgoAAQgoAAgcgfg");
	this.shape_9.setTransform(-63.1,-43.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AFGj9IAEABQgOh3gbhhQEIhQDdiFQA6iIgphhQl8AhlVCtQgnAUgkAVAMGqpQCrhnCRiGQiYgJiTANAFKj8QHsBSFxCUIkdHGIosjKIDzGcIifAcQAxCOBGCNQBVgWBIBoIA/hAQBXgJAAB2IlsG2QghAQgSgbIABgLIADgzAFeDmIgOgFIgEA6IAAACQAnDJA/C4AG9P+QA5g5AzgMAInTnQBEh4BbhkAFQDhQASkPgYjOAsf3zIAAAEQBtCeA3DLQA2DKgBD6QFOhjE+BhAtPzZIgDgBAtPzZQBsgdBoBwAtPzZQAPiMAhiKAsCFjQhLgzgtg9QhShuATiPQAGg0AUg5QgFkPBpihIACgDQg8ldAmlSAsfpOIgaAkAqxqeQA1gVA2gPAo6HUQEFF3g1D1QgtA0gugMQirhBidjfAgiHYQh2EJABE6QAxA1BLAJQD1ifChiwAsCFjQA/AqBTAjAsYXNIAAAHQgUATgjgIIlXmsQAAhgBugBIACACQAoAhAiAmQAtiGBWAbQBskhgFksAsGPzQg3g2gsgNAsYXNIgHg+AvsQbQBWBhAyB+AEhnUIhjAbAG+XYIzWgL");
	this.shape_10.setTransform(-23.7,-21);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("rgba(255,102,153,0.859)").s().p("AhdBLIB0AAQgZAOggAAQghAAgagOgABXhYQAHAVAAAZQAAAYgHAUg");
	this.shape_11.setTransform(-12.8,-8.4);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("AG9VZIABgLIADgzIgDAzIzWgLIgHg+IAHA+IAAAHQgUATgjgIIlXmsQAAhgBugBIACACQAoAhAiAmQAtiGBWAbQBskhgFksQA/AqBTAjQhTgjg/gqQhLgzgtg9QhShsATiRQAGg0AUg5QgFkPBpihIACgDQg8ldAmlSQBsgdBoBwQA2DKgBD6QFOhjE+BhQFVitF8ghQApBhg6CIQjdCFkIBQQAbBhAOB3IgEgBIAEABQHsBSFxCUIkdHGIosjKIDzGcIifAcQg/i4gnjJIAAgCIAEg6IAOAFIgOgFQAIhzAAhpQAAiLgOh2QAOB2AACLQAABpgIBzIgEA6IAAACQAnDJA/C4QAxCOBGCNQBVgWBIBoQhbBkhEB4QBEh4BbhkIA/hAQBXgJAAB2IlsG2QgMAGgLAAQgRAAgLgRgAtkRwQgyh+hWhhQBWBhAyB+gAmzPgQAiAAAiglIACgBIADgDIAAgBQAJgpAAgtQAAjejZk4QDZE4AADeQAAAtgJApIAAABIgDADIgCABQgiAlgiAAIgBAAIAAAAQgIAAgHgCIgBAAIgBAAQirhBidjfQCdDfCrBBIABAAIABAAQAHACAIAAIAAAAIABAAgAgbPPQD1ifChiwQihCwj1CfQhLgJgxg1IAAgHQAAk2B1kGQh1EGAAE2IAAAHQAxA1BLAJIAAAAgAG9N0QA5g5AzgMQgzAMg5A5gAsGNpQg3g2gsgNQAsANA3A2gABJBNQAgAAAbgOIh2AAQAaAOAhAAgADEgIQAHgWAAgYQAAgZgHgVgAC+pDIBjgbgAs5q0IAagkgAgFslQAkgVAngUQgnAUgkAVgAqxsoQA1gVA2gPQg2APg1AVgAsCDZIAAAAgAFQBXgAFKmGg");
	this.shape_12.setTransform(-23.7,-7.2);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#000000").s().p("AKfC5QCSgMCYAIQiRCHiqBnQA5iJgohhgAvIiMQAQiMAgiKQBtCfA3DLQhohwhsAcg");
	this.shape_13.setTransform(-11.6,-131.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-143.8,-174.4,240.4,306.9);


(lib.shape43 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAEguQAOACAIAMQAJALgCAOQAAAPgLAJQgJAJgNAAQgMgCgJgLQgJgMADgOQABgOAIgKQALgKALABgAgHg9IALAAIAOADQANAFALAOQAPATgDAYQgCAagRASQgKAJgMADIgRACQgKAAgHgDQgLgHgIgLQgPgVACgYQADgaASgQQALgMAOgDgAAPAoIgDAEIgGACQgEAAgBgCQgCgDAAgEIACgDQADgEADAAIAGAEg");
	this.shape.setTransform(-101.1,-47.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAAtQgDgDAAgEIADgDQACgEADAAIAGAEIACAGIgDAEIgGACQgEAAAAgCgAgXAOQgJgMADgOQABgOAIgLQALgKALABQAOACAIANQAJAKgCAOQAAAQgLAJQgJAIgMAAQgNgBgJgLg");
	this.shape_1.setTransform(-100.9,-47.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgVA7QgLgHgIgLQgPgVACgYQADgaASgQQALgMAOgDIALAAIAOADQANAFALAOQAPATgDAYQgCAagRASQgKAJgMADIgRACQgKAAgHgDgAABAiIgCADQAAAEACADQABACAEAAIAGgCIADgEIgCgGIgGgEQgDAAgDAEgAgSglQgIAKgBAOQgDAOAJAMQAJALAMACQANAAAJgJQALgJAAgPQACgOgJgLQgIgMgOgCIgDAAQgJAAgKAJg");
	this.shape_2.setTransform(-101.1,-47.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgVgqQANgMANACQAQACAKAOQAJAMgCAQQAAASgNAKQgJAKgQAAQgOgCgLgNQgJgNACgQQACgQAJgMgAAFhGIAQAEQAPAGANAQQARAWgEAbQgCAegUAUQgLAKgNAEIgUACIgVgEQgMgIgKgMQgQgYABgbQAEgeAVgSQAMgOARgEgAAQAnIACAHQAAADgEACIgHACIgGgCQgCgEAAgEIACgEQAEgEADAAg");
	this.shape_3.setTransform(-33.4,-52.2);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAzQgDgDAAgFIADgDQACgFAEAAIAHAFIACAGQAAADgDACIgIACgAgbAQQgKgOADgQQABgQAJgMQANgLANABQARACAJAPQAKALgCAQQAAASgNAKQgKAKgOAAQgPgCgLgMg");
	this.shape_4.setTransform(-33.2,-52.2);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgZBDQgMgIgKgMQgQgYABgbQAEgeAVgSQAMgOARgEIANAAIAQAEQAPAGANAQQARAWgEAbQgCAegUAUQgLAKgNAEIgUACgAABAnIgCAEQAAAEACAEIAGACIAHgCQAEgCAAgDIgCgHIgIgEQgDAAgEAEgAgVgqQgJAMgCAQQgCAQAJANQALANAOACQAQAAAJgKQANgKAAgSQACgQgJgMQgKgOgQgCIgEAAQgLAAgLAKg");
	this.shape_5.setTransform(-33.4,-52.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ak3hxQATAfAAArQAAApgTAeQgUAfgcAAQgcAAgUgfQgKgQgFgRQgFgSAAgUQAAgVAFgRQAFgUAKgQQAUgfAcAAQAcAAAUAfgAi1A7QAAgGAFgFIAQgFQAJAAAGAFQAGAFAAAGQAAAHgGAEQgGAFgJAAQgJAAgHgFQgFgEAAgHgAGshfQAAAsgcAgQgdAdgoAAQgpAAgcgdQgdggAAgsQAAgsAdggQAcgfApAAQAoAAAdAfQAcAgAAAsgAifCVQCMBYBChAAkfC9QAfArBehT");
	this.shape_6.setTransform(-63.9,-41.4);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiMgvQCMAdCNgWQgbA+hcAaQhsgMg2hTg");
	this.shape_7.setTransform(-55.8,30.7);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("rgba(255,102,153,0.859)").s().p("AoxhfIAFAAQAxABAiAoQAjAoAAA2QAAA5gjAoQgNAQgPAJQhMh0AQiNgAFsBHIgLgQQgYgiAAgtQAAg5AjgoQAigoAxAAQAfAAAZARQAOAIANAPQAiAoAAA5QAAA3giAoQgjAogwAAQgxAAgigog");
	this.shape_8.setTransform(-62.8,-14.5);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AivCGQgHgEAAgHQAAgGAHgFIAPgFQAJAAAGAFQAGAFABAGQgBAHgGAEQgGAFgJAAQgJAAgGgFgAmXBgQgKgQgGgTQgEgSAAgUQAAgVAEgPQAGgUAKgQQAUgfAcAAQAcAAAUAfQAUAfAAApQAAArgUAeQgUAfgcAAQgcAAgUgfgAEGArQgdggAAgqQAAgsAdggQAcgfAoAAQApAAAdAfQAcAgAAAsQAAAqgcAgQgdAfgpAAQgoAAgcgfg");
	this.shape_9.setTransform(-63.9,-47.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AL3qsQjjByj2A2QAeBrAPCMIMxCOIkmISIoBirIgBAdIDlFNIiFA/QA+C8BEBeQBggTA+BkIA2hHQBTgGAOBkIl2HGQgfANgQgUIgEgHQgNgaAJgVAMQufQCcAACVAmQigB4iqBVQA0iGgbhtgAFUEFIgEBBQA2DBAuCKAI2OrQg0ALg/AuALUP8IikDYAFLkNQAXDVgNEgAsUzkQBpALBoBmQAYCmgBDjQhKAWhKAjAsUzkQAeh9A2h9QBZB8AkDvArzFQQhOg8gthFQhNh0ARiMQAKhYAuhhQADkFA5hcIACgCIAAgIQgrlZBLk2AsCqJQgVAOgdAuAgdrAIA+ggQGLjAFkABAosrqQEdhVEwBfAsHNFQDtENBagBIACABQA4ACAmgzQABlrjhj1AgbGrQiZEdArE2QAdAyBQAOQDMiTC0iWArzFQQAuAjA4AgAsSXEIgBABQgLAQgngLIk2lxQgDh5BqgCIAxA3QA9iPBSAlQBMk5AVkiAsYPaQgfgjgdgMAsSXEQAJgSgUguAvjQVQBHBaA+B6AHCXLIzUgHAEeoEIhxAV");
	this.shape_10.setTransform(-25.1,-19);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("AHGVVIgEgHQgIgPAAgOQAAgJAEgIQgEAIAAAJQAAAOAIAPIzUgHQADgFAAgJQAAgSgOgfQAOAfAAASQAAAJgDAFIgBACQgLAQgngMIk2lwQgDh6BqgCIAxA4QA9iQBSAlQBMk5AVkhQhOg8gthFQhNhzARiOQAKhXAuhiQADkFA5hbIACgCIAAgIQgrlZBLk3QBpAMBoBlQAYCmgBDjQhKAXhKAiQBKgiBKgXQEdhVEwBfQGLi/FkABQAbBsg0CHQjjBxj2A2QAeBrAPCMIMxCPIkmISIoBirQAFhoAAhdQAAiogPiJQAPCJAACoQAABdgFBoIgBAcIDlFNIiFBAQguiLg2jAIAEhCIgEBCQA2DAAuCLQA+C7BEBfQBggUA+BkIikDYICkjYIA2hHQBTgFAOBkIl2HGQgMAFgKAAQgPAAgKgNgAteRtQg+h6hHhaQBHBaA+B6gAm6PVQA1AAAkgwIABgBIAAgFQAAlnjgj0QDgD0AAFnIAAAFIgBABQgkAwg1AAIAAAAIAAAAIgEAAIgCAAIgBAAIgCAAQgIgBgKgCIAAAAQhWgXi7jOIgDgDIgBgBIgdghIAdAhIABABIADADQC7DOBWAXIAAAAQAKACAIABIACAAIABAAIACAAIAEAAIAAAAIAAAAgAgcPBQDMiTC0iWQi0CWjMCTQhQgOgdgxQgKhEABhDQAAjuB3jfQh3DfAADuQgBBDAKBEQAdAxBQAOIAAAAgAHDNoQA/gvA0gKQg0AKg/AvgAsYNeQgfgjgdgNQAdANAfAjgAqNEWQg4gfgugjQAuAjA4AfgACtprIBxgWgAs0rJQAdgvAVgNQgVANgdAvgAgds9IA+ggg");
	this.shape_11.setTransform(-25.1,-6.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("AJ6ClQCcAACVAmQigB4iqBVQA0iGgbhtgAuqieQAeh9A2h9QBZB9AkDuQhohmhpgLg");
	this.shape_12.setTransform(-10.1,-128.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-140.9,-170.3,231.8,302.6);


(lib.shape42 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgYgtQAOgMAPACQATACALAPQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRQABgRALgNgAgrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAAQA2IgJACIgGgCIgCgJIACgDIAIgFIAJAFQACABAAAFQAAAEgEACg");
	this.shape.setTransform(-18.9,-52.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_1.setTransform(-18.6,-52.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_2.setTransform(-18.9,-52.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAAFg3QATACALAPQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRQABgRALgNQAOgMAPACgAABAqIAIgFIAJAFQACABAAAFQAAAEgEACIgJACIgGgCIgCgJg");
	this.shape_3.setTransform(-88.3,-50);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_4.setTransform(-88,-50.1);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_5.setTransform(-88.3,-50);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AlfixQAiAAAYAeQAZAfAAAqQAAArgZAeQgYAcgiAAQgjAAgYgcQgZgeAAgrQAAgqAZgfQAYgeAjAAgAFXjPQAnAAAbAgQAbAgAAAtQAAAtgbAfQgbAegnAAQgmAAgbgeQgcgfAAgtQAAgtAcggQAbggAmAAgAhiBNQgFAGgIAAQgIAAgGgGQgGgFAAgHQAAgIAGgFQAGgFAIAAQAIAAAFAFQAGAFAAAIQAAAHgGAFgAj9DPQA/AIBLg/QBpBPBogs");
	this.shape_6.setTransform(-51.8,-40.9);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiQgvQCKAaCWgIQgtBIhvAFQhsgwgYgvg");
	this.shape_7.setTransform(-47,29.6);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("Ah+CMQgFgFAAgIQAAgHAFgGQAGgFAJAAQAHAAAGAFQAGAGAAAHQAAAIgGAFQgGAFgHAAQgJAAgGgFgAmaA7QgZgeAAgoQAAgrAZgeQAYgeAiAAQAjAAAZAeQAYAeAAArQAAAogYAeQgZAfgjAAQgiAAgYgfgAEVAnQgbggAAgrQAAgtAbgfQAbggAnAAQAmAAAcAgQAbAfAAAtQAAArgbAgQgcAfgmAAQgnAAgbgfg");
	this.shape_8.setTransform(-51.8,-47.2);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("rgba(255,102,153,0.859)").s().p("ApCBnQgPgVgGgbIgEgSIgBgYQAAgzAaglQAbglAmAAQAmAAAbAlQAbAlAAAzQAAA1gbAlQgbAmgmAAQgmAAgbgmgAGEBMQglglAAgzQAAg1AlgmQAmglA0AAQA1AAAlAlQAlAmAAA1QAAAzglAlQglAlg1AAQg0AAgmglg");
	this.shape_9.setTransform(-51,-14.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AM5v/QAvB7gyCCQChiJCBizQiYAaiHAlQmpB0jyDZIgbAUAGVn8IgtATAHTEEQgBhxALiOQAKiGgNh0QgRiRg0h2QDkhlC8ifIABgCAHaj1IIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhAMMQXIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7AJ5PPQBfgPA0BXQhZBmhCB1Atq30IAAgFAtkzXQBrgPCBBdQhni/iLisAtkzXQgQiVAKiIAqNpeQgXAQgUAVQiMlsggkyAqNFdQh7hPgciKIgDgSQgQhjAdh9IgBgGIACAFIgBABAsbh0QgSlFB1iAACeqyQkQiBlLByQgtAPgtAUAm9rBQhIj1hzjTAq0NWQBxCsDOB1QBngFAOg7ABpHhQiNDxgJFMQAiBCBqADQDoiQB/iWAmwHmQCkEMARFFAqNFdQAxAfBAAWArHXdIAEAPQgpAcgcgcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzArSQBQgmgzgjgRArHXdIgUhFAupQZQBaBxA0BxAIUXiIzbgF");
	this.shape_10.setTransform(-30.5,-22);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("ArOVcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzQh7hPgciIIgDgSQgQhjAdh+IABgCIgCgEQgSlGB1h/QAUgWAXgQQgXAQgUAWQiMltggkyQBrgOCBBcQBzDUBID0QgtAQgtATQAtgTAtgQQFLhyEQCBIgbAUIAbgUQDyjYGph0QAvB6gyCDIgBABQi8CfjkBlQA0B2ARCRQAIBCAABHQAAA4gFA7QgKCHAABrIAAALIAAgLQAAhrAKiHQAFg7AAg4QAAhHgIhCIIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhQA6ghAwgHQBfgPA0BXQhZBmhCB1QBCh1BZhmIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7IAFA7IzbgFIgUhFIAUBFIAEAPQgUAOgSAAQgRAAgOgOgArhRrQg0hxhahxQBaBxA0BxgAk7PnQBngFAOg7QgOA7hnAFQjOh1hxisQBxCsDOB1IAAAAgACZPTQDoiQB/iWQh/CWjoCQQhsgDgihCQAJlMCPjxQiPDxgJFMQAiBCBsADIAAAAgAjBOnQgRlFikkMQCkEMARFFgAqYNxQgmgzgjgRQAjARAmAzgAniECQhAgWgxgfQAxAfBAAWgAGip5IAtgTg");
	this.shape_11.setTransform(-36.3,-7.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("ALFB8QCGgmCZgaQiCC0igCJQAyiDgvh6gAvZhbQgQiVAKiIQCLCtBnC+QiBhdhrAPg");
	this.shape_12.setTransform(-18.9,-136.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-142.8,-176,224.6,308);


(lib.shape41 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAWg1QAPAFAMANQARARgDAWQgDAYgUARQgLAIgNADIgUABIgVgDQgMgGgKgKQgQgTABgWQAEgYAVgPQAMgLARgDIANAAgAAfgdQAJAKgBANQAAAOgOAIQgJAIgQAAQgNgCgMgKQgJgLACgMQACgNAJgKQANgJANABQARACAJALgAASAlQAAADgEABIgHACIgFgCIgCgGIACgDIAGgEIAIAEg");
	this.shape.setTransform(-39.9,-54);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAApIgDgGIADgDIAFgEIAIAEIACAFQAAADgEABIgHACgAgcAMQgJgLACgMQACgNAJgKQANgJANABQARACAJALQAJAKgBANQAAAOgOAIQgJAIgPAAQgOgCgMgKg");
	this.shape_1.setTransform(-39.6,-54);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgZA2QgMgGgKgKQgQgTABgWQAEgYAVgPQAMgLARgDIANAAIARADQAPAFAMANQARARgDAWQgDAYgUARQgLAIgNADIgUABgAACAgIgCADIACAGIAFACIAHgCQAEgBAAgDIgCgFIgIgEgAgVgiQgJAKgCANQgCAMAJALQAMAKANACQAQAAAJgIQAOgIAAgOQABgNgJgKQgJgLgRgCIgEAAQgLAAgLAIg");
	this.shape_2.setTransform(-39.9,-54);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgSgdQALgIALABQAPABAIAKQAIAIgCAMQAAALgLAHQgIAHgOAAQgLgBgKgJQgIgJACgLQABgLAIgIgAgHgwIALAAIAPACQANAEALAMQAOAPgDASQgCAVgRAOQgKAHgLADIgRABIgSgDQgLgFgIgIQgPgRACgTQADgUASgNQALgKAOgCgAABAbIAGgDIAHADIACAEIgEAEIgGACQgDAAgCgCQgBgCAAgDg");
	this.shape_3.setTransform(31.2,-50.4);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAjQgDgCAAgDIADgDIAFgDIAGADIACAEIgDAEIgHACQAAAAgBAAQgBAAAAgBQgBAAAAAAQAAAAAAgBgAgYAKQgIgJACgLQACgLAIgIQALgIALABQAOABAIAKQAIAIgBAMQAAALgMAHQgIAHgMAAQgNgBgKgJg");
	this.shape_4.setTransform(31.4,-50.4);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgVAuQgLgFgIgIQgPgRACgTQADgUASgNQALgKAOgCIALAAIAPACQANAEALAMQAOAPgDASQgCAVgRAOQgKAHgLADIgRABgAABAbIgBADQAAADABACQAAABABAAQAAAAABAAQAAABABAAQABAAABAAIAGgCIAEgEIgCgEIgHgDgAgSgdQgIAIgBALQgCALAIAJQAKAJALABQAOAAAIgHQALgHAAgLQACgMgIgIQgIgKgPgBIgEAAQgJAAgJAHg");
	this.shape_5.setTransform(31.2,-50.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AFvioQAjAAAZAeQAZAfAAAqQAAArgZAbQgZAegjAAQgjAAgZgeQgYgbAAgrQAAgqAYgfQAZgeAjAAgACXBRQgFAFgIAAQgIAAgGgFQgGgEAAgHQAAgGAGgFIAOgFIANAFQAGAFAAAGQAAAHgGAEgAhDCyQA2BVCNhjIACgBQBDA3AogYQAMgIAKgQAkGi5QAhAgAAAsQAAAtghAfQggAgguAAQguAAghggQgggfAAgtQAAgsAgggQAhggAuAAQAuAAAgAgg");
	this.shape_6.setTransform(-6.1,-40.4);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiXgzQCIAfCogUQhGBMhjARQh7gugMg6g");
	this.shape_7.setTransform(-7.4,28.2);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("rgba(255,102,153,0.859)").s().p("AHpCLQgbgPgQgcQgZgrAMgyQAHgdATgWQALgNAQgKQArgbAvALIAAAAIAGAhIACAGQAPBhhTBlgAomA7QgjgkAAgxQAAgzAjgkQAigkAxAAQAwAAAjAkQAiAkAAAzQAAAxgiAkQgjAkgwAAQgxAAgigkg");
	this.shape_8.setTransform(-6.8,-13.7);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AB7CTQgFgFAAgGQAAgGAFgGIAPgEIANAEQAGAGAAAGQAAAGgGAFQgFAFgIAAQgJAAgGgFgAEzBHQgYgdgBgqQABgpAYgfQAZgeAjAAQAjAAAYAeQAaAfAAApQAAAqgaAdQgYAegjAAQgjAAgZgegAmjAfQgfgfgBgrQABgsAfggQAhggAuAAQAuAAAgAgQAgAgAAAsQAAArggAfQggAgguAAQguAAghggg");
	this.shape_9.setTransform(-6.1,-46.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AKoyUQCChJBBATQgXiWguiFQhQCiguCvQg2DHgLDhQA5ATA4AbANrorIgTgYANrorQBRB8gCDmIAAAAQgCAAAgClIAAAAIAHAhQAUBjhXBpQgaAfgkAgIgCACQhNBCh3BEANrzKQAvEugvFxAN/OgQA+ADAwBeQAdghAggcQBYAJgJA3IkwHbQgnAQgUgVIACgOIAXhsANcEKQgvEPBSGHAL6PrQBKhOA7ADAPtQBQhVBdhAB9Ar4u9IgBgFAxyvjQDIAKCyAcQGmBCEgCjIBSAzAkFoDIAAAEQAsAQAtAOAk2kRQALi0Amg6QkRhfjZh3Qjfh7ikiTIgGgBAmMIzIhtgOIC0mLIndDvIkKnEQF1iPGBhHQgMC6ARE9IAAABIgCALQgbCYg+CpgArvrVQgiiHAZhhAJsG8QkBF6gDDmQASA6BfgEQDGhCCbjVAmJLYQCRCyDbCmQBgAGAnghQAulqiMklAnEW9IAFAUQgVAigagiImBnIQgKhTBoAOIAvA4QA/h7BpApQBwjPA+ioAo6OqQAtARA1AwAriP8IBpB9AnEW9IgZhiAMQXMIzUgPAgyrYQFkh7E1Bn");
	this.shape_10.setTransform(-47.7,-18.8);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#000000").s().p("AvuB5QDJALCxAcQgZBgAiCHQjfh6ikiUgAOqmHQAuCGAXCWQhAgUiDBKQAvivBPijg");
	this.shape_11.setTransform(-60.9,-130.7);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("ALoVPIACgOIAWhtIgWBtIzUgPIAEATQgVAigagiImAnIQgKhSBnANIAvA5QBAh7BpAoQBvjOA/ioQA+iqAaiXIACgLIAAgBQgJi3AAiMQAAhmAFhPQALi0Amg5QkShgjZh3QghiHAYhhQGnBCEgCjIBTAzIhTgzQFkh6E0BnQA6ATA3AbQg3gbg6gTQAMjhA1jIQCDhJBAATQAvEvguFwQBRB9gDDlIAAABQgCAAAhCkIAAABIAGAgQAUBmhWBmQgbAggkAgIgCABQhMBDh3BDQB3hDBMhDQguEPBRGHQA+ADAxBeQAdggAggdQBXAJgJA3IkwHcQgQAHgOAAQgRAAgLgMgAnqUyIgZhjgAMxRQQBBh+BVhdQhVBdhBB+gAqgPuIhph9gAGoPHIAKAAQDGhDCcjVQicDVjGBDIgKAAIAAAAIgBAAQhTAAgSg1IAAAAIAAgBQADjmEBl7QkBF7gDDmIAAABIAAAAQASA1BTAAIABAAIAAAAgAglOlQBHAAAhgbIABAAQALhZAAhVQAAkEhojdQBoDdAAEEQAABVgLBZIgBAAQghAbhHAAIAAAAIAAAAIgbgBIgCAAIgCAAQjbiliRiyQCRCyDbClIACAAIACAAIAbABIAAAAIAAAAgALUNgIAAgBIACgCIAAAAIABAAIACgDIABAAIAAgBQBDhEA3gBIABAAIAAAAIADAAIgDAAIAAAAIgBAAQg3ABhDBEIAAABIgBAAIgCADIgBAAIAAAAIgCACIAAABgAn+NgQg1gwgtgSQAtASA1AwgAkrqKIBYAeIhYgeIAAgFIAAAFgANFq3IgUgYgAofGaIC0mLIndDuIkKnDQF0iQGChHQgFBPAABmQAACMAJC3IAAABIgCALQgaCXg+Cqg");
	this.shape_12.setTransform(-43.8,-4.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-163.2,-170.9,231.2,304.2);


(lib.shape40 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 5
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgWgJQABgKAHgIQAJgIAIABQAMACAGAJQAHAIgBALQAAAKgJAHQgHAGgLAAQgJgBgIgIQgHgJACgKgAggAfQgMgPABgSQADgUAOgMQAJgJAMgCIAIAAIAMACQAKAEAJAKQAMAPgCARQgCAUgOANIgRAJIgNABIgPgCgAALAaIABAEIgCADIgFABIgEgBIgBgFIABgCQACgDADAAg");
	this.shape.setTransform(-90.1,-129.8);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAAhIgCgFIACgCQABgDADAAIAFADIABAEIgCADIgFABgAgTAKQgHgJACgKQABgKAHgIQAJgIAIACQAMABAGAJQAHAIgBALQAAAKgJAHQgHAGgKAAQgKgBgIgIg");
	this.shape_1.setTransform(-89.9,-129.8);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgRAsIgPgNQgMgPABgTQADgTAOgLQAJgKAMgCIAIAAIAMACQAKAFAJAJQAMAPgCARQgCATgOAOIgRAJIgNABgAABAaIgBACIABAFIAEABIAFgBIACgEIgBgDIgFgEQgDAAgCAEgAgOgbQgHAIgBAKQgCAKAHAJQAIAIAJABQALAAAHgHQAJgGAAgLQABgLgHgHQgGgJgMgBIgDgBQgHAAgHAHg");
	this.shape_2.setTransform(-90.1,-129.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 4
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAPgrQAKAEAJAKQAMAPgCARQgCATgOAOIgRAJIgNABIgPgDIgPgMQgMgQABgSQADgTAOgMQAJgJAMgCIAIAAgAAbgEQAAAKgJAGQgHAHgLAAQgJgBgIgIQgHgJACgKQABgKAHgIQAJgIAIABQAMACAGAJQAHAHgBAMgAABAhIgBgFIABgDIAFgDIAFADIABAFIgCADIgFABg");
	this.shape_3.setTransform(-134.8,-129.3);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAhIgCgFIACgCIAEgDIAFADIABAEIgCADIgFABgAgTAKQgHgJACgKQABgKAHgIQAJgIAIACQAMABAGAJQAHAIgBALQAAAKgJAHQgHAGgKAAQgKgBgIgIg");
	this.shape_4.setTransform(-134.6,-129.4);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgRAsIgPgNQgMgPABgTQADgTAOgLQAJgKAMgCIAIAAIAMACQAKAFAJAJQAMAPgCARQgCATgOAOIgRAJIgNABgAABAaIgBACIABAFIAEABIAFgBIACgEIgBgDIgFgEgAgOgbQgHAIgBAKQgCAKAHAJQAIAIAJABQALAAAHgHQAJgGAAgLQABgLgHgHQgGgJgMgBIgDgBQgHAAgHAHg");
	this.shape_5.setTransform(-134.8,-129.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 3
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAAgEIAHABIAEADQAAACgEACIgHACIgGgCIgEgEIAEgDg");
	this.shape_6.setTransform(-112.7,-114.8);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#000000").s().p("AgGAEIgEgEIAEgDIAGgBIAHABIAEADQAAACgEACIgHABg");
	this.shape_7.setTransform(-112.7,-114.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 2
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ai0isQATARAAAZQAAAYgTARQgRASgZAAQgZAAgTgSQgRgRAAgYQAAgZARgRQATgRAZAAQAZAAARARgAEciHQAAAZgSARQgSARgZAAQgZAAgSgRQgSgRAAgZQAAgYASgRQASgRAZAAQAZAAASARQASARAAAYgABCBpIAmACAA3CGIALgdQgkgIggghQggAngjAGIALAcQA8hAA1A9QgbBBgegFQgcAFgcg+Ah0BmQAYALAXgE");
	this.shape_8.setTransform(-111.9,-115.4);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#CCCC00").s().p("AiCgTIEFgRQggA2hjATIAAAAQhZAAgpg4g");
	this.shape_9.setTransform(-111.7,-77.7);

	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("#FF6666").s().p("AAAAsQgcAFgcg8QA8hAA1A9QgZA7gdAAIgDgBg");
	this.shape_10.setTransform(-112.1,-100.4);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#660000").s().p("AhDAIQAjgFAggmQAgAhAkAGIgKAdQg1g7g9A+g");
	this.shape_11.setTransform(-112.2,-105.3);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("AkKArQgRgRAAgZQAAgWARgRQATgRAYAAQAaAAARARQASARAAAWQAAAZgSARQgRASgaAAQgYAAgTgSgAC0AmQgSgQAAgXQAAgZASgRQASgQAZgBQAZABASAQQASARAAAZQAAAXgSAQQgSASgZAAQgZAAgSgSg");
	this.shape_12.setTransform(-111.9,-128.8);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("rgba(255,102,153,0.859)").s().p("AEqBRQgRgGgNgPQgXgZAAgjQAAgiAXgZQANgPARgFIAZgFQAhAAAXAZQAXAZAAAiQAAAjgXAZQgXAZghAAgAl5A6QgYgYAAghQAAghAYgYQAXgXAhAAIAPABQAYAEASASQAXAYAAAhQAAAhgXAYQgSATgYAFIgPABQghAAgXgZg");
	this.shape_13.setTransform(-111.4,-109.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 1
	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("An2rDIAAAFAsDpvQgyAUgxAYIgHAEQBwAiCXAIQhmghg3g5QCGg2CHgeIgSiKIEHBMQEAiTD7BjQEghQDkgNIBrgDIAFAAIgDAEQhFBHiXBSQhxA9ifBEQAfBOAGBmIAAAEQBMCZhgCVQDYhYB3icQA0gGAfA3QhXDwjWDiIgBgHAFwqWQAIAQAHATAKPr0QBhhNAOhNAEbsjIghgOIgEABAj9sAIgEgBIgtAcIgCAAQhnANhfAVAlLhfQiRibBji1QgEgYAAgWIhTgFIgFgxIBagEAl9ndQgChaA2hKAmsD7QhLgtArhkICBjJAlJhiIgCADIAvAsAmsD7QAKAGALAEQBbAHBOhxQAAhDgqgOIhDADAGQiNQgRAdgWAdAnVoTQhMAChFgEAIFCCQAkHKjmDbIBcAqQASAwgeARIkBhBIAAgfQh9ATh1gJIgDAYIjwA3Qg6AKAOhEIBsgqIAJAFAC0MtIgiAFAhgM8IghgDAkTMnQjniJBOmj");
	this.shape_14.setTransform(-113.3,-77.5);

	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f("#FFFF33").s().p("AB7NQIAAgfIAigFIgiAFQh7ASh3gJIghgDIAhADIgDAZIjxA3Qg5AJAOhDIBsgqQjniJBOmjQhLgtArhlICBjIQiRibBji1QgEgYAAgXIAAgFQAAhXAzhHQgzBHAABXIAAAFIhUgEIgEgxIBagEIhaAEQhMAChFgEQhmghg3g5QCGg3CHgdIgSiLIEHBNIgtAcIgDAAQhmANhfAVIAAAEIAAgEQBfgVBmgNIADAAIAtgcIAEABIgEgBQEAiTD7BjIgEABIAEgBIAhANIghgNQEghRDkgNQgOBOhhBMQhxA+ifBEQgHgTgIgQQAIAQAHATQAfBOAGBmIAAADQBMCZhgCWQDXhYB3icQA1gGAfA3QhXDvjXDjIAAgHIAAAHQAlHKjmDaIBcArQARAwgdAQgAkhMrIgJgFgAmjEEQBSAABIhjIABgDIABgBIABgBIAAgBIABgBQAAhDgqgOIhDADIBDgDQAqAOAABDIgBABIAAABIgBABIgBABIgBADQhIBjhSAAIgBAAIAAAAIgJAAIgBAAQgLgFgKgFQAKAFALAFIABAAIAJAAIAAAAIABAAgAlihgIAvAsIgvgsIABgDIgBADgAFShUQAWgeARgcQgRAcgWAegAkYsCgADjsyg");
	this.shape_15.setTransform(-111,-77.3);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#000000").s().p("AttCTIAHgEQAygXAygUQA2A4BmAiQiXgJhwgigAL+i6IBrgDIAFAAIgDAEQhFBHiXBSQBhhNAOhNg");
	this.shape_16.setTransform(-113.3,-149.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15},{t:this.shape_14}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-202.2,-169.9,177.7,184.9);


(lib.shape39 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 2
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACYiXQAEhjArAFQA/gLANBSAkRgZQgIhpBFgTQBPgSAIBfABmg0IgEgFIAEgFIALgCIAKACQAFACAAADQAAADgFACIgKACgADFAiQAbAAANgSAAvBRQAugIAgg/QAsAWAcACQgFA7gGArQgSBugcgBQgXAUg4iRIgOgnIgegBAA9B4QBcgjAhAz");
	this.shape.setTransform(-110.7,-131.9);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#CCCC00").s().p("Ah4gMQCCgCBvgnQg+Bng3ADQhPgIgtg5g");
	this.shape_1.setTransform(-99,-89);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#FF6666").s().p("Ag9g3QBagjAhAzQgSBsgcgBQgDACgDAAQgVAAgyh9g");
	this.shape_2.setTransform(-98.3,-114.2);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#660000").s().p("Ag7AuIgPgmQAugIAeg+QAsAXAdABQgFA6gHArQghgzhZAig");
	this.shape_3.setTransform(-98.4,-124.6);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#000000").s().p("AgJAEIgFgEIAFgDIAJgCIAKACQAFACAAABQAAACgFACIgKACg");
	this.shape_4.setTransform(-99.3,-137.8);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("rgba(255,102,153,0.859)").s().p("AlhCCQgbgZAAgkQAAgkAbgZQAZgXAmAAQAlAAAaAXQAaAZAAAkQAAAkgaAZQgaAaglgBQgmABgZgagAEMgTQgTgXAAghQAAghATgXQATgYAcAAQAbAAATAYQATAXABAhQgBAhgTAXQgTAWgbgBQgcABgTgWg");
	this.shape_5.setTransform(-105.9,-120.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 1
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AnxlkQAmAfBeAFQCBgcCNgvIAggJAnxlkIhPBFQBlgKBugXAhWCgIgPgdIhrBbIiNmqQlRhHlSAKQAQC8B0C5IGmguICSFwICsiEIAfBEQAIhlAbhpQAUhXAihaIAFAGAOIq3IADABALRoVIBai6QA1gcAnA0IABAAQASF+iRGQQgQhogghvIgLAbANftQQBchRBDhoIhuATQAUAmhFCAQhmBZiEBGQA4BbAkBBQBOCMgKARQAKBChGC2AP+wJIADAAAJUroIAhA3AOQv2QjCAuj0CZQkwgXjDC9QjxBkjnDBABVAzIBBBvIAqg9QA/gSAXA9QgQB0hXCNQg/Aog+gdQhehngMhzAgggRQhwiHAxjzAKCOKIBFAqQAXApg0ARQiZgehkhGIi9AOQhqBWihAcQg2gPASgcQAagjBLgYIAEAGAGtOKIA5AAADwOYIhBALAKCOKIgGAKAMJBXQBIHPjPFkAAmOkQi5jnAalP");
	this.shape_6.setTransform(-144.9,-89.4);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#000000").s().p("ArPEwQAmAeBeAFQhuAYhlAJgAKxlhIBugSQhDBohbBQQBEh/gUgng");
	this.shape_7.setTransform(-122.7,-155.5);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#FFFF33").s().p("AgIPVQAXgiBNgYIAFAFIgFgFQi5jnAalPQAIhlAbhqQAUhWAhhaQhviHAxjzIAhgKIghAKQiNAviAAbQhegFgmgeQDnjBDuhkQDFi9ExAWQDziYDCgvQAUAnhECAQhmBYiEBGQA3BcAkBBQBGB8AAAcIgBAFIABASQAABGg+CgQA+igAAhGIgBgSIABgFQAAgchGh8IBai6QA1gcAoA0IABAAQASF+iSGQQgQhogghvIgLAbIALgbQAgBvAQBoQBIHPjPFkIBFAqQAXAogzARQiagdhjhGIA4gBIg4ABIi+ANIhBALIBBgLQhqBXijAbQgzgPASgcgAKyOKIAGgJgACeGfQAhAAAhgUIABAAIACgCIABAAIAAAAIABgBQBYiNAQh1QgSgugpAAIgBAAIAAAAQgMAAgNAEIgBAAIgrA8IhAhuIBABuIArg8IABAAQANgEAMAAIAAAAIABAAQApAAASAuQgQB1hYCNIgBABIAAAAIgBAAIgCACIgBAAQghAUghAAIgBAAIAAAAQgZAAgZgLIgDgCQhfhngKhyQAKByBfBnIADACQAZALAZAAIAAAAIABAAgAAZgUIgEgGgAKsq7Ighg2gAmgA0ImlAvQh0i6gRi7QFSgKFSBGICMGrIBrhbIAPAcQgbBqgIBlIgfhFIirCEgAggCWg");
	this.shape_8.setTransform(-150.4,-88.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-248.5,-193.8,207.1,208.9);


(lib.shape38 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 2
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkRgZQgIhpBFgTQBPgSAIBfACYiXIAEgrQAKg3AhAEQA/gLANBSAB7g7IgHgCIgDgEIADgFIAHgBIAHABQAEACAAADQAAACgEACgADFAiQAbAAANgSAAvBRQAugIAgg/QAsAWAcACQgFA7gJArQgPBugcgBQgXAUg4iTIgOglIgegBAA9B2QBbghAfAz");
	this.shape.setTransform(-109.3,-128.5);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#CCCC00").s().p("Ah7AAID3g0QgtBYg0APQgPACgNAAQhEAAg2g1g");
	this.shape_1.setTransform(-98.5,-87.1);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#FF6666").s().p("Ag7g5QBZghAeAzQgOBsgcgBQgDADgDAAQgWAAgxiAg");
	this.shape_2.setTransform(-97,-110.8);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#660000").s().p("Ag8AtIgOglQAugIAeg+QAsAXAdABQgFA6gKArQgegzhaAhg");
	this.shape_3.setTransform(-97,-121.2);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#000000").s().p("AgGADIgDgDIADgDIAGgCIAGACQAEACAAABQAAACgEABIgGACg");
	this.shape_4.setTransform(-96.9,-135.2);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("rgba(255,102,153,0.859)").s().p("AliCCQgLgLgHgPQgIgQABgTQAAgkAZgaQAbgXAkABQAmgBAZAXQAaAaAAAkQABATgIAQQgGAPgNALQgZAZgmABQgkgBgbgZgAEMgTQgTgXAAghQAAghATgXQAUgYAbAAQAbAAATAYQATAXAAAhQAAAhgTAXQgTAVgbABQgbgBgUgVg");
	this.shape_5.setTransform(-104.5,-117.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 1
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("An4lwQgnAggbAjIgEAEQBQABBUgFIBvgJQiCgIhLgyQCiiDFohUQD/jSDhAmIAUAEAhXEeIgPgfIivCWIiBlsInbALQhOi/gdjBQC4AFC2AaQC3AbC0AxIBZGjIBohOIAXAbQAWg9AchAQg5hIgNhfQgJhGAQhSIAMgxIAzgLAOPwFQAggMAugKQgzBlhHBlQhPBvhqBwQAvBKAqBQQAWhYA6hXQAygTAkAlQAWGGiRGEQBkHojmErIBcA3QAPAvhBAHQh2gQhsg5IgCgbIBOgHANjtRQAnheAFhWAMDnYQAdA5AbA9IABAEQANAwgaBXIgvC6QAcBbASBXAgrCPQgcBKgQBFAAHASIAEAFAB5BXIBBBWIBAg9QA1gHAaAkQgWDehHBHQhEAWhEgWIhdh9AHyrzQC/i7DehXAKHqnIAjA1AgsleQiEAah7AOADgPKIAMAgQhLAmiBAMQg7gJAEgiQAfglBHgNIADAEAC6POIAmgEAHyOxIkSAZABPO/QkGkNBgmU");
	this.shape_6.setTransform(-148.2,-91.9);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFF33").s().p("AAAPmQAhglBHgNQkGkNBgmUIgPgfIivCWIiBltInbAMQhOjAgdjAQC4AEC2AbQC3AbC0AxIBZGjIBohOIAXAbQgcBJgQBGQAQhGAchJQAUg+Aeg/Qg5hIgNhgQgJhFAQhSIAMgxIAzgLIgzALQiEAah7ANQiCgHhLgyQCiiDFohUQD/jSDhAmIAUAEIgUgEQC/i7DehXQgFBVgnBeQhPBwhqBwIgjg2IAjA2QAvBJApBQQAXhXA5hXQAzgTAkAlQAWGGiRGDQgShWgchbIAvi6QARg6AAgpQAAgUgEgQIgBgFQgbg9geg5QAeA5AbA9IABAFQAEAQAAAUQAAApgRA6IgvC6QAcBbASBWQBkHpjmErIBbA3QAQAvhCAHQh1gQhsg5IgCgbIBNgIIhNAIIkSAZIgmADIAmgDIAMAgQhLAmiBAMQg7gJACgigABrO4IgDgEgAB9GmQBEAXBEgXQBHhGAWjeIgBgBIAAgBIgBAAIgBgBIAAAAIgBgCQgVgZgmAAIAAAAIAAAAIgNABIgDAAIAAAAIhAA9IhBhWIBBBWIBAg9IAAAAIADAAIANgBIAAAAIAAAAQAmAAAVAZIABACIAAAAIABABIABAAIAAABIABABQgWDehHBGQhEAXhEgXIhdh9gAAkALIgEgEg");
	this.shape_7.setTransform(-150.7,-90.8);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("AsNF5IAEgEQAbgjAnggQBLAxCCAIIhvAKQhGAEhDAAIgbAAgALAlhQAggNAugKQgzBlhHBlQAnheAFhVg");
	this.shape_8.setTransform(-127.5,-159.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-248.1,-198.2,199.9,212.6);


(lib.shape37 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 5
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgOgbQAJgIAIABQAMACAGAJQAHAIgBALQAAAKgJAHQgHAGgLAAQgJgBgIgIQgHgJACgKQABgKAHgIgAgagiQAJgJAMgCIAIAAIAMACQAKAEAJAKQAMAPgCARQgCAUgOANIgRAJIgNABIgPgCIgPgNQgMgPABgSQADgUAOgMgAABAaQACgDADAAIAFADIABAEIgCADIgFABIgEgBIgBgFg");
	this.shape.setTransform(-90.1,-129.8);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAAhIgCgFIACgCQABgDADAAIAFADIABAEIgCADIgFABgAgTAKQgHgJACgKQABgKAHgIQAJgIAIACQAMABAGAJQAHAIgBALQAAAKgJAHQgHAGgKAAQgKgBgIgIg");
	this.shape_1.setTransform(-89.9,-129.8);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgRAsIgPgNQgMgPABgTQADgTAOgLQAJgKAMgCIAIAAIAMACQAKAFAJAJQAMAPgCARQgCATgOAOIgRAJIgNABgAABAaIgBACIABAFIAEABIAFgBIACgEIgBgDIgFgEQgDAAgCAEgAgOgbQgHAIgBAKQgCAKAHAJQAIAIAJABQALAAAHgHQAJgGAAgLQABgLgHgHQgGgJgMgBIgDgBQgHAAgHAHg");
	this.shape_2.setTransform(-90.1,-129.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 4
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAVgXQAHAHgBAMQAAAKgJAGQgHAHgLAAQgJgBgIgIQgHgJACgKQABgKAHgIQAJgIAIABQAMACAGAJgAAPgrQAKAEAJAKQAMAPgCARQgCATgOAOIgRAJIgNABIgPgDIgPgMQgMgQABgSQADgTAOgMQAJgJAMgCIAIAAgAABAhIgBgFIABgDIAFgDIAFADIABAFIgCADIgFABg");
	this.shape_3.setTransform(-134.8,-129.3);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAhIgCgFIACgCIAEgDIAFADIABAEIgCADIgFABgAgTAKQgHgJACgKQABgKAHgIQAJgIAIACQAMABAGAJQAHAIgBALQAAAKgJAHQgHAGgKAAQgKgBgIgIg");
	this.shape_4.setTransform(-134.6,-129.4);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgRAsIgPgNQgMgPABgTQADgTAOgLQAJgKAMgCIAIAAIAMACQAKAFAJAJQAMAPgCARQgCATgOAOIgRAJIgNABgAABAaIgBACIABAFIAEABIAFgBIACgEIgBgDIgFgEgAgOgbQgHAIgBAKQgCAKAHAJQAIAIAJABQALAAAHgHQAJgGAAgLQABgLgHgHQgGgJgMgBIgDgBQgHAAgHAHg");
	this.shape_5.setTransform(-134.8,-129.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 3
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AgGgDIAGgBIAHABIAEADQAAACgEACIgHACIgGgCIgEgEg");
	this.shape_6.setTransform(-112.7,-114.8);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#000000").s().p("AgGAEIgEgEIAEgDIAGgBIAHABIAEADQAAACgEACIgHABg");
	this.shape_7.setTransform(-112.7,-114.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 2
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ajei9QAZAAARARQATARAAAZQAAAYgTARQgRASgZAAQgZAAgTgSQgRgRAAgYQAAgZARgRQATgRAZAAgAEciHQAAAZgSARQgSARgZAAQgZAAgSgRQgSgRAAgZQAAgYASgRQASgRAZAAQAZAAASARQASARAAAYgABCBpIAmACAA3CGIALgdQgkgIggghQggAngjAGIALAcQA8hAA1A9QgbBBgegFQgcAFgcg+Ah0BmQAYALAXgE");
	this.shape_8.setTransform(-111.9,-115.4);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#CCCC00").s().p("AiCgTIEFgRQggA2hjATIAAAAQhZAAgpg4g");
	this.shape_9.setTransform(-111.7,-77.7);

	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("#FF6666").s().p("AAAAsQgcAFgcg8QA8hAA1A9QgZA7gdAAIgDgBg");
	this.shape_10.setTransform(-112.1,-100.4);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#660000").s().p("AhDAIQAjgFAggmQAgAhAkAGIgKAdQg1g7g9A+g");
	this.shape_11.setTransform(-112.2,-105.3);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("AkKArQgRgRAAgZQAAgWARgRQATgRAYAAQAaAAARARQASARAAAWQAAAZgSARQgRASgaAAQgYAAgTgSgAC0AmQgSgQAAgXQAAgZASgRQASgQAZgBQAZABASAQQASARAAAZQAAAXgSAQQgSASgZAAQgZAAgSgSg");
	this.shape_12.setTransform(-111.9,-128.8);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("rgba(255,102,153,0.859)").s().p("AEqBRQgRgGgNgPQgXgZAAgjQAAgiAXgZQANgPARgFIAZgFQAhAAAXAZQAXAZAAAiQAAAjgXAZQgXAZghAAgAl5A6QgYgYAAghQAAghAYgYQAXgXAhAAIAPABQAYAEASASQAXAYAAAhQAAAhgXAYQgSATgYAFIgPABQghAAgXgZg");
	this.shape_13.setTransform(-111.4,-109.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 1
	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("An2rDQiHAeiGA2QA3A5BmAhQBFAEBMgCIBagEAsDpvQgyAUgxAYIgHAEQBwAiCXAIAn2rDIAAAFAkBsBIkHhMIASCKAL+uOIBrgDIAFAAIgDAEQhFBHiXBSQhxA9ifBEQAfBOAGBmIAAAEQBMCZhgCVQDYhYB3icQA0gGAfA3QhXDwjWDiIgBgHAFwqWQAIAQAHATAKPr0QBhhNAOhNQjkANkgBQIAhAOAD6sxIgEABAj9sAIgEgBIgtAcIgCAAQhnANhfAVAlLhfQiRibBji1QgEgYAAgWIhTgFIgFgxAl9ndQgChaA2hKAkBsBQEAiTD7BjAmsD7QhLgtArhkICBjJAmsD7QAKAGALAEQBbAHBOhxQAAhDgqgOIhDADAlJhiIgCADIAvAsAGQiNQgRAdgWAdAIFCCQAkHKjmDbIBcAqQASAwgeARIkBhBIAAgfQh9ATh1gJIgDAYIjwA3Qg6AKAOhEIBsgqIAJAFAC0MtIgiAFAhgM8IghgDAkTMnQjniJBOmj");
	this.shape_14.setTransform(-113.3,-77.5);

	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f("#FFFF33").s().p("AB7NQIAAgfQh7ASh3gJIghgDIAhADIgDAZIjxA3Qg5AJAOhDIBsgqIAJAFIgJgFQjniJBOmjQAKAFALAFIABAAIAJAAIAAAAIABAAQBSAABIhjIABgDIABgBIABgBIAAgBIABgBQAAhDgqgOIhDADIBDgDQAqAOAABDIgBABIAAABIgBABIgBABIgBADQhIBjhSAAIgBAAIAAAAIgJAAIgBAAQgLgFgKgFQhLgtArhlICBjIQiRibBji1QgEgYAAgXIAAgFQAAhXAzhHQgzBHAABXIAAAFIhUgEIgEgxIBagEIhaAEQhMAChFgEQhmghg3g5QCGg3CHgdIAAAEIAAgEQBfgVBmgNIADAAIAtgcIAEABIgEgBIgtAcIgDAAQhmANhfAVIgSiLIEHBNQEAiTD7BjQEghRDkgNQgOBOhhBMQhxA+ifBEQgHgTgIgQQAIAQAHATQAfBOAGBmIAAADQBMCZhgCWQDXhYB3icQA1gGAfA3QhXDvjXDjQAlHKjmDaIBcArQARAwgdAQgAB7MxIAigFgAHtCBIAAgHgAlihgIAvAsIgvgsIABgDIgBADgAFShUQAWgeARgcQgRAcgWAegAEEslIghgNIgEABIAEgBg");
	this.shape_15.setTransform(-111,-77.3);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#000000").s().p("AttCTIAHgEQAygXAygUQA2A4BmAiQiXgJhwgigAL+i6IBrgDIAFAAIgDAEQhFBHiXBSQBhhNAOhNg");
	this.shape_16.setTransform(-113.3,-149.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15},{t:this.shape_14}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-202.2,-169.9,177.7,184.9);


(lib.shape35 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 5
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgNgQQADgLAIgFIAOgBQAIAEADALQACALgDAIQgEAMgIAEIgNABQgJgEgDgKQgCgIAEgMgAAHgwIAIADIAIAGQAGAHAEAMIAAABQAFARgGASQgHAUgOAIIAAABIgNAEQgGAAgGgDIgKgIIgIgRQgFgTAHgRQAHgUANgHQAKgHAHABgAABAhIgBAEIgEgBIgDgDIAAgFIADgCIAEgBIABAEg");
	this.shape.setTransform(-186.2,-99.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AgHAjIgDgEIgBgFIADgCIAEAAIAEAEIAAAEIgEAEgAgSACQgCgIADgLQAEgLAHgFIAOgCQAJAFACAKQADALgEAJQgEALgIAFIgNABQgIgEgDgLg");
	this.shape_1.setTransform(-185.8,-99.4);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgOAuIgKgIIgIgRQgFgTAHgRQAHgUANgHQAKgHAHABIAIADIAIAGQAGAHAEAMIAAABQAFARgGASQgHAUgOAIIAAABIgNAEQgGAAgGgDgAgEAaIgDACIAAAFIADADIAEABIABgEIAAgEIgBgEgAgCggQgIAFgDALQgEAMACAIQADAKAJAEIANgBQAIgEAEgMQADgIgCgLQgDgLgIgEg");
	this.shape_2.setTransform(-186.2,-99.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 4
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgPgzQANgHALABIAMADIAMAHQAJAIAGAPIgBABQAHAUgJAVQgKAYgTAKIAAABIgUAFIgRgEQgJgDgGgGQgHgJgEgLQgHgXAKgVQAJgXAUgJgAgEgmQAKgHALAFQAMAFADANQAEANgFAKQgGAOgLAFIgUACQgMgFgEgNQgDgKAFgNQAFgNALgGgAABAnIgDAFIgFgBIgEgEIAAgGIAEgDIAFAAIADAFg");
	this.shape_3.setTransform(-136.5,-110.4);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AgLAqIgEgEIgBgGIAEgCIAGgBIAFAFIAAAFIgFAEgAgbAEQgDgKAFgOQAGgNAKgFQALgHAKAFQAMAFAEAMQADANgFALQgFAOgMAFIgTABQgMgFgFgMg");
	this.shape_4.setTransform(-136.1,-110.3);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgVA2QgJgDgGgGQgHgJgEgLQgHgXAKgVQAJgXAUgJQANgHALABIAMADIAMAHQAJAIAGAPIgBABQAHAUgJAVQgKAYgTAKIAAABIgUAFgAgHAeIgEADIAAAGIAEAEIAFABIADgFIAAgEIgDgFgAgEgmQgLAGgFANQgFANADAKQAEANAMAFIAUgCQALgFAGgOQAFgKgEgNQgDgNgMgFQgEgCgFAAQgHAAgFAEg");
	this.shape_5.setTransform(-136.5,-110.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 3
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ajwi8QAKAZgEAiQgEAigPAWQgPAWgRgCQgSgCgJgZQgKgZAEgiQAEgiAPgWQAPgWASACQARACAJAZgAinh1QAIACAAAFQABAFgGAEQgGAFgKABIgRgBQgHgCgBgFQgBgFAHgEIAPgGgAjuAgIAigCIAYA6IAIAUIAiBLQBKChAigLQAmACAJiYQADgsAAg5IAAgbIgDg1QARgEARgJAjMAeQAhgLAOgrQBhAyBNgYAEnkyQAZAcAAAmQAAAmgZAaQgZAcgjAAQgkAAgZgcQgZgaAAgmQAAgmAZgcIAPgMQAUgOAaAAQAaAAAUAOgAi0BYQBbg+BtAd");
	this.shape_6.setTransform(-157.8,-84.8);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FF6666").s().p("Ag5gFIgihLIgJgUQBcg+BsAdIAAAbQAAA5gCAsQgJCWgogCIgFAAQgiAAhDiUg");
	this.shape_7.setTransform(-165.8,-65.8);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("rgba(255,102,153,0.859)").s().p("AnvAaIAcAXQAtAtgBA/IgBATQg5hJgOhNgAD1BWIgFgEQgrgsAAg9QAAg/ArgtQAegeAngKIAlgEQA/AAArAsQAsAtAAA/QAAA9gsAsQgrAtg/AAQg6AAgrgpg");
	this.shape_8.setTransform(-149,-75.1);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#660000").s().p("AhvAAQAhgKAOgtQBeAzBPgYIADA0QhugbhaA7g");
	this.shape_9.setTransform(-167,-81.6);

	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("#000000").s().p("AkdCLQgSgCgJgZQgKgZAEgiQAEgiAPgUQAPgWASACQARACAJAXQAKAZgEAiQgEAigPAWQgOAUgQAAIgCAAgAjFBgQgHgCgBgFQgBgFAHgEIAPgGIARABQAIACAAAFQABAFgGAEQgGAFgKABgACuAQQgZgYAAgmQAAgmAZgcIAPgMQAUgOAaAAQAaAAAUAOIAOAMQAZAcAAAmQAAAmgZAYQgZAcgjAAQgkAAgZgcg");
	this.shape_10.setTransform(-157.8,-104.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 2
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#CCCC00").s().p("AiQgYQCFAGCcgsQg4BihiAaQh0gGgThQg");
	this.shape_11.setTransform(-147.8,-34);

	this.timeline.addTween(cjs.Tween.get(this.shape_11).wait(1));

	// Layer 1
	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACah5IhDiuQJ/jqLQArIibJUQmaglmFAwIBAGgIjHBEIBBDQIh1BCQgoDuhoBBQhhAui7gCIggESQh0ClheiIIh0jLIgsiuAkZ2eQBqhgB+g4QgrDLhXDBQhwD4i4DpQByBxAOCsQAPCsAHAtQAGAuArBnQDDhXDrAcQB2AOCAArIADAAQBCA9gXBgQhFA/hJA5IBMC7Ii7CAIB+ECIgmAbQgehdgzhTIg1hNQg4hKhWhJQCehaCNhuAx4xJQg0hmiGhaQAriUBOiRQBFDvgED2QgCCSgcCaQEziHDWBsQCLmTDpjTAzEsIIAugVA1BpxQAXgxAggwA1BpxQhPlUBelEAywDDQhshJg9hNQg6hIgNhMQgThlA+hrIAAgBQgRilBFiUAywDDIA1AiAqNs4IAFACAnbrJIgbAhAmJAdQA2g1A/gmAAFGdQg0Aeg4AcAizyqQgTithThHADIJ9IAFgBAh3SsIgEAeAk7NRQjPgLgfDHIAcBzIimBHIkTD4QigAvgUh7QAriCBIh4IgWgVQhZiuAdi5IAAgcQjvjbCYlDAx7GKQApCzgHCk");
	this.shape_12.setTransform(-54.5,-62);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#000000").s().p("Ap/A2QAriSBOiRQBFDtgED2Qg0hmiGhagAGYhdQBrhgB9g4QgrDLhXC/QgTirhThHg");
	this.shape_13.setTransform(-123.6,-196.5);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("AltWtIh0jLIgsiuIgchzIAAgDIAAAAQAfi5C6gBIAAAAIAAAAIAVABIgVgBIAAAAIAAAAQi6ABgfC5IAAAAIAAADIAcBzIimBHIkTD4QigAvgUh7QAriCBIh4IgWgVQhZiuAdi5IAAgcQjvjbCYlDIA1AiIg1giQhshJg9hLQg6hKgNhMQgThlA+hrIAAgBQgRilBFiUQAXgxAggwQggAwgXAxQhPlUBelEQCGBaA0BmQgCCSgcCaIguAVIAugVQEziHDWBsQCLmTDpjTQBTBHATCtQhwD4i4DpQByBxAOCsQAPCsAHAtQAGAuArBnQDDhXDrAcIhDiuQJ/jqLQArIibJUQmagjmFAuIBAGgIjHBEIBBDQIh1BCQgoDuhoBBQhhAui7gCIAEgeIgEAeIggESQhABbg5AAQgvAAgqg+gAAFFRQBWBJA4BKIA1BNQAzBTAeBdIAmgbIh+kCIC7iAIhMi7QiNBuieBagAxZKVIABguQAAiPgjiaQAjCaAACPIgBAugAhnGLQA4gcA0geQg0Aeg4AcgAG+ARQhFA/hJA5QBJg5BFg/QAGgYAAgXQAAhAgxguIgDAAQiAgrh2gOQB2AOCAArIADAAQAxAuAABAQAAAXgGAYgAmJgtQA2g3A/gmQg/Amg2A3gAn2r0IAbghgAqIuCIgFgCg");
	this.shape_14.setTransform(-54.5,-54.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13},{t:this.shape_12}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-200.1,-222.1,291.3,320.2);


(lib.shape34 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 13
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ABaAZQhVAYhehO");
	this.shape.setTransform(-45.7,-182.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 12
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgNAIQgGgDAAgFQAAgFAGgFIANgDIAOADQAGAFAAAFQAAAFgGADQgGAGgIAAQgHAAgGgGg");
	this.shape_1.setTransform(-30.5,-213.1);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 11
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAoAAQAAAOgLAJQgMAOgRAAQgQAAgLgOQgMgJAAgOQAAgNAMgPQALgIAQAAQARAAAMAIQALAPAAANg");
	this.shape_2.setTransform(-29.9,-212.3);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgbAXQgMgJAAgOQAAgNAMgPQALgIAQAAQARAAALAIQAMAPAAANQAAAOgMAJQgLAOgRAAQgQAAgLgOg");
	this.shape_3.setTransform(-29.9,-212.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 10
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAmAyIgBgCIAHgCIADAAIACAEIgFAGgAgjAAQgMgRgBgSQADgNAMgHIASABQARAKALASQAMAPgCAMQAAANgMAIIgTABQgQgIgLgPg");
	this.shape_4.setTransform(-58.3,-224.5);

	this.timeline.addTween(cjs.Tween.get(this.shape_4).wait(1));

	// Layer 9
	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#E3E300").s().p("AgbAdIgUgLQAoAQAZgLQAdgUgEgsIAFAKQgBAsgOAUQgIAJgNAAQgPAAgYgNg");
	this.shape_5.setTransform(-54.9,-213.3);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#000000").s().p("AAFBvIgDgBIgBgCIgQgKIgKgIIgBgBIgBAAQgRgVgRgdQgegsgGgsQAEgvAegMQAdgUAiAPQArAYAdAxQAdAtgEAtQAEAtgdAVQgLAEgNAAQgUAAgXgJg");
	this.shape_6.setTransform(-60.3,-222.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5}]}).wait(1));

	// Layer 8
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("rgba(255,102,153,0.859)").s().p("AgjCVQg9gQgmg3Qgjg4APg5QAOg8A2gjQA8ghA8APQBAAPAiA3QAmA3gPA7QgOA8g7AhQgmAYgrAAQgQAAgUgEg");
	this.shape_7.setTransform(-95.8,-182.2);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 7
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACgkDQBXFPgQAXQgIAmjfBDQjeBEgKgOQgJgOB8j7IAUgo");
	this.shape_8.setTransform(-68.6,-16.9);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#FFFF33").s().p("AjoECQgJgOB8j7IAUgoIASgkICdhxIBSg/QBXFPgQAXQgIAmjfBDQi4A4gmAAQgIAAgCgCg");
	this.shape_9.setTransform(-68.6,-16.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 6
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AiZDSQDLgpDBhjQBXhGhCg9QjTh1lYgf");
	this.shape_10.setTransform(-73.3,-126.5);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("AkjjSQFYAgDTB1QBCA9hXBGQjBBjjLAqg");
	this.shape_11.setTransform(-73.3,-126.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_11},{t:this.shape_10}]}).wait(1));

	// Layer 5
	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Anvi1QAKCOC5BfQE7BWF+AvAnvi1QilANiyAeQD2B1EkBNANHBzQn2lbtAAz");
	this.shape_12.setTransform(-174.8,-275.1);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#000000").s().p("AkLhLQCygeCigNQAKCMC5BhQkihPj1hzg");
	this.shape_13.setTransform(-231.9,-281.4);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("AnYA4Qi4hfgKiOQNAgzH1FbIiAAAIAAAcIjzAWIhGAYQl8gvk+hWg");
	this.shape_14.setTransform(-157.7,-275.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13},{t:this.shape_12}]}).wait(1));

	// Layer 4
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAAz9QEXAADFDAQDGDBAAERQAAERjGDBIgQAPQCsDvAAFOQAAFdi8D3Qi9D3kIAAQkKAAi8j3Qi8j3AAldQAAlWC0jyIgEgEQjGjBAAkRQAAkRDGjBQDFjAEWAAg");
	this.shape_15.setTransform(-99.5,-141.4);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AnPQHQi8j3AAldQAAlWC0jyIgEgEQjGjBAAkRQAAkRDGjBQDFjAEWAAQEXAADFDAQDGDBAAERQAAERjGDBIgQAPQCsDvAAFOQAAFdi8D3Qi9D3kIAAQkKAAi8j3g");
	this.shape_16.setTransform(-99.5,-141.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 3
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ALPADIgDgCQnejequCsAj2BxQiogygihwAj2BxQj8gUjcg6QCKgwCEgkAH5BnQmcAmlTgc");
	this.shape_17.setTransform(-180.7,-262.6);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#000000").s().p("AjrAEQCLgxCCgkQAhBxCpAyQj6gUjdg6g");
	this.shape_18.setTransform(-229.1,-259.4);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#FFFF33").s().p("Al8BxQiogyghhwQKtisHeDeIjTBmQjtAWjUAAQidAAiRgMg");
	this.shape_19.setTransform(-167.4,-262.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 2
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhVhsQinDpBDgSQDGAKDAil");
	this.shape_20.setTransform(-101,-12.8);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("AhfhqIAIgDIEuAXIgUAoQjACljGgKIgIABQgvAACbjYg");
	this.shape_21.setTransform(-100,-13);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

	// Layer 1
	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Al/mLIl+BoQEBMJKoFlQFqidDokcQoLjBn5nNIDLh+IkJj8IDoifIgWgUIgjghImSCOg");
	this.shape_22.setTransform(-132.1,36.5);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#FFFF33").s().p("Ar9kjIF+hoIiokxIGSiOIAjAhIAWAUIjoCfIEJD8IjLB+QH5HNILDBQjoEclqCdQqollkBsJg");
	this.shape_23.setTransform(-132.1,36.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-259.7,-295,234.9,416.9);


(lib.shape32 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#CC9900").ss(1.5,1,1).p("AHVriQATgcANAiQAVA5gzgKQgpAAAng1gACECcQADgZAMgZQgog3AwgzQAogqA+ACQA6ACAjAsQAkAwgLA8IgEASQgOAtggAlQglAqg2ANQhBAQgbg+QgOghAEgigAN/APQAwgqApAxQAqA1gtAsQggAeglgZQgIgGgMgVQg4gdA7g1gAGWJ7QALg/ApAYQATALACAZQAEA+gxgOQgnAPALg8gAvZgWQA2gPgMA2QgFAYgOABQgcAIgMguQgGgTAXgHgAhZrbQAJgxgeg3QgthTg9AwQgZgWgSAVQgkAoAWApIANATQgMAggCAaQgDAgAQAeQAmBHBIg8QAxgnALgugAmKoIQgSgBgTgJQgzgIApg0QAJgLAOABQBIgNgNBDQgGAbgdgBgAqVkQQAahIA2AsQASAPAHAQQAMAggiAiQgtAugkgtIgIgLQgcgrAigQIATgGAmThvQgQg7A1gdQApgWAqAYQAyAeAAA+QgBA4gjAtQgnAvg7gbQg7gZAShCQAFgQAJgOIAJgMApMB6QgBAwgvADQgohDA1gVQAlgXgCA8gAokIBQAOgMAPAOQAjg5A6A4QAdAcgMApQgVBMhEACQgSABgGgRIgWgyQhEgbBAg3gAkuMxQAUgOAPASQAkAvhFAUQg8gaA6gtg");
	this.shape.setTransform(0.2,-0.9);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#E2BE16").s().p("AEfHnQgPghAFgiIABgCQAfgjAngSQAngSAsAJQAsAJAjAbIACACIADACQgNAtggAlQglAqg3ANQgNADgMAAQgtAAgVgxgAoCA8IAJgSQAhg1A8AGIAAAAQANAegiAiQgXAXgUAAQgUAAgSgWgAh1mDQgQgeAEggQACgaAMggIgNgTQA0gVAvAfIgVANIAXgFQAjgCAtArIgBAGQgLAugvAnQghAbgaAAQgfAAgVgmg");
	this.shape_1.setTransform(-14.3,-27.3);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#FEEB9E").s().p("AkuMxQAUgOAPASQAkAvhFAUQg8gaA6gtgAGWJ7QALg/ApAYQATALACAZQAEA+gxgOQgIADgGAAQgXAAAJgwgAoKKFIgWgyQhEgbBAg3QAOgMAPAOQAjg5A6A4QAdAcgMApQgVBMhEACIgBAAQgRAAgGgQgApvBVQAlgXgCA8QgBAwgvADQgohDA1gVgACTBqQgog3AwgzQAogqA+ACQA6ACAjAsQAkAwgLA8IgKAOQgjgbgsgJQgsgJgnASQgnASgeAjIgCACQADgZAMgZgAOQB8QgIgGgMgVQg4gdA7g1QAwgqApAxQAqA1gtAsQgRAQgTAAQgQAAgRgLgAvqAEQgGgTAXgHQA2gPgMA2QgFAYgOABIgIABQgWAAgKgngAlvAQQg7gZAShCQAFgQAJgOIgJgGQgQg7A1gdQApgWAqAYQAyAeAAA+QgBA4gjAtQgZAegiAAQgSAAgVgKgAmKhpIAJgMgAqbjVQgcgrAigQQAahIA2AsQASAPAHAQIgBAAQg8gGghA3IgJASgAqVkQIATgGgAmKoIQgSgBgTgJQgzgIApg0QAJgLAOABQBIgNgNBDQgGAagaAAIgDAAgAHXqtQgpAAAng1QATgcANAiQASAwgiAAIgOgBgAissEIgCgIQgvgfg0AVQgWgpAkgoQASgVAZAWQA9gwAtBTQAeA3gJAxQgugrglACg");
	this.shape_2.setTransform(0.2,-0.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-101.2,-90.8,202.9,179.8);


(lib.shape31 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#CC9900").ss(1.5,1,1).p("AD4q5QAYgaAcAbQAsArg1AYIgYADQhHgOA0g5gAKsBeQAJASABAYQACA9gxAdQhBAngVhAQgEgKADgNIgRgKIAAAAQglgdAdgwQAig5BAAUQAlALAOAdgABPD+QgzAVgfgzQg/gLAYhLQASg3A4AJQApAFAiAUQAaAPACAiQADBAg7AYgACPAnQAVgnAVAkQAXAngqAMQgxACAagygADwKZIAFgHQAmg2gqgyQgqgzg6AcQgVAKgNAOQgYAZAJAmQgRAPAAAWQADBJBJgMQA4gJAhgqgAkkq+IAKAJQATAOAcgDQBZgHAZhaQAFgSAAgQQAAgugrgYQg0gfgeA1QgPgagbARQg/AqA1AoQgkAtAeAjgAnIn0QAcAPgKAfQgJAZgpgBQgxgJAtg2QAPgSAVALgAnahcIAWgGQAfg4A0A1QAUAVAHAdQAPA5gvAmQgvAng7gOQhBgOAJg/QAIg+A2gWgAohjXQAtAIgFAwQgFA7g8AVQhCAYgIhDQgCgWAXgSQAQhBA+AMgAqaCFQAPgIADARQAHAsgigFQgqgVAzgbgAm3J5QAEBCgsAzQgqAvg2ghQghgTAFgpQACgPgBgKQg5gjA9gtQALgIALALQAohGBEAxQAaATADAhgAmRMyQAGgEAIgPQAjhBArA/QARAagOAbQgeA5g3gOQhIgjA+gog");
	this.shape.setTransform(-1.7,-0.2);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FEEB9E").s().p("AmCN9QhIgjA+goQAGgEAIgPQAjhBArA/QARAagOAbQgYAtgoAAQgKAAgLgCgAo6L8QghgTAFgpQACgPgBgKQg5gjA9gtQALgIALALQAohGBEAxQAaATADAhQAEBCgsAzQgZAcgdAAQgUAAgWgOgADiJ2Qgog5goALQgCgPgPgIIgRgGQANgOAVgKQA6gcAqAzQAqAygmA2IgFAHQgGgRgNgSgAAADgQg9gLAYhLQASg3A4AJQApAFAiAUQAaAPACAiQADBAg7AYQgPAGgNAAQggAAgYgkgAqeC1QgqgVAzgbQAPgIADARQAHAogbAAIgHgBgAIcBbQAig5BAAUQAlALAOAdQghgEggARQgnAVggAjIgFAFQglgdAdgwgACUAnQAVgnAVAkQAXAngqAMIgEAAQgsAAAZgwgAnbBFQhBgOAJg/QAIg+A2gWIAWgGQAfg4A0A1QAUAVAHAdQAPA5gvAmQgiAdgqAAQgOAAgQgEgAp/h6QgCgWAXgSQAQhBA+AMQAtAIgFAwQgFA7g8AVQgRAGgNAAQgmAAgGgxgAnjmuQgxgJAtg2QAPgSAVALQAcAPgKAfQgJAYglAAIgEAAgAD9q5QAYgaAcAbQAsArg1AYIgYADQhHgOA0g5gAkgsUQg1goA/gqQAbgRAPAaQAeg1A0AfQArAYAAAuQgPgKgRgHQgzgVglAjQgrAqgLA7IgJAHQgegjAkgtg");
	this.shape_1.setTransform(-2.2,-0.2);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#E2BE16").s().p("Ah7LLQgBgVASgQQgJglAYgaIAQAHQAPAHADAQQAogLAmA4QAMASAHARQghAqg2AJQgLACgJAAQg2AAgCg/gAFoEGQgEgLADgMIgQgKIAEgGQAggjAogVQAfgQAiAEQAJARABAYQACA9gxAeQgZAPgSAAQgeAAgOgogAnip5IgLgIIACgNQALg8ArgpQAlgjAzAUQASAHAOAKQAAAQgFASQgZBahZAIIgIAAQgWAAgQgMg");
	this.shape_2.setTransform(18.4,-6.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-72.2,-90.7,141,181.2);


(lib.shape30复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#CC9900").ss(1.5,1,1).p("AAvrrIAFgZIABghQAAgSgHgQQgHgSgMgPQgLgOgPgLQgDgDgFAAQgSgBAAgNQAAgUgNgRQgLgNgRgHQgRgGgTgBIgjAEQgUAGgOAOQgOANgFAUQgEATABAUQACATAHARQAHASAQALQgSAMgEAUQgEAQACARIAAAFQADAUANAPQANAPAQAJQAPAJAQAHQARAHASACIAjgDQASgGAQgKQANgLAMgNQANgMAIgQgACmqaIgLgNQgJgQABgTQABgTANgOQAPgOATgDQATgCASAHQATAHAKARIADAFQAHAOACAQQACAUgJASQgKASgSAHQgTAGgQgKIgUgRQgKgCgHgGgAE1EHIgBgBQgMgNgGgRQgFgSABgTQACgUAGgTQAFgRAMgPQAMgQATgHIAlgEQAUADAQALQAKAHADALQAKgHANAAIASABIAXADIAbAHQAMAEALAIQATAOAOATQAQAWADAdQACAVgDAUIgIAdQgTAyguAbQgiAUgoAAQgZABgXgJQgagLgQgWQgKgOgFgQQgEgPgBgQQAAgGAFgIQgTgEgNgNgACAA7QgNgEgHgSQgGgOAIgMQAMgPAVAEQATABAJATQAIAUgOAPQgKAKgNgDgABJDrQAAAUgHASQgHARgMAMQgNAOgTADIgkgCQgSgEgNgOQgNgOgIgSQgDgIABgJIgBgJQgVAAgJgSQgJgRAFgSQAFgTAMgPQANgPAUgGQARgGAQAJQATALAIAJIAHAEIAgAPQARAIAJARQAJARgBASgADUJ9IAEglQACgagLgYQgOgcgUgWQgTgSgZgJQgRgHgSgCIgUABQgUADgTAKQgMAGgJAJIgQATQgSgEgTAEQgUADgJATQgJARgDATQgEARACASQADATAKARIAGAJIARAPQAOAKARgBQgGANAHANIAQAdQAJAMANAIQAKAHAMAFIAeADIAYgFIAfgPIAcgTIAVgUIARgXQAIgOADgOgAnIldQAAgUAPgNQAPgNAUABQAUABAIARIACAMQABAUgOAOQgPAOgRgDIgOgDQgVgGAAgVgAn3A6QgMgMgGgRIgMgiQgFgRAAgRQAAgUAIgSQAIgRALgPQALgOAPgKQAQgLATgCQAVgDAMAQQAIAKADALQAOgIARADIAYAHIASAHIAZAKQATAKANAQIASAWQAKAQADARIABAMQABARgGATIgUA0QgRAqgqALIhBAFQghgEgcgUQgPgMgHgSQgFgKADgJQgNgDgLgLgAoeNmIgFgFIgBgCIgUgWQgLgPgHgSQgIgSAAgUIADgmQADgTAIgSIAQgiQAJgPAMgMQAMgOAQgIIATgJQASgIATgCIAlACQASAEALAOIAOANQAGgIAMgDIAXgCIAZACIAZAHIAXAOQANAJAJANQAKAPAIAPQAIARAEARQACANAAAMIgBAMIgCAGIgFALQgDAHAIADIAJAHIALARQAIARgCATQgBANgEAMQgFAPgJANQgIAMgLAKIgQAMIgUALIgWAOQgQALgTAFQgpAMgmgUQgegPgYgWQgTgSgLgWIgDgJQgQAIgSgFQgOgEgJgJg");

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#E2BE16").s().p("AmnN8QgegPgYgXQgTgRgLgWIgDgKQgQAJgSgGQgOgEgJgJIADgOQALgtAaglQAcgqAygQQAvgOAwAJQAqAJAnATIAkAUIgCAHIgFALQgDAHAIADIAJAGIALARQAIASgCATQgBANgEAMQgFAPgJAMQgIAMgLAKIgQAMIgUAMIgWANQgQALgTAFQgQAFgPAAQgZAAgXgMgAAVKuQgMgEgJgHQgMgJgJgMIgSgdQgHgNAGgNQgRABgOgKIgRgPQAMgdATgYQAbgjAngPQAwgSAxANQAfAJAUAYIAOAWIAQAoIgEARQgDAPgIANIgRAYIgVATIgcATIgfAPIgYAGgAF1E5QgagKgQgXQgKgNgFgRQgEgPgBgQQAAgFAFgJQgTgEgNgNIAHgPQAZguAxgOQAwgOAyAMQAvAKAfAlIAbAlIgIAeQgTAxguAbQgiAUgoABIgDAAQgXAAgWgJgAnfAvQgPgLgHgSQgFgKADgIQgNgDgLgKIgBgBIAGgeQAMgvAogcQAngaAuAGQAlAEAfATIAMAIQAVAOAPAUQABATgGATIgUAyQgRApgqALIhBAFQghgDgcgVgACyrMIgUgQQgKgDgHgGIAsgmQAYgYAegBQAHAOACAPQACAUgJASQgKATgSAGQgHADgHAAQgLAAgKgHgAiGrrQgQgHgPgIQgQgKgNgOQgNgPgDgUIAAgFQAQgkAjgWQApgbAwANQAxANAbApIAQAXIgGAMQgIAQgLANQgMANgPAKQgQALgSAFIgjAEQgSgCgRgIg");
	this.shape_1.setTransform(2.5,7.5);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#FEEB9E").s().p("AojOYIgBgCIgUgXQgLgPgHgRQgIgSAAgVIADglQADgTAIgTIAQghQAJgPAMgNQAMgNAQgIIATgJQASgIATgCIAlACQASAEALANIAOAOQAGgIAMgDIAXgDIAZADIAZAHIAXANQANAKAJANQAKAOAIAQQAIAQAEASQACAMAAANIgBALIgkgUQgngTgqgJQgwgJgvAOQgyAQgcAqQgaAlgLAtIgDAOgAhFK3QgKgQgDgUQgCgRAEgSQADgTAJgRQAJgSAUgEQATgEASAFIAQgUQAJgJAMgGQATgKAUgDIAUAAQASACARAGQAZAJATATQAUAVAOAdQALAYgCAZIgEAlIgQgoIgOgWQgUgYgfgJQgxgNgwASQgnAPgbAjQgTAYgMAdgAgVF0QgSgFgNgNQgNgOgIgSQgDgIABgJIgBgJQgVgBgJgSQgJgRAFgSQAFgSAMgPQANgQAUgGQARgGAQAJQATALAIAJIAHAEIAgAPQARAIAJASQAJAQgBATQAAATgHASQgHARgMANQgNAOgTADgAI2EfQgfglgvgKQgygMgwAOQgxAOgZAuIgHAPIgBAAQgMgNgGgRQgFgSABgUQACgUAGgSQAFgSAMgPQAMgPATgHIAlgEQAUACAQAMQAKAGADALQAKgGANAAIASABIAXADIAbAGQAMAFALAIQATAOAOATQAQAVADAdQACAVgDAUgACOB0IgOgDQgNgEgHgSQgGgNAIgNQAMgQAVADQATAEAJATQAIATgOAPQgHAIgKAAIgGgBgAoJBTIgMgjQgFgRAAgSQAAgRAIgTQAIgRALgPQALgOAPgKQAQgKATgDQAVgDAMAQQAIALADALQAOgJARADIAYAHIASAHIAZALQATAJANAQIASAVQAKAPADASIABAMQgPgUgVgOIgMgIQgfgRglgEQgugGgnAYQgoAcgMAvIgGAeQgMgNgGgRgAmlkJIgOgCQgVgGAAgWQAAgTAPgOQAPgMAUABQAUAAAIASIACALQABAUgOAOQgMAMgOAAIgGgBgACbpxQgJgQABgTQABgTANgNQAPgOATgDQATgDASAHQATAHAKASIADAFQgeABgYAYIgsAmgAAfrLQgdgpgvgNQgwgNgpAbQgjAWgQAkQgCgRAEgRQAEgTASgMQgQgLgHgSQgHgSgCgSQgBgUAEgTQAFgUAOgOQAOgOAUgFIAjgEQATAAARAHQARAGALAOQANAQAAAUQAAANASABQAFAAADAEQAPAKALAPQAMAOAHASQAHARAAARIgBAiIgFAZg");
	this.shape_2.setTransform(0,-5.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-60.5,-98.9,121.2,197.8);


(lib.shape29复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#CC9900").ss(1.5,1,1).p("AKzo5QAHgPAOANQAqAlgkAUQg6AHAfg+gABwq1QAmAyglAuQgkAuhGgGQgzgXACg+QACg7AzgVQA9gXAoA0gAB6koQAIgJAOAAQBKgBgTBEQgPA0glgjQhHgSAug5gAFcDIQANhLgxAPQAKgPABgSQACgygtgRQhLgdgGBUIgQgYQgNgRgUAAQhBAAAXBEIgLAFIgCABQgaANgbAWQgfAZgLAoQgLAwAgAeIAUAOQA5AfArgkQAMAOAQAJQAuAcA2gBQA9gCAag2QAQgigEgWQgFgWgagLQAHgIADgJgAlhp0QgJgIgHgLQgTgcAUgeQAZgmAfgGQgfg7BAgZQAagLAVARQA9glA1ApQAwAlgNA8IgDAMQgQAzgzAaQg1Abg6gJQg3AVgigegAm0lEQANAZgRAWQgrA4g6gUQgUgHgNgZQgehJBFAAQA8g4AnBOgAqGBSQhGgUgQhTQgHgpAXghQAggsA6AGQglgqBDgXIA5ACQAhANAaAUQAdhHBEA0QAaAUACAjQABAYgQAQQA6gFARBPIABAFQAPBShMAtQgMAHgPgBQgfgBgbgOQhIBWhHhUQgIgJABgPQggABgYgHgAi+G2QgvACgXAwQgLAYADAcQAGA8A9APQA3ANAxgcQA5ghgFg/QgEgxgrgNQAeg7g6gcQglgTgZAkQgWAgAOAiIAWABApmIZQgBgxA2gLQAYgFAUARQAKAJAJAMQATg6BFAEQBPAEAIBNQA1AQAAA2IAAAFQgCAOgNAKIgVAOQAdArghAxQgqA/hGAeQg2AWg1gWQg5gXALhDIAEgWQgmAIgdgQQgPgJgMgOQgpgwATg4QAMgiAhgOgApjIvIgDgW");
	this.shape.setTransform(0.2,0.3);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#E2BE16").s().p("AmFMPQg5gYAKhCIAEgWQglAIgdgRIAJggQAVg4A4gSQA7gTArAqQgCggAcgOQAkgSAoAHQAsAIAfAbIACACQgBAPgOAKIgUAOQAdAqghAyQgqA+hHAeQgbAMgbAAQgbAAgZgLgAE1EeQgQgKgMgOQgsAlg4ggIgVgOIAGgfQANgwAXgtQAQgfAYgKIAIgCQAVgGAaAJQAiAMAFAiIAOgVQATgbAhALQA2ARAeAjIABABQgDAJgIAHQAaALAFAWQAFAXgQAhQgaA2g9ACIgGABQgzAAgrgbgAm7A8QgJgKACgOQggABgZgHQASg0AigtQAggsA3gEQgYAegGAlIgDAcIANgRQARgSAggUQATgMAWgHQA4gQAzAaQATAKARANQAQBShMAtQgMAHgPAAQgggBgbgPQgkAsgkAAQgkAAgigpgAjWqpIAOgcQAfg5A7gJQAugHgFApQAFgPALgNQAYgcAjAFQAdAEANARIADAEQgQA0gxAaQg1Abg5gKQgXAJgUAAQgbAAgUgSg");
	this.shape_1.setTransform(-13.8,5.5);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#FEEB9E").s().p("AqZL1QgpgvATg5QAMghAhgOIAcgDQgBgyA2gLQAYgFAUASQAKAJAJALQATg6BFAEQBPAFAIBNQA1AQAAA2IgCACQgfgbgtgIQgngHglASQgcAOADAgQgsgqg6ATQg5ASgUA4IgKAgQgPgIgMgPgApjJwIgDgVgAjJKoQg9gPgGg7QgDgcALgYQAXgwAvgCIAWABIgWgBQgOgjAWgfQAZgkAlASQA6AdgeA7QArANAEAwQAFA/g5AhQgiAUgkAAQgRAAgRgFgAAAEmQALgoAfgaQAbgWAagNIAIANQgYAKgQAfQgXAtgNAwIgGAfQgggeALgvgAEHDYQghgLgTAbIgPAVQgFgighgMQgbgJgUAGIgDgQQgXhFBBAAQAUAAANASIAQAXQAGhTBLAcQAtARgCAyQgBATgKAPQAxgPgNBLIgCACQgegjg1gRgArcAqQgHgoAXgfQAggsA6AGQglgqBDgXIA5ABQAhANAaAUQAdhGBEA0QAaAUACAjQABAXgQARQA6gGARBOIABAEQgRgNgTgKQgzgag4AQQgXAHgTAMQggAUgQASIgKgLQAFglAZgeQg4AEggAsQghAtgSA2QhGgVgQhVgACTicQhHgSAug4QAIgKAOAAQBKAAgTBDQgJAfgRAAQgLAAgPgOgAodivQgUgHgNgaQgehJBFAAQA8g4AnBOQANAZgRAXQggApgoAAQgOAAgPgFgAKzn4QAHgPAOANQAqAlgkAUIgLABQgsAAAcg4gAAHnrQgzgXACg/QACg7AzgUQA9gYAoA1QAmAxglAvQggAog8AAIgOAAgAlxpFQgTgcAUgfQAZglAfgHQgfg6BAgaQAagKAVAQQA9glA1ApQAwAlgNA9IgGAHQgNgRgegEQglgFgYAcQgLANgEAPQAEgpguAHQg6AJggA5IgNAcQgJgHgHgLg");
	this.shape_2.setTransform(0.2,-6.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-74.3,-85.3,149.1,171.3);


(lib.shape28复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 13
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ABYggQg0BFh7gE");
	this.shape.setTransform(-62.5,-246.1);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 12
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgOANQgEgFABgIIAKgJIALgGQAIABAEAEQADAFgDAFQAAAHgHAFQgHAFgHgBIgDAAQgEAAgCgDg");
	this.shape_1.setTransform(-64.6,-275.1);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 11
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAmACQAAASgOALQgOALgQgFQgOAAgJgMQgJgLAAgQQAEgOAOgLQAOgLAMAAQASAFAJALQAJALgEANg");
	this.shape_2.setTransform(-63.8,-274.8);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgGAlQgOAAgJgMQgJgLAAgQQAEgOAOgLQAOgLAMAAQASAFAJALQAJALgEANQAAASgOALQgKAIgMAAIgIgCg");
	this.shape_3.setTransform(-63.8,-274.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 10
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AjGEKQjZA3jQAJQBfh+B0hnQAfCtC3gIQFEhRFZi5AJwlJQp+BLmOFj");
	this.shape_4.setTransform(-216.7,-239.5);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AAAhxQAdCrC3gHQjXA2jQAJQBfh9B0hmg");
	this.shape_5.setTransform(-257.9,-217.9);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#FFFF33").s().p("AoGCFQGPljJ+hLIhADJIhZB+QlZC6lEBSIgPAAQiqAAgeilg");
	this.shape_6.setTransform(-206.2,-242.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5},{t:this.shape_4}]}).wait(1));

	// Layer 9
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFFFF").s().p("AgcAZQgVgFgOgMQgGgJAEgOQACgHAMgEIApgBQARAEAIALQAJAKgEAMQgDANgLACQgHADgJAAQgJAAgJgDgAA8AMIgDAAIADgGIADgDIAFACIAAAHIgDABIgFgBg");
	this.shape_7.setTransform(-92.5,-275.8);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 8
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#E3E300").s().p("AARAMQAIggggghQAFABADAEQAeAjAEAWQgBAZguAOIgWAFQAmgPANgag");
	this.shape_8.setTransform(-81.5,-270.5);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AgWBJQg0gNgjgdQgcgjANgeQAHgiAlgNQAxgMA0ARQA1AOAcAkQAhAegHAiQgNAbgoAPIgDABIgCAAIgUAEIgMABIgCAAIgBABQgbgEgegKg");
	this.shape_9.setTransform(-92.6,-273.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 7
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("AgtCVQg/gSgdg2Qgeg1AQg9QAVhAA5gfQA5ghA8ASQBCAPAdA3QAfA2gVA9QgRA/g5AhQgmAUgnAAQgVAAgWgFg");
	this.shape_10.setTransform(-92.1,-219.3);

	this.timeline.addTween(cjs.Tween.get(this.shape_10).wait(1));

	// Layer 6
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACXmXQACB7gFBpQgWHBiSCGQg+AhgmjUQgRhfgNiP");
	this.shape_11.setTransform(6.9,-53.2);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("Ah4DhQgRhfgNiPIACgDQBhhJBGhaQBShnAyh9QACB7gFBpQgWHBiSCGQgJAEgHAAQgzAAghi3g");
	this.shape_12.setTransform(6.9,-53.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11}]}).wait(1));

	// Layer 5
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AjIEuIAAgFQgKlSBajhQA0hIBQBNQB7C1BDDG");
	this.shape_13.setTransform(-56,-190.2);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("Ah4kHQA1hJBQBOQB6C0BDDHImSCzQgKlTBajgg");
	this.shape_14.setTransform(-56,-190.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13}]}).wait(1));

	// Layer 4
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AQAKhQgLFHjgCXQjgCYkyhwQkwhvjSk1QjSk1AKlHIAEg0IgjgFQkPgxiZjfQiZjiA3kPQA3kPDnicQDoidEPAxQEOAxCZDiQCZDig3EOQgHAigKAgIAXAIQEzBwDREzQDSE1gKFGg");
	this.shape_15.setTransform(-79.4,-167.5);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AEDSnQkwhvjSk1QjSk1AKlHIAEg0IgjgFQkPgxiZjfQiZjiA3kPQA3kPDnicQDoidEPAxQEOAxCZDiQCZDig3EOQgHAigKAgIAXAIQEzBwDREzQDSE1gKFGQgLFHjgCXQiBBYicAAQh0AAiBgwg");
	this.shape_16.setTransform(-79.4,-167.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 3
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AiwEzQisBsisBDQA4iGBNh2AIJniQphD+krHKQA7CHCYg6QFEjNFAlf");
	this.shape_17.setTransform(-206.2,-223.4);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#000000").s().p("Aglh9QA5CGCYg4QisBqirBDQA5iEBNh3g");
	this.shape_18.setTransform(-241.2,-187.8);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#FFFF33").s().p("AnFE0QEqnJJij+Ig2DpQlAFflEDNQguASglAAQhWAAgphgg");
	this.shape_19.setTransform(-199.5,-231.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 2
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhUhnQghDABBAPQBFAIBQg5");
	this.shape_20.setTransform(-15,-36.2);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("Ag0CQQhBgPAhjAQBXgiBAguQANCPARBfQhHAxg8AAIgSAAg");
	this.shape_21.setTransform(-15,-40.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

	// Layer 1
	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ao7qFIkUFFIErCyIkVEZQJoIfL/gmQDpk+A5loQomBgqdiNIBwjTIlkhUIB4j/IgegGg");
	this.shape_22.setTransform(-14.6,3);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#FFFF33").s().p("As5CLIEVkZIkriyIEUlFIAuAKIAeAGIh4D/IFkBUIhwDTQKdCNImhgQg5FojpE+QgyACgxAAQrEAApAn7g");
	this.shape_23.setTransform(-14.6,3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-280.2,-292.4,351.4,361.2);


(lib.shape27复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 12
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AgShcQA5BCgeB3");
	this.shape.setTransform(-130.3,-294.9);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 11
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgDANQgIgDgEgEQgFgGACgFQACgGAGAAIAOgCQAIADADAHQAGADgCAEQgCAGgHAEg");
	this.shape_1.setTransform(-163.7,-279.7);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 10
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAnAMQgEAOgPALIgdgBQgRgEgIgMQgHgRAEgMQAEgOANgFQAQgLAPAFQAQAFAHARQAJAMgEAMg");
	this.shape_2.setTransform(-164,-280.9);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgJAkQgRgEgIgMQgHgRAEgMQAEgOANgFQAQgLAPAFQAQAFAHARQAJAMgEAMQgEAOgPALg");
	this.shape_3.setTransform(-164,-280.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 9
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AlWqVIAAAEQgOI7GJHiAiIoDQDaE1B8FfQBRDmA4EfQiwiCiCiIQCZhfAQic");
	this.shape_4.setTransform(-123.6,-137.7);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AiYgGQCYhfAQicQBQDmA5EdQiuiCiDiGg");
	this.shape_5.setTransform(-104.5,-97.3);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#FFFF33").s().p("AkSoOIDOCOQDaE1B9FfQgQCciaBfQmJnkAOo5g");
	this.shape_6.setTransform(-130.4,-150.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5},{t:this.shape_4}]}).wait(1));

	// Layer 8
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFFFF").s().p("AgGBEQgJgBgEgMQgKgTADgUQABgSAJgKQAIgLAMACQAOABAFAMQAGANgCATQgCAXgKAPQgHAHgIAAIgGgBgAgDg8IgCgCIAAgEIAFgCQADAEgCAEQABAAAAABQAAAAAAAAQAAABAAAAQAAABgBAAg");
	this.shape_7.setTransform(-160.7,-242);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 7
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("AgPCCQgjgCgRgjQgTgvAIg2QAHg3AfggQAbglAgABQAdAJAVAmIABADIABABIAGATIADAMIABACIAAABQABAcgEAfQgHA1gXAoQgbAagYAAQgGAAgGgCg");
	this.shape_8.setTransform(-158.2,-242.4);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#E3E300").s().p("AAGgNQghgCgbAkIACgLQAfghAVgHQAagDAUAsIAJAVQgVgkgcgJg");
	this.shape_9.setTransform(-156.8,-254.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 6
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("Ag6CMQg9gZgYg8Qgcg7Aag5QAYg5A+gXQA7gYA8AZQA7AZAcA/QAYA5gZA5QgYA5g7AYQgeALgdAAQgeAAgggOg");
	this.shape_10.setTransform(-113.5,-267.9);

	this.timeline.addTween(cjs.Tween.get(this.shape_10).wait(1));

	// Layer 5
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkICHIgYgDAjulSQEtC0DeErQAoBClggoQAwCPgmAdQgvAMjIjYAgbCnIjtgg");
	this.shape_11.setTransform(98.4,-272.1);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("AkICHIDtAgIjtggIgYgDQA2iYgJiaIAFgMIAAiYQEtC0DeErQAoBClggoQAwCPgmAdIgFAAQg1AAi9jMgAkICHg");
	this.shape_12.setTransform(98.4,-272.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11}]}).wait(1));

	// Layer 4
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACZECQkWjKh7jRQgZhWBvgSQDZAODGBH");
	this.shape_13.setTransform(-74.8,-295.2);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("Aj4iZQgZhWBvgSQDZAODGBHIhkGuQkWjKh7jRg");
	this.shape_14.setTransform(-74.8,-295.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13}]}).wait(1));

	// Layer 3
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ATem9QBQD1jJD/QjJEAltB3Qk6BnkSg0QhzDSjkBVQkABej5h2Qj5h2hhkGQhhkGBwj6QBxj8EAhfQD/hfD5B3IASAIQC+jCEshjQFth3E6BZQE6BZBQD0g");
	this.shape_15.setTransform(-47.1,-256.2);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AttLwQj5h2hhkGQhhkGBwj6QBxj8EAhfQD/hfD5B3IASAIQC+jCEshjQFth3E6BZQE6BZBQD0QBQD1jJD/QjJEAltB3Qk6BnkSg0QhzDSjkBVQhxAqhxAAQiMAAiLhCg");
	this.shape_16.setTransform(-47.1,-256.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 2
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AnQoyIAAAEQBwIZHZGCQB7hygTiWAjvnLQEPD9DBEzQB9DKB0EEQjBhdiYhp");
	this.shape_17.setTransform(-112.9,-151.6);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#000000").s().p("AirAgQB7hwgTiVQB8DJBzECQi/hciYhqg");
	this.shape_18.setTransform(-83.5,-118.4);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#FFFF33").s().p("AlZnNIDgBjQEQD9DAEzQAUCWh8ByQnYmEhwoXg");
	this.shape_19.setTransform(-124.8,-161.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 1
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AG1rtQjaICnjHjIh1jQIkID8IiUjvIgVAWIgiAhIB7GYIE4iZIBWGDQMVjeGDqXQiMlxkQj1g");
	this.shape_20.setTransform(123.2,-239.4);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("AmdFrIk4CaIh7mYIAigiIAVgWICUDvIEIj8IB1DQQHjnjDZoCQERD2CMFwQmDKXsVDeg");
	this.shape_21.setTransform(123.2,-239.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-175,-338.9,384.2,268.5);


(lib.shape26复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 13
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhPgwQBXgCBIBk");
	this.shape.setTransform(-138.2,-288.2);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 12
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgCANIgNgGQgEgHABgEIAIgGQAHgFAHACQAIACAEAIQAFADgCAEQgBAGgIAEg");
	this.shape_1.setTransform(-160.1,-269.9);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 11
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAUAkIgcABQgRgFgJgLQgIgRAEgMQAEgOANgGQAPgLAPAFQARAEAHARQAJAMgDAMQgEAOgPALg");
	this.shape_2.setTransform(-160.4,-271);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgiAVQgIgRAEgMQAEgOANgGQAPgLAPAFQARAEAHARQAJAMgDAMQgEAOgPALIgcABQgRgFgJgLg");
	this.shape_3.setTransform(-160.4,-271);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 10
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AJmByIgsAIQwaCtnknKAJmByQCpgfC2gxQjwhSkjgyQCTBNAhCHgAFwhtIgEgEAqTinQI/gLHEBFIBCAL");
	this.shape_4.setTransform(-41.3,-188.2);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AkIhqQEhAyDwBSQi2AxinAfQghiGiThOg");
	this.shape_5.setTransform(28.7,-187.4);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#FFFF33").s().p("AsUijIExgEQI8gLHGBFIBDALQCSBNAhCHIgrAIQkfAvj1AAQqKAAlglMg");
	this.shape_6.setTransform(-58.8,-188.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5},{t:this.shape_4}]}).wait(1));

	// Layer 9
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFFFF").s().p("AgHA6QgPgPgDgVQgFgTAGgKQAFgNAOgCQALgDAIAKQALAMAEARQAEAXgEAQQgHALgOACIgDABQgFAAgHgJgAgag4IgDgCIgBgEIAGgEQAEACAAAFQABABAAAAQAAAAAAABQAAAAAAABQAAAAAAABg");
	this.shape_7.setTransform(-144.9,-248.8);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 8
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("AglBuQgggogJg4QgKg0AUgoQAPgsAhgIQAcgBAfAeIACACIABABIANARIAGAKIABACIAAABQAJAaAGAhQAKAzgKAsQgWApghACQgHACgHAAQgXAAgWgVg");
	this.shape_8.setTransform(-143.1,-250);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#E3E300").s().p("Ag1AYQASgpAUgOQAWgLAgAmIAQAPQgfgcgdABQghAIgPAqg");
	this.shape_9.setTransform(-144.6,-261.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 7
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("AhWB8Qg2glgLg/QgQhAAlgyQAkgzBBgKQA+gLA2AkQA1AmAOBCQAMA9gkAzQgjAyg/ANQgOACgMAAQgxAAgrgfg");
	this.shape_10.setTransform(-95.1,-273.7);

	this.timeline.addTween(cjs.Tween.get(this.shape_10).wait(1));

	// Layer 6
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhkEIQh+irg8hjQiCjcBPgiQBkghJdFe");
	this.shape_11.setTransform(-112.1,-443.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("AkegGQiCjcBPgiQBkghJdFeInUDPQh+irg8hjg");
	this.shape_12.setTransform(-112.1,-443.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11}]}).wait(1));

	// Layer 5
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AjWikIAFgCIgBgDADXkfQgpFTh9DSQg8A/hGhZQhejEgijO");
	this.shape_13.setTransform(-81.1,-318.4);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("AhUDsQhdjEgjjOIGph5QgqFTh8DSQgaAagaAAQgmAAgpg0g");
	this.shape_14.setTransform(-80.8,-318.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13}]}).wait(1));

	// Layer 4
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhBzrQD3gqDiELQDiEKBGGkQBHGgh+FGQgTAygWAqQANBKgEBQQgNERjQC3QjOC2kVgOQkXgOi8jKQi7jKANkRQAOkQDPi3IA+gyQgfhkgThyQhGmkB+lFQB+lGD4gqg");
	this.shape_15.setTransform(-86.4,-312.9);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AhXTwQkXgOi8jKQi7jKANkRQAOkQDPi3IA+gyQgfhkgThyQhGmkB+lFQB+lGD4gqQD3gqDiELQDiEKBGGkQBHGgh+FGQgTAygWAqQANBKgEBQQgNERjQC3QjACpj9AAIgmgBg");
	this.shape_16.setTransform(-86.4,-312.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 3
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AGciWQEcANDvAzQiqBCifAuIgoAMQvcEVoAlxAFciYIgFgEAFciYIBAACQCVA3AtB5AqChbQImhLG4AO");
	this.shape_17.setTransform(-43.4,-201.6);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#FFFF33").s().p("AsBg2IEkgmQIjhLG6AOIBAADQCVA3AtB4IgoAMQmoB3lOAAQnBAAkkjSg");
	this.shape_18.setTransform(-59.8,-201.5);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#000000").s().p("AkEhXQEaANDvAzQiqA/idAwQgth4iVg3g");
	this.shape_19.setTransform(24,-207.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 2
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AgPjMIhxAHQg4ASAkA4QCHC4CwCQ");
	this.shape_20.setTransform(-139.2,-423.8);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("Ai4h7Qgkg4A4gSIBxgHQBJB+CxDnIhIA0QiwiQiHi4g");
	this.shape_21.setTransform(-135.6,-423.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

	// Layer 1
	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AEQKOIj0CNIAVAWIAhAjIGbhuIiPk9IGEhLQjFsaqMmXQl1CAj9EKQH7DoHVHyIjUBug");
	this.shape_22.setTransform(-38.5,-474.6);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#FFFF33").s().p("AAxMxIgVgWIDziNIj1kPIDVhuQnVnyn7joQD9kKF1iAQKMGXDFMaImFBLICQE9ImbBug");
	this.shape_23.setTransform(-38.5,-474.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-165.4,-560.8,221.7,390.6);


(lib.shape25复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 13
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhbAWQA+g8B5AY");
	this.shape.setTransform(-139.1,-262.4);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 12
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgOAMQgEgEACgGQAAgGAHgGQAGgGAHABQAGgCAEAFQAEAEgBAIQgCAEgGAGQgHAGgEABQgIAAgEgFg");
	this.shape_1.setTransform(-145.8,-236.9);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 11
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AglAAQgBgRANgMQANgLAQADQAPAAAJAKQAKALABARQgDAOgNALQgNAMgMABQgSgDgKgLQgKgLADgOg");
	this.shape_2.setTransform(-146.8,-237.1);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgeAZQgKgLADgOQgBgRANgMQANgLAQADQAPAAAJAKQAKALABARQgDAOgNALQgNAMgMABQgSgDgKgLg");
	this.shape_3.setTransform(-146.8,-237.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 10
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AD6nZQAOi/gEjRQjQEGh7ENAkEHIQAgmlCdl5QCoiPCZAMQg+NLmxH4");
	this.shape_4.setTransform(-4.7,-321.9);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#FFFF33").s().p("Aj/EAQAgmiCel7QCoiPCZAMQg/NLmwH3g");
	this.shape_5.setTransform(-5.3,-302);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#000000").s().p("AClkIQAFDRgOC9QiZgMioCPQB9kLDNkGg");
	this.shape_6.setTransform(4.7,-382.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5},{t:this.shape_4}]}).wait(1));

	// Layer 9
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFFFF").s().p("AAOA2QgRgJgLgTQgKgQABgLQAAgOANgHIAUAAQAPAHAKAPQALAUACARQgDAMgNAHQgDACgDAAQgGAAgGgEgAgtgvIgCgFIAFgFIAGAFQAAAAABABQAAAAAAAAQAAABAAAAQAAABAAABIgHABg");
	this.shape_7.setTransform(-122.6,-237.3);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 8
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#E3E300").s().p("AgwAgQACgtAPgUQATgSApAZIAUALQgngSgbALQgcATAAAtg");
	this.shape_8.setTransform(-125.7,-248.7);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AgBBzQgsgbgbgyQgcguAFgrQgBgvAdgTQAcgLAnASIABABIABABIASALIAJAIIABACIABAAQARAWARAdQAbAsAFAtQgGAugeANQgQAKgRAAQgPAAgOgHg");
	this.shape_9.setTransform(-120.7,-239.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 7
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("AgmCTQg/gRggg3Qglg5ASg6QAQg7A6ggQA5ggA9AQQA/ASAjA5QAgA4gQA5QgQA8g4AhQgmATgnAAQgUAAgXgGg");
	this.shape_10.setTransform(-83.9,-277.9);

	this.timeline.addTween(cjs.Tween.get(this.shape_10).wait(1));

	// Layer 6
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAEDYQjDiLh8iHIg3hAQgUgzAsgkQEwggGkCU");
	this.shape_11.setTransform(-221.2,-401.5);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("Ak7g6Ig3hAQgUgzAsgkQEwggGkCUIgHAAIhTBkIhiBWIiIBPIgyAsQjDiLh8iHg");
	this.shape_12.setTransform(-221.2,-401.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11}]}).wait(1));

	// Layer 5
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhlkQQD8DnBkDcQAPBYhxAGQjWgmi+hc");
	this.shape_13.setTransform(-105.4,-333.2);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("Aj7CPICWmfQD8DnBkDcQAPBYhxAGQjWgmi+hcg");
	this.shape_14.setTransform(-105.4,-333.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13}]}).wait(1));

	// Layer 4
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AIWjjQDLAqCPCiQC0DLgUETQgVEUjRC4QjSC5kTgOQkUgOizjNQi0jMAUkTQAGhKAThEQigg7iehzQkpjXhwkhQhvkhCMi/QCMi/E1ASQE0ATEoDZQEpDZBvEgQAXA9ANA4g");
	this.shape_15.setTransform(-118.7,-303.5);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AFFQ+QkUgOizjNQi0jMAUkTQAGhKAThEQigg7iehzQkpjXhwkhQhvkhCMi/QCMi/E1ASQE0ATEoDZQEpDZBvEgQAXA9ANA4QDLAqCPCiQC0DLgUETQgVEUjRC4QjDCsj8AAIgmgBg");
	this.shape_16.setTransform(-118.7,-303.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 3
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ACWn5QiGAMh8CoQhPGMAjGkAhLOFQEqo1hJtJQgRi/gjjNQiKEihEEe");
	this.shape_17.setTransform(-21.4,-317.1);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#000000").s().p("ABOkeQAjDMAQC9QiDANh+CnQBEkdCKkgg");
	this.shape_18.setTransform(-19.3,-378.6);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#FFFF33").s().p("AiYEmQgjmjBPmOQB8inCGgNQBJNKkqI1g");
	this.shape_19.setTransform(-21.4,-297.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 2
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AibjDQgoDeFjCp");
	this.shape_20.setTransform(-237.2,-387.8);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("AjCjDQCTCiD3CjIhPBCQljipAojeg");
	this.shape_21.setTransform(-233.3,-387.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

	// Layer 1
	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AEXEHIFuAFIg+ETIAeAAIAwAAIDHl5IlKhuIDSlOQrOmOrlDJQifFpAVFsQIEjWKtgDg");
	this.shape_22.setTransform(-208.2,-459.5);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#FFFF33").s().p("AKFEMIlugFIBBjmQqtADoEDWQgVlsCflpQLljJLOGOIjSFOIFKBuIjHF5IgwAAIgeAAg");
	this.shape_23.setTransform(-208.2,-459.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-295.3,-514.8,317.7,321.2);


(lib.shape24复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 13
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhJAzQAghMBzga");
	this.shape.setTransform(-83,-223.9);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 12
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AgLAOQgFgDACgHQgCgFAGgHQAFgGAFgBIAKABQAFADABAHQgBAFgFAGQgGAHgEACg");
	this.shape_1.setTransform(-73.3,-200.5);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 11
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AgBgkQAMgDALAJQALAIAEARQAAAMgKANQgLAOgNADQgQABgLgJQgLgIABgPQgFgOALgOQAKgNARgBg");
	this.shape_2.setTransform(-74.2,-200.6);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgYAdQgLgIABgPQgFgOALgOQAKgNARgBQAMgDALAJQALAIAEARQAAAMgKANQgLAOgNADIgCAAQgOAAgLgIg");
	this.shape_3.setTransform(-74.2,-200.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 10
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AhNmUQhqixiBidQghFEBJE4AAvJqQjplghWlwQBWkHBtgnQEiHqBuKN");
	this.shape_4.setTransform(-18.5,-366.9);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#FFFF33").s().p("AAWHDQjplghWlwQBWkHBtgnQEiHsBuKLQh0hLiggug");
	this.shape_5.setTransform(-16,-350.2);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#000000").s().p("Ahvk8QB/CcBqCwQhtAnhUEGQhJk3AhlCg");
	this.shape_6.setTransform(-38.6,-409);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5},{t:this.shape_4}]}).wait(1));

	// Layer 9
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FFFFFF").s().p("AANAaQgQgDgKgJQgKgJADgMQACgNANgFIAggCQAWAEAOAKQAHALgCAMQgCAJgNADQgMAGgOAAQgHAAgHgCgAhDAAIAAgGIAHgBIAEABIgDAGIgDAAg");
	this.shape_7.setTransform(-47.3,-211.2);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 8
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("AhiAmQgkgfAFggQAKgcAogTIACgBIACAAIAUgFIAMgCIACAAIAAAAIA7AHQA1ALAlAaQAfAjgLAdQgEAigkAQIhmADQg3gLgdggg");
	this.shape_8.setTransform(-47.4,-214.1);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#E3E300").s().p("AAEAzQgfghgFgWQgBgaAtgQIAVgIQglASgLAcQgEAgAhAfg");
	this.shape_9.setTransform(-58.9,-215.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 7
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("AggCXQhCgKgig1QgigzAPg/QANg/A2glQA2gkBAAMQBAAMAiA0QAiAzgMA9QgQBCg3AjQgoAbgsAAQgPAAgQgDg");
	this.shape_10.setTransform(-51.8,-267.3);

	this.timeline.addTween(cjs.Tween.get(this.shape_10).wait(1));

	// Layer 6
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ADFmQIgCACQleFXhkGbQALBLCagyIApgWIACgBQCohcCHh2");
	this.shape_11.setTransform(-233.6,-292.7);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("Aj/FjQBkmbFelXIA9IiQiHB2ioBcIgCABIgpAWQg+AUgnAAQg6AAgGgtg");
	this.shape_12.setTransform(-233.6,-292.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11}]}).wait(1));

	// Layer 5
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkQjZIAGABQFHBSDACQQA3BGhgA7QjKBEjKALIgMAA");
	this.shape_13.setTransform(-88.2,-304.7);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("AkQjZIAGABQFHBSDACQQA3BGhgA7QjKBEjKALIgMAAg");
	this.shape_14.setTransform(-88.2,-304.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13}]}).wait(1));

	// Layer 4
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AS3FPQhYEJj1B/Qj1B+kDhWQkDhWh3j4IgHgOQj3AZkXhdQllh3jIjwQjIjzBLjgQBKjfExhLQExhKFmB3QEWBeC4CpIAKgFQD1h/EDBWQEDBXB5D4QB4D2hYEJg");
	this.shape_15.setTransform(-97.4,-274.1);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AFyL/QkDhWh3j4IgHgOQj3AZkXhdQllh3jIjwQjIjzBLjgQBKjfExhLQExhKFmB3QEWBeC4CpIAKgFQD1h/EDBWQEDBXB5D4QB4D2hYEJQhYEJj1B/QiSBLiWAAQhnAAhpgjg");
	this.shape_16.setTransform(-97.4,-274.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 3
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Akjg2QAokRBkg6QiFidiaiEQAXFEB8EoQCUFcEhE0AG3KjQjbpylzmy");
	this.shape_17.setTransform(-24.6,-345.6);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#FFFF33").s().p("ABIHJQkgk0iUlcQAokRBkg5QFyGzDbJxQh9g3iogTg");
	this.shape_18.setTransform(-17.3,-331.1);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#000000").s().p("AiOk0QCYCECFCdQhkA4goERQh7kogWlCg");
	this.shape_19.setTransform(-54.2,-382.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 2
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("ADdhPQkvDJiKg1");
	this.shape_20.setTransform(-216.3,-263.8);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("AjcB4QDHhsCXiOIBbBlQjwCgiJAAQgjAAgdgLg");
	this.shape_21.setTransform(-216.3,-268.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

	// Layer 1
	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AGTl3IhgmAQsPDzlwKiQCWFsEXDuQDKoIHXnwIB6DNIEBkEICaDrIAVgWIAhgjIiFmUg");
	this.shape_22.setTransform(-278.5,-334.2);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#FFFF33").s().p("AtMCeQFwqiMPjzIBgGAIE1iiICFGUIghAjIgVAWIiajrIkBEEIh6jNQnXHwjKIIQkXjuiWlsg");
	this.shape_23.setTransform(-278.5,-334.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_23},{t:this.shape_22}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-364.1,-441.8,392.2,248.9);


(lib.shape23复制2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 12
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AAfBZQhEg1AHh7");
	this.shape.setTransform(-17.9,-231.8);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

	// Layer 11
	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#333333").s().p("AAAAQQgGgBgFgHQgFgHABgGQgBgGAFgEIALgCIAKAJIAGAMQAAAIgFAEQgCACgDAAQgDAAgDgCg");
	this.shape_1.setTransform(15.6,-237.7);

	this.timeline.addTween(cjs.Tween.get(this.shape_1).wait(1));

	// Layer 10
	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AgBAmQgTgBgKgOQgLgOAFgQQABgOALgJQAMgIAQAAQAOAEAKAOQALAOAAAMQgFATgLAIQgMAJgMgEg");
	this.shape_2.setTransform(15.3,-236.9);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#000000").s().p("AgBAmQgTgBgKgOQgLgOAFgQQABgOALgJQAMgIAQAAQAOAEAKAOQALAOAAAMQgFATgLAIQgIAGgJAAIgHgBg");
	this.shape_3.setTransform(15.3,-236.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_3},{t:this.shape_2}]}).wait(1));

	// Layer 9
	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AgoA7QAAgDADgEQAAgBAAAAQAAgBAAAAQABAAAAgBQAAAAABAAIAEAGIABADIgDAEgAgTARQgLgIACgLQABgRALgPQAOgSAOgIQAMgCALAIQAIAGgCANQgBAUgOAQQgLAPgMAFQgDABgEAAQgIAAgHgFg");
	this.shape_4.setTransform(15.1,-264.7);

	this.timeline.addTween(cjs.Tween.get(this.shape_4).wait(1));

	// Layer 8
	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AhUBqQgWgWACgrIAAgDIABgBIADgVIAEgLIABgCIAAAAQAOgYAUgZQAhgsAmgVQAtgMAXAXQAdATgCAoQgIAwgkAsQgiAsgnAMQgVAJgSAAQgSAAgPgKg");
	this.shape_5.setTransform(12.4,-265.8);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f("#E3E300").s().p("AgbBOQgYgKAGgyIACgUQgBApAVAWQAbATArgSIgIAHQggALgTAAQgIAAgHgCgAggguIgBAFIgBABgAgVhLIADgEIgGAKg");
	this.shape_6.setTransform(6.3,-261.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_6},{t:this.shape_5}]}).wait(1));

	// Layer 7
	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("rgba(255,102,153,0.859)").s().p("AhbB8QgzgogJhBQgIg+Aog3QAngzA9gIQA8gHAzAnQAzArAIBAQAJA/goAzQgmA3g+AHIgUABQgwAAgrgjg");
	this.shape_7.setTransform(-37.7,-285.3);

	this.timeline.addTween(cjs.Tween.get(this.shape_7).wait(1));

	// Layer 6
	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AjzjRQELI6B/gKQCRg9hRqA");
	this.shape_8.setTransform(-104.1,-103.3);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#FFFF33").s().p("AjzjRIHKiNQBRKAiRA9IgFAAQh+AAkHowg");
	this.shape_9.setTransform(-104.1,-103.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8}]}).wait(1));

	// Layer 5
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkJjXQFCBRC9CPQA3BGhgA6QjNBGjSAJIgDAA");
	this.shape_10.setTransform(-72.7,-228.7);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("AkJjYQFCBSC9COQA3BHhgA6QjNBGjSAJg");
	this.shape_11.setTransform(-72.7,-228.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_11},{t:this.shape_10}]}).wait(1));

	// Layer 4
	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Al7kGQASCyBeCxQD0CCE1BZAMNEPQkpmHtfiOQi7gejWgTQDIDtE5Cn");
	this.shape_12.setTransform(-88.8,-369.3);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#000000").s().p("Aj/jJQDWASC5AfQASCwBeCyQk3iojIjrg");
	this.shape_13.setTransform(-141.3,-380.2);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.f("#FFFF33").s().p("AnTBEQhfiwgRiyQNeCNEpGIIkaAAIAHgOIjbA2Qk2hZjziCg");
	this.shape_14.setTransform(-68.7,-366.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_14},{t:this.shape_13},{t:this.shape_12}]}).wait(1));

	// Layer 3
	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("APOpbQA3EOiZDiQiZDgkQAwQhHANhFgBIgLgBQgUCBgwCHQiCFxkCDIQkDDJjrhTQjshUhLk+QhKk/CClwQCCluECjJQA8guA6ggQAfgQAegMQAah4BKhtQCZjiEOgxQEQgwDnCcQDnCdA3EPg");
	this.shape_15.setTransform(-66.8,-236.6);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#FFFF33").s().p("AqKRnQjshUhLk+QhKk/CClwQCCluECjJQA8guA6ggQAfgQAegMQAah4BKhtQCZjiEOgxQEQgwDnCcQDnCdA3EPQA3EOiZDiQiZDgkQAwQhHANhFgBIgLgBQgUCBgwCHQiCFxkCDIQi3COisAAQhGAAhFgYg");
	this.shape_16.setTransform(-66.8,-236.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_16},{t:this.shape_15}]}).wait(1));

	// Layer 2
	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AmnjHQN3gdFEF1IADAAAmnjHQAyCbB5CMQEtBbGBAPAmnjHIlvAaQDhCuE5Bf");
	this.shape_17.setTransform(-94.8,-355.8);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#000000").s().p("AkLh4IFsgbQAzCZB4COQk2hfjhitg");
	this.shape_18.setTransform(-147,-361);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#FFFF33").s().p("AmyBgQh5iMgyibQN3gdFEF1IliA5Ql/gPkvhbg");
	this.shape_19.setTransform(-76.6,-355.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_19},{t:this.shape_18},{t:this.shape_17}]}).wait(1));

	// Layer 1
	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AHsqUIgHgwImOiWIhEFVIlmimQkwL5ElLHQF4BxFphEQkVnlhaqmIDsAjIgplsIEYAbg");
	this.shape_20.setTransform(-213.4,-110.3);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#FFFF33").s().p("AleMVQklrHEwr5IFmCmIBElVIGOCWIAHAwIADAdIkYgbIApFsIjsgjQBaKmEVHlQiIAZiJAAQjkAAjshGg");
	this.shape_21.setTransform(-213.4,-110.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_21},{t:this.shape_20}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-263.9,-401.5,297,378.3);


(lib.shape21 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("rgba(0,0,0,0.298)").s().p("AkpAoQh8gRAAgXQAAgWB8gRQB7gQCuAAQCuAAB8AQQB8ARAAAWQAAAXh8ARIkqAQQiuAAh7gQg");
	this.shape.setTransform(42.2,5.7);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0,0,84.4,11.4);


(lib.üì_y复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#000000").s().p("Eg37ApkIr3KnQlAAAh4tHMAVPgtnIigAAIuYFAIkXoIIAAk/QIHxgPAxgIrQwRIEYloIVRAAIG3IIQkYABifNwQpZgBwQVRIAACgISxvoIEYAAIEYE/IAAIJMgIwAgdIQPtuIAAigIEYloIAADJIkYE/IAAFoIwPd/IAAKnIB3SwIh3AAIB3CgIAAIJIkXFoQpZgBAA9/gEgODAnEUAAAghwAG5gsXIkYtvICgigIG3AAQNvBPgBG5UgG4ASvgCfAzOICfLQIQRAAUAu3gG3AAAgUoIChqnIEXAAIAAd/UAAAAZogu3ADvI3JCgQttlnlBtIg");
	this.shape.setTransform(522,-278);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(44,-736,956,916);


(lib.üì_NI复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#000000").s().p("AjvajIigAAUgNwAUogmHAV5InhAAIqnqoIAAloQDHlBepoHIen6RIAAjHQr44/63w4IihAAIAAjHIFBlBIIHAAQcJPoIHSxICgAAIE/tJQvnwPAAqpIIIk/IPmE/ICgvoIloloIFok/QPoE/AAFoIAAChMgNHA3nQqoFngBKnUAL5Ak5AsXAEXQJZB4EXKBQAAE/nfAAUhBogNIgG3gf4g");
	this.shape.setTransform(512,-338);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(24,-796,976,916);


(lib.üì_复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#000000").s().p("EACfBPnQm1gow58IIAplAIFojwQgoG4O/IwIQPB4IDvr4IBQrQQrPhQwOHgIoIgoUAAogG4AhGgGQItxyIIG5vmIlBgoUgs9A3mgQQAG4IngloQFn5oIIAoIhQL4UAZBgUAAiWgz2UgonAIIgVPAQQIlBloIApigMAn/gTYIDxtwIAnigQvojwAolAQYYjIB4tIICfh4ICgAoISwZoQgpEYtIhQIjvIwICfAoQIJAoa33IIChCgIAACgIngNwIDIigIFoAoIEYFoI8ILQIpZNIQPoBQgnFAIgoG4IoxIuIgnEYIOXQQIXxCgIFAFAIgpCgQh4G4z/igIloAAIAAB4UgEuAlpgXiAAAQheAAhjgJg");
	this.shape.setTransform(500,-349.5);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(104,-860,792,1021);


(lib.üì_嬟复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#000000").s().p("EBAYBLAI1QloItwAAICgtxItwtHQvAVQtvAAIifjIIAAqpQPmABKn1RIiftwIigAAQgBIJwONHInfk/Qnha3mQABIigAAIifoJMAKAgmIUgaRAwIgHfADxIlBAAIovoJQgBqnNxzYIAAnhQr5ABkYrRIAAoFQNxAAMgqpIIvAAQHhAAgBNGICgAAQFAAAIx9+IE/AAIFAFoIIuigMAAAgggIourQIGOloQPBAAgBTYIAAChIk/IIIAAE/ICfigIFBAAIIvIxIk/V3QKABQAAG5IAANFInha4IMgLQUAEZgmHAG3gABIChAAQHgChgBNwMgKAAggIKAAAQIxAAAAtIIE/AAIChYYIngFogAPnO/IChAAIDwrQImRAAgAoHBPIAAKoICgAAILP1NIAAlogEgtmgXHQuZovm4qpIDwifIRhAAIIwIwIAAHfIlBFogEA1IghuQlBAAk/w4ICfigIKBAAIE/FoIAAHfIk/GRg");
	this.shape.setTransform(504,-328);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(28,-808,952,960);


(lib.shape48 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgkgPQABgRALgNQAOgMAPACQATACALAPQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRgAgrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAAQA2IgJACIgGgCIgCgJIACgDIAIgFIAJAFQACABAAAFQAAAEgEACg");
	this.shape.setTransform(-18.9,-52.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_1.setTransform(-18.6,-52.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_2.setTransform(-18.9,-52.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAArgJQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRQABgRALgNQAOgMAPACQATACALAPQAKAMgCARgAABAqIAIgFIAJAFQACABAAAFQAAAEgEACIgJACIgGgCIgCgJg");
	this.shape_3.setTransform(-88.3,-50);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_4.setTransform(-88,-50.1);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_5.setTransform(-88.3,-50);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AlfixQAiAAAYAeQAZAfAAAqQAAArgZAeQgYAcgiAAQgjAAgYgcQgZgeAAgrQAAgqAZgfQAYgeAjAAgAG0hiQAAAtgbAfQgbAegnAAQgmAAgbgeQgcgfAAgtQAAgtAcggQAbggAmAAQAnAAAbAgQAbAgAAAtgAh9BNQgGgFAAgHQAAgIAGgFQAGgFAIAAQAIAAAFAFQAGAFAAAIQAAAHgGAFQgFAGgIAAQgIAAgGgGgAj9DPQA/AIBLg/QBpBPBogs");
	this.shape_6.setTransform(-51.8,-40.9);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiQgvQCKAaCWgIQgtBIhvAFQhsgwgYgvg");
	this.shape_7.setTransform(-47,29.6);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("Ah+CMQgFgFAAgIQAAgHAFgGQAGgFAJAAQAHAAAGAFQAGAGAAAHQAAAIgGAFQgGAFgHAAQgJAAgGgFgAmaA7QgZgeAAgoQAAgrAZgeQAYgeAiAAQAjAAAZAeQAYAeAAArQAAAogYAeQgZAfgjAAQgiAAgYgfgAEVAnQgbggAAgrQAAgtAbgfQAbggAnAAQAmAAAcAgQAbAfAAAtQAAArgbAgQgcAfgmAAQgnAAgbgfg");
	this.shape_8.setTransform(-51.8,-47.2);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("rgba(255,102,153,0.859)").s().p("ApCBnQgPgVgGgbIgEgSIgBgYQAAgzAaglQAbglAmAAQAmAAAbAlQAbAlAAAzQAAA1gbAlQgbAmgmAAQgmAAgbgmgAGEBMQglglAAgzQAAg1AlgmQAmglA0AAQA1AAAlAlQAlAmAAA1QAAAzglAlQglAlg1AAQg0AAgmglg");
	this.shape_9.setTransform(-51,-14.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AM5v/QAvB7gyCCQChiJCBizQiYAaiHAlQmpB0jyDZIgbAUAGVn8IgtATAHaj1QANB0gKCGQgLCOABBxIEmBFIDrnJIoKh1QgRiRg0h2QDkhlC8ifIABgCAHTEEQAAB+AOBZQAbCpB9FLQgwAHg6AhAMMQXIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7AJ5PPQBfgPA0BXQhZBmhCB1Atq30IAAgFAtkzXQBrgPCBBdQhni/iLisAtkzXQgQiVAKiIAqNpeQgXAQgUAVQiMlsggkyAqNFdQh7hPgciKIgDgSQgQhjAdh9IgBgGIACAFIgBABAsbh0QgSlFB1iAACeqyQkQiBlLByQgtAPgtAUAp4yJQBzDTBID1Aq0NWQBxCsDOB1QBngFAOg7ABpHhQiNDxgJFMQAiBCBqADQDoiQB/iWAmwHmQCkEMARFFAqNFdQAxAfBAAWArHXdIAEAPQgpAcgcgcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzArSQBQgmgzgjgRArHXdIgUhFAupQZQBaBxA0BxAIUXiIzbgF");
	this.shape_10.setTransform(-30.5,-22);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("ArOVcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzQh7hPgciIIgDgSQgQhjAdh+IABgCIgCgEQgSlGB1h/QAUgWAXgQQgXAQgUAWQiMltggkyQBrgOCBBcQBzDUBID0QgtAQgtATQAtgTAtgQQFLhyEQCBIgbAUIAbgUQDyjYGph0QAvB6gyCDIgBABQi8CfjkBlQA0B2ARCRIIKB1IjrHJIkmhFIAAgLQAAhrAKiHQAFg7AAg4QAAhHgIhCQAIBCAABHQAAA4gFA7QgKCHAABrIAAALQAAB+AOBZQAbCpB9FLQgwAHg6AhQA6ghAwgHQBfgPA0BXQhZBmhCB1QBCh1BZhmIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7IAFA7IzbgFIgUhFIAUBFIAEAPQgUAOgSAAQgRAAgOgOgArhRrQg0hxhahxQBaBxA0BxgAk7PnQBngFAOg7QgOA7hnAFQjOh1hxisQBxCsDOB1IAAAAgACZPTQDoiQB/iWQh/CWjoCQQhsgDgihCQAJlMCPjxQiPDxgJFMQAiBCBsADIAAAAgAjBOnQgRlFikkMQCkEMARFFgAqYNxQgmgzgjgRQAjARAmAzgAniECQhAgWgxgfQAxAfBAAWgAGip5IAtgTg");
	this.shape_11.setTransform(-36.3,-7.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("ALFB8QCGgmCZgaQiCC0igCJQAyiDgvh6gAvZhbQgQiVAKiIQCLCtBnC+QiBhdhrAPg");
	this.shape_12.setTransform(-18.9,-136.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-142.8,-176,224.6,308);


(lib.shape47_1 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#FFFFFF").s().p("EjNRCDYMAAAkGvMGajAAAMAAAEGvgEhOHAyAMCcOAAAMAAAhj+MicOAAAg");
	this.shape_13.setTransform(500,320);

	this.timeline.addTween(cjs.Tween.get(this.shape_13).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-813.9,-520.9,2627.8,1681.8);


(lib.shape46 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgZggQgJALACAOQAAAPALAJQAJAJAMAAQANgCAJgLQAJgMgDgOQgBgOgIgKQgLgKgLABQgOACgIAMgAg1AEQACAaARASQAKAJAMADIARACIARgDQALgHAIgLQAPgVgCgYQgDgagSgQQgLgMgOgDIgLAAIgOADQgNAFgLAOQgPATADAYgAgGAeIgGAEIgCAGQAAADADABQADACADAAQAEAAABgCQACgDAAgEIgCgDQgDgEgDAAg");
	this.shape.setTransform(30.5,-50.9);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AgJAtQgBgBAAAAQgBAAAAgBQgBAAAAgBQAAgBAAAAIACgGIAGgEQADAAABAEIAEADQAAAEgEADQAAACgDAAQgDAAgDgCgAgTASQgLgJAAgQQgCgOAJgKQAIgNAOgCQALgBALAKQAIALABAOQADAOgJAMQgJALgNABQgMAAgJgIg");
	this.shape_1.setTransform(30.3,-50.9);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgMA8QgMgDgKgJQgRgSgCgaQgDgYAPgTQALgOANgFIAOgDIALAAQAOADALAMQASAQADAaQACAYgPAVQgIALgLAHIgRADgAgMAiIgCAGQAAABAAAAQAAABAAAAQABABAAAAQABABABAAQADACADAAQAEAAABgCQACgDAAgEIgCgDQgDgEgDAAgAgDguQgOACgIAMQgJALACAOQAAAPALAJQAJAJAMAAQANgCAJgLQAJgMgDgOQgBgOgIgKQgKgJgJAAIgDAAg");
	this.shape_2.setTransform(30.5,-50.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AgUhCQgPAGgNAQQgRAWAEAbQACAeAUAUQALAKANAEIAUACQAMAAAJgEQAMgIAKgMQAQgYgBgbQgEgegVgSQgMgOgRgEIgNAAgAAWgqQgNgMgNACQgQACgKAOQgJAMACAQQAAASANAKQAJAKAPAAQAPgCALgNQAJgNgCgQQgCgQgJgMgAgNAzIAHACQAEAAACgCQACgEAAgEIgCgEIgHgEQgEADgEABIgCAHg");
	this.shape_3.setTransform(-37.2,-55.5);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AgLAzIgDgFIACgGIAHgFIAFAFIAEADQAAAFgEADQAAACgDAAgAgWAUQgNgKAAgSQgCgQAKgLQAJgPARgCQANgBANALQAJAMABAQQADAQgKAOQgLAMgPACQgOAAgKgKg");
	this.shape_4.setTransform(-37.4,-55.5);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgPBFQgNgEgLgKQgUgUgCgeQgEgbARgWQANgQAPgGIAQgEIANAAQARAEAMAOQAVASAEAeQABAbgQAYQgKAMgMAIQgJAEgMAAgAgPAnIgCAHIAEAFIAHACQAEAAACgCQACgEAAgEIgCgEIgHgEIgIAEgAgEg0QgQACgKAOQgJAMACAQQAAASANAKQAJAKAPAAQAPgCALgNQAJgNgCgQQgCgQgJgMQgLgKgLAAIgEAAg");
	this.shape_5.setTransform(-37.2,-55.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AGYhxQgTgfgdAAQgcAAgUAfQgTAfAAArQAAApATAeQAUAfAcAAQAdAAATgfQALgQAFgRQAEgSAAgUQAAgVgEgRQgFgUgLgQgAChArQgJAAgGAFQgHAFAAAGQAAAHAHAEQAGAFAJAAQAJAAAHgFQAFgEAAgHQAAgGgFgFgAEgC9QgfArhehTAlKjKQgoAAgdAfQgcAgAAAsQAAAsAcAgQAdAdAoAAQApAAAcgdQAdggAAgsQAAgsgdggQgcgfgpAAgACfCVQiMBYhBhA");
	this.shape_6.setTransform(-6.6,-44.7);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiMgoQCMAWCNgdQg2BThsAMQhcgagbg+g");
	this.shape_7.setTransform(-14.8,27.4);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("rgba(255,102,153,0.859)").s().p("AHaCJQgjgoAAg4QAAg3AjgpQAignAxAAIAFAAQAQCLhMB1QgPgKgNgPgAoRBGQgigoAAg2QAAg4AigpQANgOAOgKQAZgQAfAAQAxAAAiAoQAjApAAA4QAAAsgYAjIgLAPQgiApgxAAQgwAAgjgpg");
	this.shape_8.setTransform(-7.8,-17.8);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("ACSCGQgHgEABgHQgBgGAHgFQAGgFAJAAIAQAFQAFAFAAAGQAAAHgFAEQgHAFgJAAQgJAAgGgFgAE4BgQgTgeAAgrQAAgpATgfQAUgfAcAAQAdAAATAfQALAQAEAUQAFAPAAAVQAAAUgFASQgEATgLAQQgTAfgdAAQgcAAgUgfgAmOArQgdggAAgqQAAgsAdggQAcgfAoAAQApAAAcAfQAdAgAAAsQAAAqgdAgQgcAfgpAAQgoAAgcgfg");
	this.shape_9.setTransform(-6.6,-51.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AMVzkQhpALhoBmQAkjvBZh8QA2B9AeB9QBLE2grFZIAAAIIACACQA5BcADEFQAuBhAKBYQARCMhNB0QgtBFhOA8QguAjg4AgAItrqQBKAWBKAjAMDqJQAVAOAdAuAJExzQgYCmABDjAMZPaQAfgjAdgMAPkQVIAxg3QBqACgDB5Ik2FxQgnALgLgQIgBgBQgJgSAUguAL0FQQAVEiBME5QBSglA9CPQhHBag+B6Ar2qsQiqhVigh4QCVgmCcAAQFkgBGLDAQEwhfEdBVAkdoEIBxAVAAerAIg+ggAlKkNQAPiMAehrQj2g2jjhyAlUDoIoBCrIkmoSIMxiOQgXDVANEggAsPufQgbBtA0CGAlTEFIgBgdAlTEFIAEBBQg2DBguCKIiFg/gAMINFQjtENhagBIgCABQg4ACgmgzQgBlrDhj1AAcGrQCZEdgrE2QgdAyhQAOQjMiTi0iWAnBXLIgEAHQgQAUgfgNIl2nGQAOhkBTAGIA2BHQA+hkBgATQA0ALA/AuAo1OrQBEheA+i8AnBXLQANgagJgVArTP8ICkDYAMTXEIzUAH");
	this.shape_10.setTransform(-45.5,-22.3);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("An0VdIl2nGQAOhkBTAFIA2BHQA+hkBgAUQA0AKA/AvQg/gvg0gKQBEhfA+i7IiFhAIDllNIAEBCQg2DAguCLQAuiLA2jAIgEhCIgBgcQgFhnAAhcQAAipAPiKQAPiMAehrIBxAWIhxgWQj2g2jjhxQg0iHAbhsQFkgBGLC/QEwhfEdBVQBKAXBKAiQhKgihKgXQgBjjAYimQBohlBpgMQBLE3grFZIAAAIIACACQA5BbADEFQAuBiAKBXQARCOhNBzQgtBFhOA8QguAjg4AfQA4gfAugjQAVEhBME5QBSglA9CQQhHBag+B6QA+h6BHhaIAxg4QBqACgDB6Ik2FwQgnAMgLgQIgBgCQgDgFAAgJQAAgSAOgfQgOAfAAASQAAAJADAFIzUAHQAIgPAAgOQAAgJgEgIQAEAIAAAJQAAAOgIAPIgEAHQgKANgPAAQgKAAgMgFgAovRXIikjYgAG7PVIAEAAIACAAIABAAIAGgBIAAAAIACAAIACAAIAAAAIAFgBIABAAQBRgSCti6IAXgZIACgDIAQgSIANgOIACgDIgCADIgNAOIgQASIgCADIgXAZQitC6hRASIgBAAIgFABIAAAAIgCAAIgCAAIAAAAIgGABIgBAAIgCAAIgEAAIAAAAIAAAAQgyAAgjgqIgDgEIAAgBIgCgCIAAgFQAAlnDgj0QjgD0AAFnIAAAFIACACIAAABIADAEQAjAqAyAAIAAAAIAAAAgAAdPBQBQgOAdgxQAKhEgBhDQAAjuh3jfQB3DfAADuQABBDgKBEQgdAxhQAOQjMiTi0iWQC0CWDMCTgAMZNeQAfgjAdgNQgdANgfAjgAM1rJQgdgvgVgNQAVANAdAvgAAes9Ig+gggAnBVOIAAAAgAx7j7IMxiPQgPCKAACpQAABcAFBnIoBCrg");
	this.shape_11.setTransform(-45.5,-9.9);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("AuqDLQCVgmCcABQgbBsA0CHQiqhWigh4gANXmXQA2B8AeB9QhpAMhoBlQAkjuBZh8g");
	this.shape_12.setTransform(-60.5,-131.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-161.4,-173.6,231.8,302.6);


(lib.shape45 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAgkgPQABgRALgNQAOgMAPACQATACALAPQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRgAAQA2IgJACIgGgCIgCgJIACgDIAIgFIAJAFQACABAAAFQAAAEgEACg");
	this.shape.setTransform(-18.9,-52.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_1.setTransform(-18.6,-52.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_2.setTransform(-18.9,-52.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("Agrg3QAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACIgYgEQgOgJgKgMQgTgZACgeQAEgfAXgTgAAjgmQAKAMgCARQAAATgPALQgKAKgSAAQgQgCgNgNQgKgOADgRQABgRALgNQAOgMAPACQATACALAPgAABAqIAIgFIAJAFQACABAAAFQAAAEgEACIgJACIgGgCIgCgJg");
	this.shape_3.setTransform(-88.3,-50);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAA2IgEgIIAEgEIAGgFIAJAFQACACAAAFQAAADgEACIgJACgAggARQgKgPADgRQABgRALgMQAOgNAPACQATACALAPQAKANgCARQAAATgPAKQgKALgRAAQgRgCgNgNg");
	this.shape_4.setTransform(-88,-50.1);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgdBHQgOgJgKgMQgTgZACgeQAEgfAXgTQAOgPATgEIAPAAIATAEQARAGAPARQASAXgDAeQgDAfgXAVQgMALgPAEIgXACgAABAqIgCADIACAJIAGACIAJgCQAEgCAAgEQAAgFgCgBIgJgFgAgYgtQgLANgBARQgDARAKAOQANANAQACQASAAAKgKQAPgLAAgTQACgRgKgMQgLgPgTgCIgEgBQgNAAgMALg");
	this.shape_5.setTransform(-88.3,-50);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AkliTQAZAfAAAqQAAArgZAeQgYAcgiAAQgjAAgYgcQgZgeAAgrQAAgqAZgfQAYgeAjAAQAiAAAYAegAGZivQAbAgAAAtQAAAtgbAfQgbAegnAAQgmAAgbgeQgcgfAAgtQAAgtAcggQAbggAmAAQAnAAAbAggAhvBTQgIAAgGgGQgGgFAAgHQAAgIAGgFQAGgFAIAAQAIAAAFAFQAGAFAAAIQAAAHgGAFQgFAGgIAAgAj9DPQA/AIBLg/QBpBPBogs");
	this.shape_6.setTransform(-51.8,-40.9);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#CCCC00").s().p("AiQgvQCKAaCWgIQgtBIhvAFQhsgwgYgvg");
	this.shape_7.setTransform(-47,29.6);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#000000").s().p("Ah+CMQgFgFAAgIQAAgHAFgGQAGgFAJAAQAHAAAGAFQAGAGAAAHQAAAIgGAFQgGAFgHAAQgJAAgGgFgAmaA7QgZgeAAgoQAAgrAZgeQAYgeAiAAQAjAAAZAeQAYAeAAArQAAAogYAeQgZAfgjAAQgiAAgYgfgAEVAnQgbggAAgrQAAgtAbgfQAbggAnAAQAmAAAcAgQAbAfAAAtQAAArgbAgQgcAfgmAAQgnAAgbgfg");
	this.shape_8.setTransform(-51.8,-47.2);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("rgba(255,102,153,0.859)").s().p("ApCBnQgPgVgGgbIgEgSIgBgYQAAgzAaglQAbglAmAAQAmAAAbAlQAbAlAAAzQAAA1gbAlQgbAmgmAAQgmAAgbgmgAGEBMQglglAAgzQAAg1AlgmQAmglA0AAQA1AAAlAlQAlAmAAA1QAAAzglAlQglAlg1AAQg0AAgmglg");
	this.shape_9.setTransform(-51,-14.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AM2sCQChiJCBizQiYAaiHAlQAvB7gyCCIgBACQi8CfjkBlQA0B2ARCRIIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhAGVn8IgtATAHaj1QANB0gKCGQgLCOABBxAMMQXIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7AJ5PPQBfgPA0BXQhZBmhCB1Atq30IAAgFAtkzXQBrgPCBBdQhni/iLisAtkzXQgQiVAKiIAqNpeQgXAQgUAVQiMlsggkyAqNFdQh7hPgciKIgDgSQgQhjAdh9IgBgGIACAFIgBABAsbh0QgSlFB1iAACeqyIgbAUACeqyQkQiBlLByQgtAPgtAUAp4yJQBzDTBID1Aq0NWQBxCsDOB1QBngFAOg7ABpHhQiNDxgJFMQAiBCBqADQDoiQB/iWAmwHmQCkEMARFFAqNFdQAxAfBAAWArHXdIAEAPQgpAcgcgcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzArSQBQgmgzgjgRArHXdIgUhFAupQZQBaBxA0BxAIUXiIzbgFAM5v/QmpB0jyDZ");
	this.shape_10.setTransform(-30.5,-22);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FFFF33").s().p("ArOVcIlPnBQAfhtBsAwIAjArQBAiDBOAnQBoktAmkzQh7hPgciIIgDgSQgQhjAdh+IABgCIgCgEQgSlGB1h/QAUgWAXgQQgXAQgUAWQiMltggkyQBrgOCBBcQBzDUBID0QgtAQgtATQAtgTAtgQQFLhyEQCBIgbAUIAbgUQDyjYGph0QAvB6gyCDIgBABQi8CfjkBlQA0B2ARCRIIKB1IjrHJIkmhFQAAB+AOBZQAbCpB9FLQgwAHg6AhQA6ghAwgHQBfgPA0BXQhZBmhCB1QBCh1BZhmIA4g9QBVgEAhBhIlvG+QgeALgZgYIAAgGIgFg7IAFA7IzbgFIgUhFIAUBFIAEAPQgUAOgSAAQgRAAgOgOgArhRrQg0hxhahxQBaBxA0BxgAk7PnQBngFAOg7QgOA7hnAFQjOh1hxisQBxCsDOB1IAAAAgACZPTQDoiQB/iWQh/CWjoCQQhsgDgihCQAJlMCPjxQiPDxgJFMQAiBCBsADIAAAAgAjBOnQgRlFikkMQCkEMARFFgAqYNxQgmgzgjgRQAjARAmAzgAniECQhAgWgxgfQAxAfBAAWgAINB0IAAgLQAAhrAKiHQAFg7AAg4QAAhHgIhCQAIBCAABHQAAA4gFA7QgKCHAABrIAAALgAGip5IAtgTg");
	this.shape_11.setTransform(-36.3,-7.6);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#000000").s().p("ALFB8QCGgmCZgaQiCC0igCJQAyiDgvh6gAvZhbQgQiVAKiIQCLCtBnC+QiBhdhrAPg");
	this.shape_12.setTransform(-18.9,-136.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-142.8,-176,224.6,308);


(lib.shape36 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAugkQAPASgDAXQgCAZgTARQgKAIgMAEIgTABIgTgDIgUgRQgQgUACgXQAEgZASgPQAMgMAPgDIANAAIAPADQAOAFAMAOgAAdgeQAIAKgBAOQAAAPgMAIQgJAIgPAAQgMgBgLgLQgJgLADgNQABgOAJgKQALgKANACQAPACAJALgAARAnQAAADgEABIgHACIgFgCIgBgGIABgDQAEgEADAAIAHAEg");
	this.shape.setTransform(-97.7,-121.8);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#FFFFFF").s().p("AAAArIgDgGIADgDQACgEAEAAIAGAEIACAFQAAADgDABIgHACgAgaANQgIgLACgNQABgOAJgKQAMgKAMACQAQACAIALQAJAKgCAOQAAAPgMAIQgJAIgOAAQgNgBgLgLg");
	this.shape_1.setTransform(-97.4,-121.8);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#000000").s().p("AgXA5IgUgRQgQgUACgYQAEgZASgOQAMgNAPgDIANAAIAPADQAOAGAMANQAPATgDAWQgCAZgTASQgKAIgMADIgTABgAABAhIgBAEIABAGIAFACIAHgCQAEgCAAgDIgCgFIgHgEQgDAAgEAEgAgTgjQgJAKgBANQgDAOAJALQALAKAMACQAPAAAJgIQAMgIAAgPQABgOgIgKQgJgLgPgCIgEgBQgKAAgKAJg");
	this.shape_2.setTransform(-97.7,-121.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 3
	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f().s("#000000").ss(1,1,1,3,true).p("AAZgbQAHAJgBAMQAAANgLAIQgHAHgNAAQgLgBgJgJQgIgKACgNQABgMAIgIQAKgKALACQANACAIAKgAAoghQANARgCAUQgCAXgRAPQgJAIgKACIgQABIgRgCIgSgPQgNgSABgVQADgXARgNQAKgLANgCIALAAIANACQAMAFALAMgAABAeQADgDADAAIAGADQABABAAAEQAAACgDABIgGACIgEgCIgBgGg");
	this.shape_3.setTransform(-141.3,-124.3);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#FFFFFF").s().p("AAAAnIgCgGIACgDQACgDADAAIAGADQAAAAAAABQABAAAAABQAAAAAAABQAAABAAABQAAACgDACIgGABgAgWAMQgIgKACgMQABgMAIgJQAKgJALABQANACAIAKQAHAJgBANQAAANgLAHQgHAIgMAAQgMgCgJgJg");
	this.shape_4.setTransform(-141.1,-124.3);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#000000").s().p("AgUAzIgSgPQgNgSABgVQADgXARgNQAKgLANgCIALAAIANACQAMAFALAMQANAQgCAVQgCAXgRAPQgJAIgKADIgQABgAABAeIgBADIABAFIAEACIAGgCQADgBAAgCQAAgBAAgBQAAgBAAAAQAAgBgBAAQAAgBAAAAIgGgDQgDAAgDADgAgRggQgIAKgBAMQgCALAIAKQAJAKALABQANABAHgIQALgHAAgOQABgMgHgJQgIgLgNgBIgEAAQgIAAgJAHg");
	this.shape_5.setTransform(-141.3,-124.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3}]}).wait(1));

	// Layer 2
	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("Ai5imQASAWAAAfQAAAfgSAVQgSAWgaAAQgZAAgTgWQgSgVAAgfQAAgfASgWQATgWAZAAQAaAAASAWgAg4AyQAAAEgEADQgFADgGAAQgGAAgEgDIgFgHIAFgHIAKgCIALACQAEADAAAEgAEMiWQAYAYAAAiQAAAigYAYQgXAYghAAQghAAgYgYQgXgYAAgiQAAgiAXgYQAYgYAhAAQAhAAAXAYgAgVCPQAWAFAegFAiFCFQAIAmAqARQAfAIAfg1QgdgHgWgVQgdARggABgAisCAIAnAF");
	this.shape_6.setTransform(-117.2,-111.7);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#FF6666").s().p("AgFAkQgqgSgIgkQAhAAAbgSQAWAWAdAGQgcAtgbAAIgGgBg");
	this.shape_7.setTransform(-125.1,-96.4);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#CCCC00").s().p("AgKApQgsgBhLhQQCBAdCCgOQhOBCg0AAIgKAAg");
	this.shape_8.setTransform(-118.8,-74);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#000000").s().p("AhRB5IgFgGIAFgHIAKgDIALADQAEADAAAEQAAAEgEACQgFADgGAAQgGAAgEgDgACbAdQgXgYAAggQAAgiAXgYQAYgYAhAAQAhAAAXAYQAYAYAAAiQAAAggYAYQgXAYghAAQghAAgYgYgAkRACQgSgUAAgfQAAgfASgWQATgVAZAAQAaAAASAVQASAWAAAfQAAAfgSAUQgSAWgaAAQgZAAgTgWg");
	this.shape_9.setTransform(-117.2,-118.1);

	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("rgba(255,102,153,0.859)").s().p("AEnBjQgSgGgOgPQgZgbAAgkQAAgjAZgaQAOgPASgGIAcgFQAjAAAYAaQAZAaAAAjQAAAkgZAbQgYAagjAAQgPAAgNgFgAmHAiQgPgZAAgfQAAghAPgYQAPgYAWAAQAUAAANASIAEAGQAQAYAAAhQAAAfgQAZIgEAFQgNASgUAAQgWAAgPgXg");
	this.shape_10.setTransform(-115.5,-103.5);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6}]}).wait(1));

	// Layer 1
	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f().s("rgba(0,0,0,0.478)").ss(1,1,1,3,true).p("AsbqJQgyASgyAcQB5ArByARQhbgwgsg6QDshYDtAVIgLAHAnRDuIgCAHQglA3gygSQhhhfBxh4ICZh7QijioB8jhQgBguANgrQAIgeAPgdAL/rqQBEANA9AUQh1BMirAgIggAFIgKAxIihBtQAChIgehFIgkABAGRr8IDyiUIgfCTQBRAFBKAOAGRr8QBvgIBkAHAGVm6IAAANIAAACQAVArALAqIBMA7Ig+BYQAGhKgUhJAG/i+IAEgFIgHAwQAPABAQAJQAcBKAGCCQAEAqgOAhQgQAmgnAbQhDAXgXg2AJApYQhcAPhrACAJgpdQCmhWgHg3ADZruIAfACICZgQAlCrMQETi0EnCUAnADpIgRAFAmRBAQgdBZgSBQAmBg4QAbAcAjAaAnTCUQAQApgOAxAG8iTQgegEgaAYIg1CgAmcoaQh4AOiAgTAEjMyQAyASAaAdQgFA/hKgUIi2g4IgCgVQiWgWheAZIAAAiIjzArQgygGAKgsQApgbApAAIAIAGACLNFIgjgGAiMNCIgjAJAEjMyQgQAMgSAKAHzCOQACIJjSCbAlVNCQjPiiBkm3");
	this.shape_11.setTransform(-109.5,-80);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#FFFF33").s().p("AEvOMIi3g4IgBgVIAiAGIgigGQiXgWhdAZIgkAJIAkgJIAAAiIjzArQgzgGAKgsQApgbApAAQjPiiBlm3IgRAFQAGgXAAgVQAAgYgIgWQAIAWAAAYQAAAVgGAXIgCAHQgmA3gxgSQhhhfBwh4ICah7QikioB8jhQAAguAMgrQAJgeAPgdQgPAdgJAeQh3AOiAgTQhcgwgsg6QDthYDtAVQESi0EnCUIgfgCIAfACICZgQIAPgBIACAAIAJgBQAugCAsAAIAAAAIAAAAQAsAAApACIAFABIAFAAIgFAAIgFgBQgpgCgsAAIAAAAIAAAAQgsAAguACIgJABIgCAAIgPABIDziUIggCTQBSAFBKAOQAGA3imBWIgfAFQhdAPhrACIgjABIAjgBQBrgCBdgPIgLAxIigBtIAAgJQAAhDgdhBQAdBBAABDIAAAJIgBANIABACQAUArAMAqIBLA7Ig9BYIABgiQAAg5gPg4QAPA4AAA5IgBAiIgEAFIAEgFIgHAwQAPABAQAJQAcBKAGCCQAEAqgPAhQgQAmgmAbIgBABIgBAAIAAAAQgTAGgPAAIgBAAIAAAAQglAAgQgkIAAAAIgBgCIABACIAAAAQAQAkAlAAIAAAAIABAAQAPAAATgGIAAAAIABAAIABgBQAmgbAQgmQACIJjRCbQAyASAZAdQgEAwgrAAQgOAAgRgFgAEQNIQARgKARgMQgRAMgRAKgAk+NIIgJgGgAmxDpQAShQAchZQgcBZgSBQgAGSh/Ig1CgIA1igIABgBIABgBQAVgTAZAAIAAAAIAAAAIAHABIAAAAIACAAIgCAAIAAAAIgHgBIAAAAIAAAAQgZAAgVATIgBABIgBABIAAAAgAk1gCQgjgagagcQAaAcAjAagAk+rFIALgHgAh9NCgAnCDuIAAAAgAGkm6IAAAAgAJPpYg");
	this.shape_12.setTransform(-110.9,-80);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#000000").s().p("At/ApQAygcAygQQAsA5BbAvQhygRh5grgAL/hkQBEANA9AVQh1BJirAgQCmhUgHg3g");
	this.shape_13.setTransform(-109.5,-144.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_13},{t:this.shape_12},{t:this.shape_11}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-200.1,-172.3,181.3,184.7);


(lib.shape20 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(3,1,1).p("Aj/kDQBqhsCVAAQCWAABrBsQBpBsAACXQAACZhpBsQhrBsiWAAQiVAAhqhsQhqhsAAiZQAAiXBqhsg");
	this.shape.setTransform(883.5,378.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(845.7,340.2,75.6,76.8);


(lib.shape18 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(3,1,1).p("Aj/kDQBqhsCVAAQCWAABqBsQBqBsAACXQAACZhqBsQhqBsiWAAQiVAAhqhsQhrhsAAiZQAAiXBrhsg");
	this.shape.setTransform(724.4,378.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(686.6,340.2,75.6,76.8);


(lib.shape16 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(3,1,1).p("Aj/kEQBqhsCVAAQCWAABqBsQBrBsAACYQAACYhrBsQhqBsiWAAQiVAAhqhsQhrhsAAiYQAAiYBrhsg");
	this.shape.setTransform(514.7,378.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(476.9,340.2,75.6,76.8);


(lib.shape14 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(3,1,1).p("Aj/kDQBqhsCVAAQCWAABqBsQBrBsAACXQAACZhrBsQhqBsiWAAQiVAAhqhsQhrhsAAiZQAAiXBrhsg");
	this.shape.setTransform(306.7,378.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(268.9,340.2,75.6,76.8);


(lib.shape12 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.478)").ss(3,1,1).p("Aj/kDQBqhsCVAAQCWAABrBsQBqBsAACXQAACZhqBsQhrBsiWAAQiVAAhqhsQhrhsAAiZQAAiXBrhsg");
	this.shape.setTransform(146.9,378.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(109.1,340.2,75.6,76.8);


(lib.shape7 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#394B68").s().p("AARBuIgZgFIgHgCQgQgEgRgKIgCgBIgdgUQgLgJgOgHIgDgCIgUgKQAMggAgAEQAaADAaAPIAIAEIARADIA4AGQAcAEAcAJIAEABIAZALIAWANIADAMIgFgBIgSAFIgNAEQgpAKgoAAIgagBgACPgHIgYgBIg3gUIgjgNQAOgJAXgHIAmgHQASAOAUAXQANAPAEASQgEgKgMgDgAiKhbQATgaAgAJQAhAIgFAqIgBAAQgugIgXAHIgeAKQAJgYAMgSg");
	this.shape.setTransform(4.2,-1.9);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#000033").s().p("ACDCbQgTgOgHgOQgOgbABgfIgDgjIACgBIgCAAIgCgLIgWgOIgagJIgDgBQgcgJgagEIg7gFIgQgDIgJgEQgZgPgagDQghgEgLAfIgKgFIgjgTIgMgJIgJgIQgLgJgaADQAPgWAagLIAPgFQgDALAEgMIAOgFIAdgJQAYgHAtAIIABAAIABAAIAhAHIAaADIASgTIAMgLQAPgLAOgHIALgFQATgIAVgCIA7gFQAfgCAZAUQAVAPAGAbIAKA2IAEAiIgCAAIACABIA7AQIAHAEIAKAEQARAHAFAKIAHARQABAHgeACQgdABADAGQACAHgDAJQgDAKgagCIgIgBIACAdIABArQgIAfgYgVQABAXgNAAQgMAAgYgVgACfglIgIgCIgIgCgAgDh1QgXAIgOAJIAjANIA2AUIAYABQAMADAEALQgFgTgNgQQgTgXgTgNgAi+hQQgGARAVgKQAcgNgcgMQgKACgFAQg");
	this.shape_1.setTransform(11.2,4);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#263246").s().p("AhFB5QgZgogOgsQgBgZAGgeIANAJIAjASIAJAFIAUAKIADACQAOAHAJAJIAdAUIAAABIABANQABAlgZAIIACAYQADAXgVAAQgggDgbgsgAhXhTIAEgRQASg+AfgCIA0gDQAcAAAXAMQAWAMAKAdQACAJAHACIgNAKIgSATIgZgCIghgHIgBgBQAFgqgfgIQgggJgTAaQgMASgJAYIgNAFg");
	this.shape_2.setTransform(-3.9,0.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-18.9,-16.1,60.3,37.8);


(lib.shape6 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#394B68").s().p("Ag/AfIgfgVQgLgJgOgFIgDgCIgTgKQALggAhAEQAaADAZAPIAIAEQAHAeABAlQgQgEgRgKgAB4AKIAXANQgHAIAEACIAAABIgSAFg");
	this.shape.setTransform(5.7,3.9);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#000033").s().p("ADcD6QgjgJgqgPQgpgPgqgbQgrgcgTgdQgWgegOgkIgXg9IgIgVQgLgggGgdQgBgmgGgfIgJgEQgZgPgagDQghgEgLAfIgKgFIgjgTIgVgRQgLgJgaADQAPgWAagLIA7gUQAYgHAtAIIACAAIAhAHQAuACCQA9IAwAGIABAAIAQAEIARAEIASAGIA9ARIAHAEIAKAEQARAJAFAKIAHARQABAHgeACQgdABADAGQACAHgDAJQgDAKgagCIgdgEIhQgfIgQgEIgGgBIAAAAQgEgDAGgHIgWgOIABAeQAEAbAMAaIADAFQAPATATANQAMAJATAqIARARQARATASAeQACASALAWIAAAAIABACQAkBagbAAIgFgBgAi+jQQgGARAVgKQAcgNgcgMQgKACgFAQg");
	this.shape_1.setTransform(11.2,16.8);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#263246").s().p("AAYCOQhTgogJgxQgKgyAKgtQALgxAYg0IADACQAOAHALAJIAdAVQARAKAQAEQAGAdALAhQgFApgMAiQAEAYgLAZQAEAwgUAAQgFAAgFgCg");
	this.shape_2.setTransform(-3.2,17.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-18.9,-8.3,60.3,50.3);


(lib.shape5 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#394B68").s().p("AACAwIgZgFQgTgEgVgMIgfgVQgLgHgOgHIgWgMQALggAhAEQAaADAZAPQAMAHANAAIA5AGQAcAEAbAJQAbAGAZARQgHAIAEACIAAABQg5ATg2AAIgbgBg");
	this.shape.setTransform(5.7,4.3);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#000033").s().p("ACHCtIgdgDIhQgfIgQgEIgGgBIAAgBQgEgCAGgIQgXgRgagIQgcgJgcgEIg7gGQgNAAgMgHQgZgPgagDQghgEgLAgIgKgFIgjgUIgVgQQgLgKgaAEQAPgWAagLIA7gTQAYgHAtAIIACABIAhAHQAtACAtARIgBACIACgBIAcgMQAQgIAQgLQAbgUATgbQBLhVBHgGIBygHIBWgIQAfAHgdAcQgaAYgdAUIgEADIgQAJIgtAgQgDAYgSASQgXAWgaASIgbAQQgjAUgmgHIgIgCIgIgBIAQADIARAFIASAGIA9ARIAHADIAKAFQARAJAFAKIAHARQABAHgeABQgdABADAHQACAGgDAKQgDAHgSAAIgIAAgAkPATQgGARAVgKQAcgNgcgLQgKABgFAQg");
	this.shape_1.setTransform(19.3,-6.1);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#263246").s().p("ABDBuQgsgUgrgCIghgHIgCAAQgngcgBg4QgBg1AtgcQAggUAlgGQATAugPAkQAiAKgQAeQApAcAPA6IgbANg");
	this.shape_2.setTransform(3.7,-16);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-18.9,-27.1,76.5,38.4);


(lib.shape4 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("#000033").ss(1,1,1,3,true).p("AgjANQAZgBAagMIAUgN");
	this.shape.setTransform(28.6,-29.5);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("#0066CC").s().p("AgMADQAFgOAIgCQAcAMgcALQgGAEgEAAQgHAAAEgLg");
	this.shape_1.setTransform(-6.6,-4.5);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#394B68").s().p("AACAwIgZgFQgTgEgVgMIgfgVQgLgHgOgHIgWgMQALggAhAEQAaADAZAPQAMAHANAAIA5AGIAZAFIAeAIQAbAGAZARQgHAIAEACIAAABQg5ATg2AAIgbgBg");
	this.shape_2.setTransform(5.7,4.3);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#263246").s().p("Ag9C9IgKg2IgNg4QgHgbACgdIAFg2QADgeANgZQAOgZAZgRIAwgeQAagOATgRQAPgOADARQABANgEALQANAIgCARIgVAQQgNAKgKAPQgLAOgNAdIgVAwQgKAdgBAlIAAAMQAAAjgHAhIgBAMQgDAXgHAUg");
	this.shape_3.setTransform(4.5,-26.8);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#000033").s().p("ADYEZIgdgEIhQgfIgQgEIgGgBIAAAAQgEgDAGgHQgZgSgagIIgfgIIgXgFIg7gFQgNgBgMgGQgZgPgagDQghgEgLAfIgTgKIgVgNIgWgQQgPgLgaADQAPgWAagLIA7gUQAYgHAvAIIAhAHQAHgUADgXIACgMQAGghABghIAAgMQAAglAMgfIAVgwQAOgdAKgOQALgPAKgKIAWgQIAIgEQARgJAZgIQAagIAjABQAjACBugSQAVAcgcAWIgWASIgJAIQgDAygSAWIgTAYIgDAuIgCASIgDALIgFAKQgNAXgSAYQgDASgJAdIgBABIgHAVQgOAogUASIgJAJIA5AIIAZAGIASAGIA9ARIAHAEIAKAEQARAJAFAKIAHARQABAHgeACQgdABADAGQACAHgDAJQgDAIgSAAIgIAAgAi+B/QgGARAVgKQAcgNgcgMQgKACgFAQgAC9h+QgbAOgaABQAagBAbgOIAVgNg");
	this.shape_4.setTransform(11.2,-16.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-18.9,-46.5,60.3,57.8);


(lib.shape3 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 27
	this.shape = new cjs.Shape();
	this.shape.graphics.f("#063201").s().p("AhBEWIofgCIAAgFIgCgKQAAgFACgDIAXgNIgKgFQAAgHAFgFQAIgFAFAAQgFgFACgFQADgFAFgDIgXgXQgDgHgHgDIgKgPQgKgcgFgbQgDgFAFgIIAPgIQAXgMAcACIAFADQAFgFgKgIIgFgHQgKgKAMgFIAKgFQgFgLgKgHQADgIAPgDQghgFgSgcQgHgKACgHIAKgFQAFgDAIADIAUAFQgKgNAKgCQAKgFgFgIIgFgIQgKAAgCgPIAbgCIAAgFQgPgXAfgSQgugWgHgaIgGgKIANgKIADgFIAFAAQAPgHAKAKIACAFQAFgDAIAFIAIADIAFAFIACAFIAFACIAAgHIADgDIAAgHQAAgKAFgDQAHgFAKAAQASAFgCAPIAAADIAKgDQAFAAACADIADAAQAHAFADAKIAAgFIACgFIAKgUQADgKAIgFIAAgDIACgFIADgFQAAgBAAAAQAAgBABAAQAAAAAAgBQABAAAAAAIAAgFQADgHAFAAIARAAQAIACgFAIIACAAIAFgFQAGgIACAFIAIAFQAKAIAAAKIAAADIACgDIAAgFIADgKQARgZAcAAIAIAAQAFARgDASIAAACIAKADQADADgDAHIAAADIADAAIAKAAQAIAAgDAHIAAADIgFAUIAAAFIASAAIAZAAIACACQAKALAAAMQAAASgHASIgDAHQgHAKgPgFIgFAAQgFAFAAAIIACAAIAFgDQAKAAgCAKIAAAFIAFgCIAFAAQAMgDADAKIACAIIAIgIIADgCIAFgFIAKgIIAZgCIAIACQAFAFgFAIIgNAMIgNADQAAAAgBAAQAAABAAAAQgBAAAAABQAAAAAAABIgDAMIAAADQAAAHADADIACgFIAIgFIAHACIADAAQACAAADADQAIAFgDAFIADAAIAMAAIAIAFQACAHgCAFIgDALQAFACAAgCIADAAIAFACQAFAFAFAAIANAFIAHAFQAFADgCAFQgBACAAAAQAAABAAABQAAABAAAAQAAAAABAAIAFAAQAFAAACAFIADADIAAAIQAFgLAMAAIADAAQADgHgDgIQAAgDAFgCQAFACAFgCQAFAAAAADIAAACQADAFAHAAIAdADIAFACQAHASgHASIgDAHQAAAIgHAFIgDACIAFAAQANAAgDAIIgCAIIAKgDIAFADQAFAKgDAKIAAAHIgHAPQAMAAADAGIAFAFQACAHAFgHIADAAIACgIIgKgNQgCgFAFgFQACgFADAAIAAgCIgFgDQAAgFACgCIANgDQgIgCAAgIIgCgFIgDgFQgCgSANADIACAAIADgNIAFAAIgDgCQgCgDAAgFQgDgCAAgIIAFgUIADgIIAFgNIACgIQAIgMAKgIIAFgCIAPAAIADAAIAMAHIAFAFQAKAFgCALIAAACIAMADIADAAIAFAFQABAAAAABQABABAAAAQAAABgBABQAAAAgBABIADAAIAAgFIACgQQAIgKAMgCQAFAAAAACIAIAIIAAgDIAFgUIAHADQADAHgDAKIAIgMIADgDIAFAAIACASQADgFAHgDQgCADAAAFQgDAIAIgDQAMAAADAKQAAgHAFgIIAIgPQAFgSAPgKIAHgCQAKgGAKAAIAQAAQAHAAAFADQAPAPgCAUIACADIAKAFIAFAAIADACQANAFgDANIAAADQAPAAAFAMIAAgFIAAgCIANgPIAFgGIAeADIAAADIAIAUQAFAIgDAHQgCAQACAMIgCAFIAAADIgFAHIgFAIIACACIAAAFQADAQgKAAIACACIAAADIAFACIADANIACAAQAFgFAKACIADAAQACgHAIgIQADgCAFAAIAFAAIAHgDIAAgIIADAAIAhAGQAFAFAAAFQAFARgPAFQgDANAXgUQAFgFAFAAQAPgFAKAFIAIAKQAFARgQASQgFAFAAAFIAQAAIAZgCQgIAegZAUQASANAFAMQACAFgCAFQgKAcgXAKIAFAFg");
	this.shape.setTransform(236.5,343.4);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.lf(["#CCFF00","#C7D013"],[0,1],-7.9,-73.3,47.9,-5.9).s().p("AjGCuIADAAIAAACgADCivIACAAIADACg");
	this.shape_1.setTransform(187.7,297.6);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("#002000").s().p("AC0GEIgFgFQgDgFgMAAIAHgPIAAgIQADgKgFgKIgFgCIgKACIACgIQADgHgNAAIgCgDQAHgFAAgHIADgIQAHgRgHgSIgFgDIgfgCQgHAAgDgFIAAgDQAAgFgFAAQgFADgFgDQgFADAAAFQADAHgDAIIgDAAQgMAAgFAKIAAgIIgDgCQgCgFgFAAIgFAAQgBAAAAgBQgBAAAAAAQAAgBABgBQAAgBABgBQACgFgFgFIgHgFIgNgFQgFAAgFgFIgFgDIgDAAQAAADgFgDIADgKQACgFgCgIIgGgFIgMAAIgDAAQADgFgIgFQgDgCgCAAIgDAAIgHgDIgIAFIgCAFQgDgCAAgIIAAgCIADgNQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIANgDIANgNQAFgHgFgFIgIgDIgZADIgKAHIgFAFIgDADIgIAHIgCgHQgDgKgMACIgFAAIgFADIAAgFQACgKgKAAIgFACIgCAAQAAgHAFgFIAFAAQAPAFAHgKIADgIQAHgSAAgRQAAgNgKgKIgCgDIgZAAIgSAAIAAgFIAFgUIAAgCQADgIgIAAIgKAAIgDAAIAAgCQADgIgDgDIgKgCIAAgCQADgQgFgSIgIAAQgcAAgRAZIgDAJIAAAEIgCADIAAgDQAAgIgKgHIgIgFQgCgFgGAHIgFAFIgCAAQAFgHgIgDIgRAAQgFAAgDAIIAAAFQAAAAgBAAQAAAAgBAAQAAABAAAAQAAABAAAAIgDAEIgCAEIAAADQgIAFgDAKIgKAUIgCAFIAAAFQgDgKgHgFIgDAAQgCgCgFAAIgKACIAAgCQACgPgSgFQgKAAgHAFQgFACAAAKIAAAIIgDACIAAAIIgFgDIgCgFIgFgFIgIgCQgIgFgFACIgCgFQgKgKgPAIIgFAAIgDAFIgNAKIAAgDIgCgKIgFghIgDghIADgKIACgDIAAgFQALgRAZgDQAAABABAAQABABAAAAQABAAAAgBQABAAABgBQAPAAANgHIAFgIQAMgUAKASQADAFAFgDQAKgFAFAFIADADQAKARAMANIAIAFIgDgKQAAgNANgCIAPACQAKAFAIAIQAPAPAMgIIgFgKQgFgKADgHIAPgPIADAAIAFgGIgQgoIgHgUIgKgZIgDgNQgKgeAUgPQAFgDADADQAcAHAUAUIgDgKQADgRAUAHQAZgRAKAZQAFgFAGgDQAUgPAWgFQgKgZAAgZQgChAAeAGQAUACApAtQAXhBAPAmQANAogQAXQADgDADAAQAWANAKAeQADgHAFgFQAXgVARAfQgCgFAAgIIACgFQAKgUASAKQgXghgHgmIAAgKIAAgKQACgFAIgIQAZgZANAeQAAgKARgCIAIACIAKAIQANANAHAPIAIgIIAHgFQADgCACAAQAVgDAMASIADAFIgDArIANgIIAHgFIAKADQAIAFADAMQAFgHAFACIAZADQAFACAFgFQADgCACAAIAFgDQAFAAAAAFIgCAIIgDARIADAAQAKAAAFAFIACADIAAAIIgCAUIgFAFIgKAHQgIAFgKAAIAAAVIgDAHQgKASgRAAQAMACAAAFIgCAVIgFAHIgKAXIAAACIACAAIAFgHIANgKIAHgDQAQgHAMAKIAAAFQAIgDAFAIIAHAAIAFAHIAIAFIACgFQADgFgDgHQAAgIAGgHQAKgFAKACQAFAAACAFIAFAKIAAAFIADAAQAKAfANAeQAAACgIADQgKAHgFALQgDAFAFAKIAFAKQgMAHgSgPQgFgHgNgDIgMAAQgNAAAAANIADAKIgIgDIgXggQgFgIgPAFQgCADgDgGQgKgPgMASQgNASgZgDQgaAAgKAUQgHAQAFAeQAFAfAHAbQAIAcAtAXQggARAPAXIAFAFQgFAIgZgDIACAFIgPAAIgFADQgKAHgIANIgCAIIgFAMIgDAKIgFAUQAAAIADADQAAAFACACIADADIgFAAIgDAMIgCAAQgNgCACARIADAFIACAFQAAAIAIADIgNACQgCADAAAFIAFACIAAADQgDAAgCAFQgFAFACAFIAKAMIgCAIIgDAAQgCAEgCAAQgBAAAAAAQAAAAgBgBQAAAAgBgBQAAgBAAgBg");
	this.shape_2.setTransform(222.9,318.6);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("#42950B").s().p("Ao4GYIAFAAQAAAAAAAAQgBABAAAAQAAAAgBABQAAAAAAABgAI2EkQABAAAAgBQAAgBAAAAQAAgBAAAAQAAgBgBgBIADAAIAAAFgAAxj7IAAgCIACAFIAAACgAEymXIADgDIAAAFg");
	this.shape_3.setTransform(196.4,313.3);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("#295C07").s().p("Aq7DYIACgCIADACgAK2DWIADgCIADACgAEZjYIACAAIgCAGg");
	this.shape_4.setTransform(167.2,329.2);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("#336600").s().p("AAIFvIgSAAIiQAAIgCgIQARgMANgaQACgIgCgCQgIgNgPgMQAZgUADghIgUACIgPADQAAgHAFgGQAMgPgFgRQgCgLgDAAQgKgEgNAEIgKADQgWAUACgMQANgDAAgSIgIgKQgFgFgZgDIgDADIgCAFQAAAGgDgDQgCAAgDADQgFgBgCADQgIAFgCAHIgDAAQgHABgFAEIgBAAIgFAAQADgEgFgIIgDgDIgCgEQAKgBgDgNIgCgHIAAgDIAFgHIADgHIACgFIAAgDIAAgcQADgKgFgHQgGgLAAgKIgCgCIgcgDIgFAFIgHAPIgDADIAAAFQgCgMgQAAIAAgDQAAgKgKgFIgCgDIgDgDQgHABgFgGIAAgCQAAgUgPgMQgGgEgFAAIgMAAQgKAAgKAGIgFAAQgNAKgIAUIgFAKQgHAKAAAIQAAgKgNACQgFABAAgLIAAgIIgHALQACgKgFgIIgDgDIgCAGQgFAEgDAIQADgKgDgIIgHgCQgDAKAAAKIgHgFIgFgDQgKAAgIANQgFAGAAAKIgFgDIgDgDIgMgCIAAgDQACgKgKgFIgCgFIgNgIIgCAAIgDgEQAZACADgIIgDgEQgPgWAegRQgrgWgHgcQgIgcgFgfQgFgeAIgUQAKgRAXAAQAWACANgUQAKgSAKAPIAFAFQANgFAFAFIAXAhIAFADIgDgKQAAgNANAAIAMADQAKACAIAIQAPAPAKgIIgFgKQgCgKACgFQADgLAKgHIAHgFIgWg9QAHgEAKAEQAIAFACAKIAAgCQAAgFADgDIAIgTQACgLAIgFIACgCIAAgGIADgEIAAgDIACgFQAAgHAIgBIAPADQAFAAgDAHIADgCIAFgCQACgIAGAFIAFAFQAKAIAAAKIACgIIADgIQAKgRARgIIAQAAIAFAAIACAhIAAACIAKADQADAFgDAIIAAACIADAAIAHAAIADAAQAHgCgCAIIAAACIgFARIgDAKIASgCIADAAIAUACIACAAQAKALgCAPIgDAZIgCAIIgDAHIAAADIgCAAQgFAIgNgDIgCAAIgDgDIAAADIgFAFIAAAFIAIAAQAKAAgDAKIAAAFIAFgCIAFAAQAKgDADAHIACAKIAIgHIAAgDIAFgCIAAgCQAFgIAIgCIACAAIASgDQAFAAACADQAFAEgFAIQgHAMgPADIgDACIgCANIAAAGQAAAHACACIADgEQAFgIAHADIAIACIACADQAFACgCAFIACAAIANADQAFAAAAAEQAFAGgCAHIgDALIAFAAIADAAQAHAEAFAAQAKADAIAHQAFAFgDAGQAAABgBABQAAABAAAAQAAABAAAAQABAAAAAAIAFAAQAGAAACAGIADAEIAAAGQAFgKAKAAIACAAQADgIgDgIQAAgEAIgBIAFgCQAHAAAAACIAAAFQADAFAFABQAQgFAMAHIADACQAHASgHASQgDADAAAFQgCAJgIAGIAFAAQAKgDgCANIAAADQACgBAIAAQAAAAABAAQAAAAABAAQAAAAAAAAQAAAAAAABQAFAKgCAKIAAAHIgFASQAKgDADAGIAFAFQACAHAFgHIAAgDQAFAAgCgFIgIgMQgCgGAFgEQAAgFAFgDIgDAAIgCgCQgDgGADgCIAMgCQgFgDgCgGIgDgFIgCgFQgDgSANADIACgDIADgKIAFAAIgDgEIAAgGIgCgKIACgUIADgKIAFgPIAFgIIAHgMIAAACIAGAKQAHAZAuAXQgfASAPAXIAAAEIgbADQACAPAKAAIAFAGQAFAHgKAGQgKACAKAMIgUgEQgIgDgFADIgKAEQgCAIAHAKQASAcAhAFQgPAFgDAHQAKAIAFAKIgKAGQgMAEAKALIAFAHQAKAHgFAGIgFgDQgcgCgXAMIgPAIQgFAIADAFQAFAbAKAcIAKAPQAHACADAIIAXAXQgFADgDAEQgCAGAFAEQgFAAgIAGQgFAEAAAIIAKAFIgXANQgCADAAAEIACAKIAAAGIIfACIAAAIg");
	this.shape_5.setTransform(167.4,335.4);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.lf(["#CCFF00","#C7D013"],[0,1],59.1,-53.4,60.7,43.9).s().p("AAAAAIAAgBIABAAIgBABIABAAIgBACg");
	this.shape_6.setTransform(73.8,331.7);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.f("#11430E").s().p("AD3IvIgIgIIAVAAIgIAKgAv1IlIAAgFQgCgFgBgIIADgFIAVgMIgIgFQgDgIAIgFQAFgFAFAAQgFgFACgIIAIgCIgXgaIgHgMQgIgDgCgKQgLgcgEgbQgBgIADgFIANgKQAWgNAaAFIAEADQAGgFgKgFIgGgKQgEgKAHgFQAFAAAFgFQgFgLgKgHQACgIANgFQgegFgSgeQgFgKACgFQADgFAFgDQAHgCAGACIAUAIQgHgKAEgFQANgFgFgIIgFgFQgHgCgGgQIAZgCIAAgFQgPgXAfgSQgrgWgIgcIgCgKIAKgIIAFgHQAPgFAKAKIADAFQAEgDAGAFIAIADIAEAFQADAHAFAAIAAgFIACgPQgCgHAIgDQAFgFAJADQATACgGASQAKgFAKAFQAIAFACAHIAAgCIAAgFQAIgKACgKQADgNAHgCIAAgFIADgDIACgIIADgCIAAgDQAAgHAIAAIAPAAQAFACgDAIIADgDIAFgFQADgGAFAGQACAAACADQALAKAAAKIADgIQgBgFADgCQAPgaAZAAIAFAAQAGAQAAARIAAADIAKADQACACgCAIIAAAFIACAAIAIgDQAHAAAAAIIgDACIgEAZIARAAIAXAAQACAAADADQAIAKAAANIgIAjIgCAIQgIAKgQgFIgCAAIACgDIgCAAIAAADIAAACQgFADAAAHIAHgCQALAAgDAKIAAAHQADgCADAAQACgDACAAQALgCACAKIADAIIAHgIIADgFIACgDQAFgFAIgCIAUgDIAIADQAEAFgEAHQgIANgQADIgCACIgCANIAAACQAAAKACADQAAgFACgDQAGgFAIADQAFAAACACQAIAFgDADIACAAIAKACIAIAFQAFAFgDAIIgCAKQABABAAAAQABABABAAQAAAAABgBQAAAAABgBIACAAQAJAIAEAAQAKACAIAIQAFACgCAIQgDAFADAAIAEAAQAFAAADACIACAFIAAAGQAGgLAKAAIACAAIADgPQgCgFAFAAIAJgCQAFgDABAFIAAAFQAAAFAKgCQAPgDANAIIACACQAIAQgGARIgCAKIgIANIgCACIAFAAQAKgCgDAKIAAAIQADgFAFACIAFAAQAFAKAAANQgCACAAAFQgFAIgBAHQAKgCADAIIACAFQADAHAFgHIADgDQAFgCgCgDIAAgDIgIgKQgGgHAGgFIAFgFIAAgDIgDgCQgDgFADgDIANgCIgHgIIgDgIIgDgCQgDgSANAAQABABABAAQAAABAAAAQABAAAAgBQAAAAAAgBIADgKIAEAAIgCgFIgCgFQgDgFAAgIIADgRIACgNIAFgMIADgIQAHgNAKgHIAFgDIANAAIACAAIANAIIADAFQAJAFgCAKIAAADIANACIACADIAFACQAAgKAFgFQAIgNAKAAIAFADIAIAFQAAgKACgKIAIACQACAIgCAKQACgIAFgFIADgFIACADQAGAHgDAKIAHgKIAAAIQABAKAFAAQAMgCAAAKQAAgIAHgKIAGgKQAHgUANgKIAFAAQAKgIAKAAIAMAAQAFAAAGAFQAPANAAAUIAAACQAFAFAHAAIADADIADACQAKAGgBAKIAAACQAQAAACANIAAgFIADgDIAHgPIAGgFIAbADIACACQABAKAFAKQAFAIgCAKIAAAcIAAACIgDAFIgDAIIgFAHIAAADIACAHQADANgKAAIACAFIADADQAGAHgEAFIADADQAAgBABAAQAAgBAAAAQAAAAABAAQAAgBABAAIAAAAQAFgFAIAAIACAAQACgHAGgFQADgDAEAAQADgCADAAQACACAAgFIACgFIADgDQAaADAEAFIAIAKQAAASgNACQgCANAXgUIAKgDQANgFAJAFQADAAADAKQAEASgMAPQgFAFAAAIIAPgDIAUgCQgCAggaAVQAPAMAIANQADACgDAIQgNAZgRANIADAHgAExDvIgFgGQgDgFgKADIAFgSIAAgHQADgKgGgKQAAgBAAAAQAAgBAAAAQAAgBgBAAQAAAAgBAAQgHAAgDADIAAgGQADgMgLACIgEAAQAHgFADgKQAAgFACgCQAIgSgIgSIgCgCQgNgIgSAFQgFAAgDgFIAAgFQABgCgIAAIgFACQgIAAAAAFQADAIgDAHIgCAAQgKAAgFAKIAAgFIgDgFQgCgFgGAAIgEAAQgBAAAAAAQgBAAAAgBQAAAAABgBQAAgBABgCQACgFgFgFQgIgHgKgDQgEAAgIgFIgCAAIgGAAIADgKQACgIgFgFQAAgFgEAAIgOgCIgCAAQADgFgFgDIgDgCIgIgDQgHgCgFAHIgCAFQgDgCAAgIIAAgFIADgNIACgCQAPgDAIgMQAEgGgEgFQgDgCgFAAIgRACIAAgFQgDgFAHAAIAGgCQAMgDAHAIQgCgFAAgFQgCgDAAgFQgGgeASgaIAGgFIAUAXIACAAIAIgFQAEAAADAIIACACIAAgCIAGgIIACgCQADgFAFAHIAAADIAKAWQAFgKAHgHIAQgVIAEAAQALgCAHAKIAPAeIADAIQAAAPgMAKIgDADIAAgDIgDAAIADADIADAMIAFgHQAHgIAFAIIACAFIAAgFIADgFQAIgKAHACIAIADIAAgNIAAgFIAAgFIACgPQAGgNAHgKIAIgCQAHgDAAAKQACASgJAMIAAADIAEANIADAFIADACIACAfIAFAjIADAKIgIANIgFAHIgFAQIgCAKIgDAUIADAKIAAAFIACAFIgFAAIgDAKIgCADQgNgDADASIADAFIACAFQADAIAEACIgMADQgCACACAFIADADIACAAQgFACAAAFQgFAFACAFIAIANQACAFgEAAIAAADQgDAEgCAAQAAAAgBgBQAAAAgBAAQAAgBAAAAQgBgBAAgBgAIZgvQgHgIgKgFIgQgCQgMACAAANIACAKIgIgFQgMgNgKgRIgDgDIADgFIAAgCQAAgIgGgFQgCgDACgFIAGgHQACgFADACIADADQAFACACgHQAKgSANgFIAEAAQAQADACAUIADAKIADAMIAAAFIACgFQAHgKAGAIIACAHQAAgFAFgFIADgCQAKACAFAKIADAGIgQAPQgDAHAFAKIAGAKQgFADgEAAQgJAAgKgKgAIviCQgJgIAHgKIAIAUgAIeiyIgHgQIgFgKIgDgPQgDgCAAgFQgCgPACgNIADgIIAKgKIAAgCQAHgFAIAAIAFgDQAIgCAFAHIADADIAHgIIAFgCIAFgDIACAFIAAgCIgCgFQgFgFAAgIQgDgPAIgKIAFgDIAHgCIgKgSIAIgCQAIACACAIIgCgQIAAgCIACgFQAHACAGAIQgDgFADgIIAFAFQADAIAFgFQAHgNAFAFIAFADIgDgDIgCAAQgFgFAAgKQACgHgCgIQgHgSAEgRIADgIQADgMAHgIIAIgNQAFgFAHgCQAQgDAMAPIACAAQAGAAAEgFIADgFIAFAAQAHgFAIAIIAAACQAKgMAKAFIAAgDIgCgFQgCgHAAgKIACgFQAIgQAMgKIACAAQAIAIAIAAQAHACAGAIQAFANAHAKIACAFIAAACIADAKIAAAIIADACQACAAACADQALAIgIAKIAIAAQAFAAACAFIADACIAAgFQADgHAHgIIAAgDQgCgHACgKQAAgFACgDIADgFQAFgFgCgCIgDgFIADgDQARgUAFgDQAIgCAFAFQAKAKgHAPQAFAKAFgjIACgKQAHgPAIgFQADgDAHADQAQAKAAAWQAAAIACAFIAKgPIAPgUQAQAbgGAhQAQgCAMACQADAAACAIQAIAcgIAZQAFACACgCIgjBEIgKgIIgIgCQgRACAAAKQgMgegaAZQgIAIgCAFIAAAKIAAAKQAHAmAYAhQgTgKgKAUIgCAFQAAAIACAFQgRgfgXAVQgFAFgDAHQgKgegWgNQgCAAgDADQAPgXgNgoQgPgmgZBBQgpgtgUgCQgegGADBAQgBAZAKAZQgWAFgUAPQgGADgFAFQgKgZgZARQgUgHgCARIACAKQgUgUgcgHQgDgDgEADQgVAPALAeIACANg");
	this.shape_7.setTransform(142.3,316.9);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.f("#003300").s().p("AjEIyIgCgFQgDgIgKADQAAgIAFgHQAAgFADgDQAAgMgFgKIgFAAQgFgDgDAFIAAgHQADgLgKADIgDgDIAIgMIACgKQAFgSgHgPIgDgDQgMgHgQACQgKADAAgFIAAgFQAAgFgFACIgKADQgFAAADAFIgDAPIgCAAQgLAAgFAKIAAgFIgCgFQgDgDgFAAIgFAAQgCAAACgFQADgHgFgDQgIgHgKgDQgFAAgIgHIgCAAQgBAAgBAAQAAABgBAAQgBAAAAgBQgBAAAAAAIACgKQADgIgFgFIgIgFIgKgDIgCAAQACgCgHgFQgDgDgFAAQgHgCgGAFQgCACAAAFQgDgCAAgKIAAgDIADgMIACgDQAQgCAHgNQAFgIgFgFIgHgCIgVACQgHADgFAFIgDACIgCAFIgIAIIgCgIQgDgKgKADQgCAAgDACQgCAAgDADIAAgIQADgKgLAAIgHADQAAgIAFgCIACgDQAQAFAHgKIADgHIAHgkQAAgMgHgKQgDgDgCAAIgXAAIgSAAIAFgZIADgDQAAgHgIAAIgHACIgDAAIAAgFQADgHgDgDIgKgCIAAgDQAAgSgFgRIgFAAQgZAAgPAbQgDADAAAFIgCAIQAAgLgLgKQgCgCgDAAQgFgIgCAIIgFAFIgDACQADgHgFgDIgPAAQgIAAAAAIIAAACIgDADIgCAIIgDACIAAAFQgHADgDAMQgCAKgIAKIAAAFIAAADQgCgIgIgFQgKgFgKAFQAFgRgSgDQgKgCgFAFQgHACACAIIgCAPIAAAFQgFAAgDgIIgFgFIgIgCQgFgFgFACIgCgFQgKgKgPAFIgFAIIgLAHQgCgFAAgHQgFgPgDgVQgFgeAKgUQAIgSAXADQARAAAKgKIAIgKQAKgSAKAPIAIAFQAKgFAFAIIAXAeIAFACIAAgKQAAgMAMAAIAKACQANADAFAHQAPAQAKgIIgCgKQgFgKACgFQAFgKAKgIIAFgFIgjhgQgHgfARgMQAFgFAFACQAXAIAUAUQgCgFAAgFQAAgSAWAIQAVgSAKAZQACgFAFgCQAUgPAVgIQgIgXAAgbQgChAAZAGQAUAFAmAqQAWg/ANAmQANApgPAUQAFgDACADQAUAKAIAeQACgHAFgFQAXgaAPAhIgCgMIACgGQAKgUAQAKQgVgjgHgjIgDgKIADgKQACgIAFgIQAagWAMAeQgCgKARgDQAFgCADACQASAPAKAXIAHgKIAFgFIAFAAQAVgFAKASIACAFIgCArIAMgIIAIgFQAFAAACADQAIAFACAMQADgHAIACIAWADQAFACAFgFIADgCIAFgDQAIAAAAAFIgDAIIgFARIACAAIANADQAFACAAAFIAAAIQAAAKgFAIIgCAFIgIAHQgKAIgIgFIAAACIAAAVIgCAHQgIARgRABQAKAAACAIIgCASIgFAHIgIAZIAFgFIANgKIAFgFQAPgHANAMIAAADQAFgDAHAFIAFAFIAEAFIAHAIIAAgIIACgMQAAgKAFgDQAIgFAIADIAKAFQAFACAAAIIgDAFIADAAIAWA8IgHAFQgKAIgDAKQgCAFACAKIAFAKQgKAIgPgPQgIgIgKgCIgKgDQgNAAAAANIADAKIgFgDIgXghQgFgFgNAFIgFgFQgKgPgKASQgNAUgWgCQgXAAgKARQgIAUAFAfQAFAeAIAcQAHAcArAWQgeASAPAXIADAFQgDAHgZgCIADAFIgNAAIgFACQgKAIgIAMIgCAIIgFANIgDAMIgCASQAAAIACAFIADAFIACAFIgFAAIgCAKQAAAAgBAAQAAABAAAAQAAAAgBgBQAAAAgBAAQgNAAADARIADADIACAIIAIAHIgNADQgDACADAFIADADIAAACIgGAFQgFAFAFAIIAIAKIAAADQADACgFADIgDACQgDAEgCAAQAAAAgBAAQAAgBgBAAQAAgBAAAAQgBgBAAgBgAEaChQgDgIgKADIgFAAIgFACIAAgFQADgKgKAAIgIAAIAAgFIADAAIAHgFQANADAFgIIACAAQAKgCADgNIgIgDIADgZQACgPgKgKIgCAAIgUgDQgIgFgKgCIAFgSIAAgCQACgIgHADIgDAAIgHAAIgDAAIAAgDQADgHgDgGIgKgCIAAgDIgCgeIgFAAIgQAAQASgKASgDIgIgFQgHgNAUgMQACghAXAKIAAgNQAFgbANgaQgUgHgQgSQglgtAWgUQAPgQA1AAQgWhEAhASQAeAPACAcQAAgFAFAAQAVgKAWAMQgCgFAAgHQACgmAfAKIgIgIQgDgCAAgFQgFgUASgIQgjgFgZgUIgGgFIgFgKQgHgDACgMQADgpAcANQgIgIAKgRQADgGACAAQAUgFAXAFQgCgFAAgFIACgKIADgHQAMgUAPAFIAFACIAXAeIAFgRIADgIIAFgFQAKgFAHAIQAAgIAFgFIASgSQAFgFAAgHIAAgFIAFgFQADgFACACIADAIIAHARIADgCQAFgIAFgCIAFAAQADAAACACQAIAIAAAKIACAIIgCAMQgDAKgHAIIAMAPIADAHQACAVgKAPQAIgKAFAFIAHAPIADAKIAKAZIAAgCIAAgIIACgSIAAgHQAIgUANAAIAFAAQAAgFAKgDIAFgCIAFgDQAHADADgDIgDgFQAAgIgHgCQgFgFACgKQADgIAHgHQAFgFAGAAQAFAAACAFIADACIAAgCIA1AWIgDAKQgCAQACAKQAAAHAKADIAIACQgDAPgUADIgSAIIgHAKQgIAMAFAIIAIAFIgFACIghgCQgIAAgFAPIgHADQgPgDACAXQADAWgSAVQgSAUAFAUQAGAXAUAPQAUASAXAPQAUAMAogWQgHAlAUAFIAFAAQACAIgUASIAIAFIgKAKIgDAHQgCANACAPQAAAFADADIACAPIAFAKIAIAPIAFAFIAKAXQgIALAKAHIAFADIAQAoIgFAFIgDAAIgCgFQgGgKgKgDIgCADQgFAFAAAFIgDgIQgFgHgHAKIgDAAIgCgNIgDgKQgCgUgQgCIgFAAQgMAFgKARQgDAIgFgDIgCgCQgDgDgCAFIgGAIQgCAFACACQAGAFAAAIIAAADIgDAFQgFgFgKAFQgFACgDgFQgKgSgMAVIgFAHQgNAIgPAAQgBAAgBAAQAAABgBAAQgBAAAAgBQgBAAAAAAQgZACgLASIAAAFIgCACIgDALIgCgDIgDgFIgFgNIAAgCQAKgNgCgRQAAgLgIADIgHADQgIAKgFAMIgCAPIAAAFIAAAFIAAANIgIgCQgIgDgHAKIgDAFIAAAFIgCgFQgFgHgIAHIgFAIIgCgNIACgCQANgKAAgQIgDgHIgPgeQgHgLgLADIgFAAIgPAUQgHAIgFAKIgKgXIAAgCQgGgIgCAFIgDADIgFAHIAAADIgCgDQgDgHgFAAIgHAFIgDAAIgUgXIgFAFQgSAZAFAeQAAAGADACQAAAFACAFQgHgHgNACIgFADQgHAAACAFIAAAFIgCAAQgIACgFAIIAAACIgFADIAAACIgIAIg");
	this.shape_8.setTransform(120.6,301.4);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.f("#234600").s().p("AikFoIgFgFQAXgKAKgcQACgFgCgFQgFgMgSgNQAZgUAIgeIgZACIgQAAQAAgFAFgFQAQgSgFgRIgIgKQgKgFgPAFQgFAAgFAFQgXAUADgNQAPgFgFgRQAAgFgFgFIghgGIgDAAIAAAIIgHADIgFAAQgFAAgDACQgIAIgCAHIgDAAQgKgCgFAFIgCAAIgDgNIgFgCIAAgDIgCgCQAKAAgDgQIAAgFIgCgCIAFgIIAFgHIAAgDIACgFQgCgMACgQQADgHgFgKIgIgUIAAgDIgegDIgFAGIgNAPIAAACIAAAFQgFgMgPAAIAAgDQADgNgNgFIgDgCIgFAAIgKgFIgCgDQACgUgPgNQgFgDgHAAIgQAAQgKAAgKAEIgHACQgPAKgFASIgIAPQgFAIAAAHQgDgKgMAAQgIADADgIQAAgFACgDQgHADgDAFIgCgSIgFAAIgDADIgIAMQADgKgDgHIgHgDIgFAUIAAADIgIgIQAAgCgFAAQgMACgIAKIgCAQIgDAAIgFgFIgDAAIgMgDIAAgCQACgLgKgFIgFgFIgMgHIgDAAIgCgFQAZACAFgHIgFgFQgPgVAggSQgtgWgIgcQgHgcgFgeQgFgfAHgRQAKgUAaAAQAZACANgSQAMgRAKAPQADAFACgDQAPgFAFAIIAXAhIAIACIgDgKQAAgMANAAIAMAAQANACAFAIQASAPAMgIIgFgKQgFgKADgFQAFgKAKgIQAIgCAAgDQgNgegKgeQAHgDAKADQAIAHADAIQAAAAABAAQAAgBAAAAQAAAAAAgBQgBAAAAgBIACgFIAKgWQADgKAKgFIAAgDIAAgFIACgFIADgDIACgCQAAgKAIAAIAPACQAHAAgBAJIACgBIACgFQAFgFAFACIAFAFQAKAKAAALIAFgIQAAgFADgDQAPgbAeAAQAFAAAAACQAGAPAAASIAAACQAFAAACADQAFADgCAKIACACIAIgCIACAAQAIAAgDAKIgCAAIgFAbIARgCIAaAAIAFACQAHALAAAPQAAAPgFARIgCAAIgDAIQgHAKgQgFIgCAAIgDADQgFACAAAIIAIAAQAKgDAAAKIAAAIIADgDQACgCAFAAQAKgDADAKIACAKIAIgKIAFgFIACgCIANgIIAXgCQAFAAACACQAIAFgIAIQgHAMgSADIAAADQgDAFAAAHIgCADQAAAKACACIADgFQAFgHAKACIAIADQAHAFAAAFIANACQAFAAACAFQADAFgDAFQgCALACAAQADAFADgDIACAAIAPAIQAKACAIAIQAFACgDAFQgCAFACADIAFAAIALACIAAAFIACAGQAFgLAKAAIADAAIACgMQAAgIAFAAIAIgCQAFAAAAAFIACAFQAAACAIAAQAQgCAPAHIACADQAIAPgIASIgFAKQAAAJgJAFIAEgCQANAAgDAKIAAAEQADgBAFAAIAFABQAFAKAAAKIgDAKIgHAPQANgDAFAFIACAIQADAFAFgHIACAAIADgGIAAgCIgIgKQgFgIAFgFQADgFACAAIgCgFQgBgBAAgBQgBgBAAgBQAAAAABAAQAAgBABAAQAKgCACgDQgFgCgCgEIgDgHQgCAAAAgFQgDgPANAAIACAAIAFgKIADAAIAAgFIgDgFQgCgFAAgIIAFgUIACgKIAFgNIAFgIQAIgMAKgFIADgFIARAAQAIACAHAFQAAAFADAAQAKAFgCANQAFAFAHAAIADAAQAFAAAAADIACAFIAAgFQAAgIAFgIQAIgKAMgCIAGACIAHAIIAAgDIADgUQAFAAACAFIADAPIAHgKIADgFIACADQAFAFgCAKQACgFAIgDIAAAIQgDAIAFAAQAQAAAAAKQAAgKAFgIQAFgDACgKQAIgRAPgKIAFAAQAKgIAKAAQAIADAHgDQAIAAAFADQAPAMAAAXQAFAFAIAAIACAAIAFAFQAKAFAAAKIAAADQAPACADAKQABAAAAAAQABAAAAAAQAAgBgBAAQAAgBgBAAIADgFQACgIAIgHQACgDADAAQAPgDAPADIACADQAAAKAGAMQAFAIgDAHIgCAcIAAAFIgDAFIgFAIIgDAFIAAACIADAIQACALgMACIACADIAFAFIADAKIAAAFIACgFQAIgFAIACIACAAQAFgHAIgFIAFgFIAFAAQAHACAAgDIAAgFIADgDQAcADAFAFQAHADAAAFQAAAPgMAFQgDANAZgUQADgFAIAAQAMgDANADIAFAKQAFAUgPAPIgIAKQgCADAWgDIAXgCQgFAggXAVIAXAWQACAFgFAFQgKAcgWAKQAHAPASADIAKAFQAFADASAPIgwAFIAyArIhBAUQA1AcAHAqg");
	this.shape_9.setTransform(312.5,335.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

	// Layer 26
	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.f("#45524E").s().p("ABNDsIgKhOIgMhxIgHhUIgQBFIAAgKQANhZAAgjIgVA1QADg2AFg3IAAgNQABgggJgxIAAAIQgHAVgNAqIgFASQgDAAgCAFIAAAIIgZA/IAZheIACgEIABgDIABgCQACgIACgFIAPhMQAFgDAAgHIACgQIABAAQACgHAFgDIAAgOQAGgXAHgNQAIgjAUgfIACAAQAAAIgIANIgMAoQgHASgDAXIAAAKQgDAPAAAZIAAAUIAGgXIAHAAIAAAwIAABCIgDBlQgFAZALBZIAeDtIAUCYQgPhKgQh8gAhmFPIAAACIgFAOgAhVEYIALgfIAziUQAAgBAAgBQAAAAAAAAQAAgBABAAQAAAAABAAIhAC9IgPArgAgVBiQgfBighBbg");
	this.shape_10.setTransform(185.5,268.4);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.f("#FF9900").s().p("AgGgSIAFgCQADANAFAcQgIgngFAAg");
	this.shape_11.setTransform(297.3,282.4);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.f("#F9FAF5").s().p("AFICpIAAgTIAHAAIADAeQgFgIgFgDgAExCCIACAAIgKhNIAKASQAKAVAFAUIAAA1QgHgVgKgOgACXguIgFhLIAPBbgAkyh0QgSgXgFgeQAFAUAZArQgFgDgCgHgAlRizIADAAIAEAJg");
	this.shape_12.setTransform(274.3,280.1);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.f("#EFF3E7").s().p("AE+C/IAAgdQAAAPAHARIADAAIAAACgAE+ChIAAgFIAAgJQAAAGAHAIgAlGi/IACgEIAAAEg");
	this.shape_13.setTransform(277.1,284);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.lf(["#445757","#A0A893","#DED7D6"],[0.039,0.345,1],-47,94.9,11.7,-34.3).s().p("ACFDqIADgCIAAACgAiiiEIAAgFIACAFgACejpIAFAAIgHAFg");
	this.shape_14.setTransform(259.9,291.3);

	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.f("#000000").s().p("AmtCXIAHgKQgCAFAAAFgAGoBYIgCgIIACAAIAFAFQABABAAAAQAAABAAAAQAAAAAAAAQAAABgBAAgAhghKIgDAqQgCAfgZBMQAZhtAFgogAmAiMIAFgKIgCAKg");
	this.shape_15.setTransform(230.3,250.2);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.f("#B6B8AB").s().p("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");
	this.shape_16.setTransform(247.4,277.2);

	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.lf(["#8CDBDE","#B8E2A3","#DED7D6"],[0.039,0.345,1],-191.4,-23.1,119.6,-24.1).s().p("At2HMIAKAPQgvArg2A6QAzg9Aog3gANZDbIAdBNIgHAHQgIglgOgvgAMoB/QgKi0AKAFIADAkIAIAkQAAARACAPQAFAVAAARIAAAmIAAAPIADAmIgNBngAPHDDIAKBEIgEADgAinDpIALACIgLAfgANoCYIgfjIQAUArASCdgAIlBUQgLgmgPgUIAAgCQgKgSgFgDQAAgFgCAAQgagsgFgTIgBgBIgEgJQgDgIADgCIAmAgIAAAAIgCAFIACAAQAHAkAKAXIAIgXIACgGQALgKgLAQQgHAJAcBUIANAzIgGACgAM/BqIAAgPIgCgQIAHAQIAAAPIAAAFQAAgFgFAAgAhnBUIgGAAIAVhgQAMhRAKgZIAIAtQAAAPgFAPIgXBMIgRAzgAGfBKIAAgHIAAgQIACAQIAAAHgAJwhJIAAAKIgIAKIgCAcIAAAHIgKANQAKg8AKgIgAMrguQAFAAAFAcIgCAGgAJmgMIAAgGIACAAIAAAGgAFYhVIAAgRIACARgAhOiSIAFgSIAFAagAg/knIgPBMQgEAFgCAIgAhnjjQAZhEAUhMIgjCYgAgmknQAAgZADgPIAWgKIgEAKQgBAHgEADQgDAPgHAMIgGAXgAiBkgQADgMAHgKIAAAZgAD2kqIACAAIAAADgAg3lCIAAABIAAgBgAgwlZIAAAOQgFADgCAGgACvo/IAHAHQAFADABAFQAFADACAMg");
	this.shape_17.setTransform(193.7,269.8);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.f("#C7C5BD").s().p("AjLEBIAjAAQADAAAAAXQgMgKgagNgADFjLIAHgUIAAAFIAAAUgACzjuIAAgFIAJghQAJgNgJA1g");
	this.shape_18.setTransform(131,303.9);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.f("#536F46").s().p("AABgVIAAAhQgBAFAAAFQAAgXABgUg");
	this.shape_19.setTransform(151.5,279.2);

	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.f("#102C00").s().p("AKIG8IADAAIAMARgAopEQIgGAtQABANgIASIgSBBgAFCEpQAGADgGAHQgBAFgBAGQAAgLACgKgAK9DJQgCgtgFgUIgGgfIAAAaIAAAPIADCQQgDg1gHgmQgCArgKAoIAMhnIgDgmIAAgPIAAgmQABgSgGgUQgCgPAAgSIgIgkIgCgjIgKgoQgDgQgFgCIAFAAIAAACQACAAADAIIAIAmIACAKIAAACIAIAhIAHAiIAAAMIAGASQAEACAAAIIADAZQADASAEANIADAjIADAeIAKAjIAZBPIgehNQAPAvAIAlQgXhEgIgcgAKsBVIAAAPQAEAAAAAFIAAgFIAAgPIgHgRgAraBpIAFADIgQAcQADgPAIgQgAHSAbIAAgCIADgsIAAgFIADgZIgGAZIAAgHIADgcIAIgKIAAgKIACgFIACgKQAFgFADAAIACgDQAIgIAIAAIAPgCQARgIADAFQASgFAlASQgjgKgMAAIgcACQgKAGgNACQgKADAAAKIgCAKQgBAHgFAIQgCAKAAAPIAAAeIgCBjIAAhiIgLA1IgRA1gAGvA3IAVhAIAEgCIAKgNIAAAFQgEAAgGAKIgEAIIgVA7gAK9hZQgMghgLgKIgPgSIgMgPIgzgUIAwAPQAKAFAFAKIAKAFQAFAIAKAHIAUAKIAYANQAKACAAAGQgIgGgKAAIgQgKQgJAAgcgWQAUAUAKAUQAIAIANA1gAHSiYQALgNAAgFQAJgKApgPIgeACQgKAIgVAFQAfgUAWgDIAzgHIAWgDIAOADQgiAAggAPQgsAPgRAPQgKAKgDAFQgJAIgFAAQAHgIAHgCgAmVjzIANgXIgFAXIgFAAIgDACgAi0kwQAHgMADgQQAFgCAAgIIACAAQgJAcgBAKgAibnFQADgHACAAIgDAHg");
	this.shape_20.setTransform(208.4,270.4);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.f("#84A673").s().p("AJeHIIACgKIAAAKgANagaIAAgIIAPBWIACAVgAG8AjIADgDIgFAOgAHGAgIAAADQAAAFgFAFgAGAgOIAMgHQgWAUgcAfQARgaAVgSgANQh9IACAPIAAAFIAAABIgHAKQAFgQAAgPgAtqnCIAAgFQAIARAFAaQgFgVgIgRg");
	this.shape_21.setTransform(102.9,274.9);

	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.f("#4E5C57").s().p("ACdFHQA6hsA1g4IhGDpQAwi7hZB2gAJNgsQgcB3geCiIgDAKIgwBxgAFQFFIAhiiQgHBMgDBtQAAhogXBRgAGwEDIBHkJQgUCGgeCzQAUiGgpBWgAgFC9IgoAZIBuhrIgGAGQgpAogoB0QAMgrAFglgAAaA+IhMANQBtgeAyhMQgtBMhhCAQAhgyAag9gApMCIQBDgKA3hIQgqA6gvAuQASgWgzAAgAk0BfID9h+QhbBEhWA6QAZgrhlArgAFqibIAAgDIACgCIABgCIAEgNQAKgzANgeIghA9QA9h6gpAfQADgIAFgCIACAAIAhgXQgKAPgCASQgDAmgPAjIgPAmIgFAMIgUA4QAHgZADgXgAGIjDIAAgIQAPgeAXg9IgNA9IAAAPQgCACAAAFIgXA4QAIg1gIANgAHMlOQgUAwgNBEQADgmAHgcIAFghIALgUQAFgMAjgfIAPgPQg6CNgNBgQADhVAUhbgAGmjQIAAACIgFAYgADmjkIAIgFIASgKQAUgFAKgIQAKgDADgFIACAAQAcgMAcgKIgpAmIADgQIhtAuQAIgFAMgFg");
	this.shape_22.setTransform(110.6,295.9);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.f("#27302E").s().p("AEeDJIAKhFIAKhRQAFgEAAgFIAAgDIAPgmIAHgRIADAIIgKATIAAAIIgKAgIAAAGIAAACIgNA9QgHAlgCAuQgIAYgEAcgAFVBhIAAgUIAAgFIAAgBIAGgOIgDAZIAAAHIgDApgAFWBDIgBAEIABgEgAC0BDIAUgjQAXgiAbgeIApgmQgcAKgcANIgCAAQgDAEgKADQAGgHAHgDQAUgIARgMQAagVAmgWIAqgcIAmgeIAVgSIANgMIAFAAIAAgDIAegmIALgLQAAABgBAAQAAABAAAAQAAABAAAAQAAABAAAAIgSAeQgKASgXAWIgPAQQgjAegFANIgLAUIgEAhQgIAbgDAmIgEAIIgGAbIgEgNIACgQIgDAMIgKgYIADgHIAKATQAAgEACgCIAAgPIANg9QgOAlgLAaIgDgIQAGgXACgYQACgSALgPIgiAXIgCAAQgFACgDAIIgCAAQgFADgDAEIgvApIAAAFIgDACIgMAIQgVASgRAZQgKAPgIASgAFYAwIAAANIgCAGgAFYAaIAAgEIACgGIABAEIgDAcgAk5gCQBdgUgyAcIjhANgACYgKQAKgHAKAAIgHAFQgNAFgIAFgAE5hGIAXAvIgDAHgACvgWQAbgQASgCQgKAIgUAFIgSAKgAHvj+IgBABIABgBg");
	this.shape_23.setTransform(117.2,274.3);

	this.shape_24 = new cjs.Shape();
	this.shape_24.graphics.lf(["#445757","#A0A893","#DED7D6"],[0.039,0.345,1],-106.5,-15.2,56,-70.8).s().p("AGJIBIAAgCIAKACgALbHtIAFAAQAAAAgBAAQAAAAAAABQgBAAAAAAQAAABAAABgAL5EHIAAgCIAKAIgAr+oAIAAADIgEAFg");
	this.shape_24.setTransform(109.5,228.9);

	this.shape_25 = new cjs.Shape();
	this.shape_25.graphics.f("#DEEFD3").s().p("AWBMBQghiXgKgLIgKg3QgKgzgKgbIAtA1QAVAKAFAPQACADANBOIgDAAQghhjAAApQAAAUAaCzgAMKLJQAFgHgFgDIAKhGIgCAzIAAAOQgDAPAAATQAAgOgFgFgAUBHdIgQh6IADgDIASApIAUAhQACAKAIAPIAFAFQAPASADARIACANIgFACQgIAFgWgsQAAATAjDZQgyjUgKgOgArWHDQAygehdAUIi2AXIi6AUIGvhGIhYgOQCAgHB8gUIg/AwIlXCIQCzhLArgfgAC8EkIAFADIgjB5gACUC1QAPhyghgEIgrBDIAKg8QhHACiICgIBviLQjNAliNDhIA8ikQhsBUhTBaIAtg/Ih8BiQBehaBshMIAIgNQANgRAMgNQAegeAmgUIAhgVIAUgCIAKAAIAAgFQEMhGAKAAIA1gFQAVgIAogXIgZBUIgUBGIAUgzQgNBMg/DhQAmiVAIhRgAs+FeIAfAFQAoAVAuAKIhWAEgAvdEOQhJgchEgmIB8AjQAUACAUAJQhBhZgzg9IgKgMIgUgVQhRhggegUIA1AZIhEhkIAAgDIBvBnIAHAYIADAHQAtBWA/BMQAfAtAwAoIAFAGQAKATAPANgA0EgSQhliqgejUQgejTB7FeQBdEigTAAQgJAAgbgvgAzWiRQgXgzgNgqQAXghg1jyQArCSANBvQAFg1gkikQAcA4AUA8IASCOIACAoQAIAoAPAkgAGVjQIAAgDIAFAAIAAADgAz/n/IgCgbIgKjrIAbDRIgHiDIACAAIAKA8QAQBbAAAuIAFA6IgFACgA1FnhQgphdgCiAIARBbQAyCegIAAQgDAAgNgcg");
	this.shape_25.setTransform(162.8,229.8);

	this.shape_26 = new cjs.Shape();
	this.shape_26.graphics.f("#CAF0EA").s().p("ADXIXIgFgKIAKAKgAi/A2IAFACIAAADgAi/j8IAFgDIAAASgAjbn9IAAgSIAAgIIACAAIAAAIIAAASg");
	this.shape_26.setTransform(57.6,211.2);

	this.shape_27 = new cjs.Shape();
	this.shape_27.graphics.f("#50625F").s().p("AkHHNQAhB3AWA+IgNAEgADgFwQgMg6g/hRQAyA8BBBZQgTgIgVgCgAhHmBIAEgFIAVAuQAjCkgFA1QgNhwgqiSgAg7mVIAKgFIADAbQgDgMgKgKgAg7qFIADAAIARA9IAAASIAHCCg");
	this.shape_27.setTransform(39.6,216.9);

	this.shape_28 = new cjs.Shape();
	this.shape_28.graphics.lf(["#445757","#A0A893","#DED7D6"],[0.039,0.345,1],-93.3,-2.2,91.8,-2.7).s().p("ADEXfQgoBggUBZQAHjzhBADQAZhCAPgyIAKgfIAIgPQAqh0ApgoIAGgGIhwBrIgKgPIAFgKQAHgFADAAIAAgIIAHgDQBjiAAthOQgyBOhvAeIgFAIQjIBLjtBRQB+g1CIhbQBUg6BbhGIj6CAIhCAfQiuBQg9AAIApgmIAMgKQAvguAqg6Qg3BIhDAKIgHAAIgpACQgtgCgcgNIg8goQgegSghgDQgyhGgxiIIANgDQgWhYgVjFQArCNAyBgQgoh+gPhWQgShdgKiiQAIASAFAUQgFgZgIgSIgShBQgOg9gIhEQgDgcgUhlQgUhjAokLQgeA3gPDjIgSlKIgDgwQgKhHgChBQAAg6gFg6IAAgkIAAgIQABgMAEgIQAAgRgFgPIgCuKIBGE5QAeCDAaBTQgag3gThCIBDEGIClJcIgDAAIAKDqIgKAFQgehGgNhFQgKg1gwlhQAcEvAFAUQAHAUAIArQAKAmAAAjQADAkAHAMIAZAzIgEAHQA1DxgYAhQgRg1gMgzQAMByAmBaIAKAIIAFAFIBEBlIg1gZQAfAUBQBiIAUAVIALAMQA/BRAMA6Ih8gjQBEAlBKAcIBYAXQAaAcAiAUIAFAKIAGAAIAeAoIhRAFQAAAFADADIBTgDIBYANImvBHIC6gUIDhgNQgrAeizBMIFWiIIAAAFIACAAQAZgFAUgSIACgDIB9hiIguA/QBThbBshTIg8CkQCNjhDPglIhxCKQCLifBHgCIgLA8IArhEQAhAFgPByQgIBRgmCVQA/jhANhMIAKADQAABBgIA1IAYg8IAKAHIgaByIgeB8IAjh6IAZg/IAAgHQADgFADAAQAHgKgbB0IAYhHIAKAFQgJA1gLBJIgUBjIADACIgyCVIgLgCQgUA8gUAzQh3ExAciNIAAgKQAfiiAbh5IhtGWIhQECQAgipAViOQAeizAUiIIhHELIhQFNIAIjCIAAgUIAAgeQAChtAHhMIghCiQhaGZhMi5QgFgMAVhMIAMhBIBHjpQg1A4g7BsQhGCGhKDUQAGhnARhUgADEVhQAAgXgCAAIgjAAQAZANAMAKgAIoSXIAShBQAIgSgBgNIAGgtgAINNVIgBACIgCACIAAADQgPBOhHC2IBWi7IgKBEIgEA3QAEgdAIgXQACguAHgmIANg8IAKAAQAAA1gKB5QAKhgAIgtIAHAFIAAAhIADgpIAAgHIADgaIgahiIAFgNIAjAzQANhEAUgwQgUBbgCBVQAMhgA6iNQAXgXAKgRIASgeQAAgBAAAAQAAgBAAAAQAAgBAAAAQABAAAAgBIABgBIgBABIgLALIgeAmIAAACIgFAAIgNANIgVASIgmAeIgqAcQgmAWgaAVQgRAMgUAIQgHACgGAIQgSACgbAQIgDAFQgKAAgKAHIgIAIIgCAAQgmAZg3A6QB+hbAtgRIANgFIghBBIgUAjQgIAQgDAPIAQgcQAIgSAKgPQAbghAXgUIADgCIAAgFIAvgpQADgFAFgCIACAAIAIAeQgJAXgSAmIh9DlICEjPgAJHMnIAAACIgGAYIAKgkgALbIFIAAACIADgCIAFAAIAFgXgArcgsQAeDSBkCpQBeCpiDmbQhNjZgSAAQgKAAAMBQgAqbh9QAuBehIjhIgRhbQACCBApBdgAIbLZQAZgSgMAjg");
	this.shape_28.setTransform(94.6,194.2);

	this.shape_29 = new cjs.Shape();
	this.shape_29.graphics.f("#5B6B66").s().p("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");
	this.shape_29.setTransform(158.3,198.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_29},{t:this.shape_28},{t:this.shape_27},{t:this.shape_26},{t:this.shape_25},{t:this.shape_24},{t:this.shape_23},{t:this.shape_22},{t:this.shape_21},{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10}]}).wait(1));

	// Layer 25
	this.shape_30 = new cjs.Shape();
	this.shape_30.graphics.f("#DBDCD5").s().p("Aj5LXInHAAQgvrGCsn6QCKoDCsEyIAhBlQCpD9B3CbIAoh9QBKj2EXE2QEUE3gGFUQgKFUgYA2QhMASh4AAQkHAAnXhWg");
	this.shape_30.setTransform(242.8,290);

	this.timeline.addTween(cjs.Tween.get(this.shape_30).wait(1));

	// Layer 24
	this.shape_31 = new cjs.Shape();
	this.shape_31.graphics.f("#295C07").s().p("ABvB3IAEgCIAAACgAhyh2IADAAIgDACg");
	this.shape_31.setTransform(360.5,348.5);

	this.shape_32 = new cjs.Shape();
	this.shape_32.graphics.f("#42950B").s().p("AAAAAIAAgBIABABIAAACg");
	this.shape_32.setTransform(352.4,325.9);

	this.shape_33 = new cjs.Shape();
	this.shape_33.graphics.f("#063201").s().p("AgkCZIksgFIAAgHQAAgBAAAAQAAgBAAAAQABAAAAgBQABAAAAAAIAKgHIgFgDQAAgFAFgCQADgDADAAQgBAAAAgBQgBgBAAAAQAAgBABAAQAAgBABgBQAAAAAAgBQAAgBAAAAQABAAAAgBQABAAAAAAIgNgPIgFgFIgFgHQgFgPgCgSQgBgBAAAAQAAgBAAgBQAAAAAAgBQAAgBABAAIAHgFQAPgIANADIADACQAFgCgFgFIgFgFQgDgFAFgDIAIgCIgKgKQACgDAIgCQgSgEgKgPIgDgKQADgCACAAQADgDAFADIAKACQgCgFACgCQAIgDgDgFQgCgCgDAAQgCAAgDgLQANADACgDIAAgCQgKgNASgKQgZgMgGgQIAAgFIAGgFIACgCIADAAQAHgFAIAHIAHADIADACIACADIADACIACADIAAgFIADgDIAAgFQgDgCAFgDQAFgCAGAAQAHACAAAIIAAACQADgCACAAQADAAACACIAIAIIAAgFIAFgNQACgFAFgCIAAgDIADgFIAAgCQACgFADAAIAKAAQADACgDADIADAAIACgDQADgCACACIADADQAFACACAIIAAgFIADgFQAHgNASAAIACAAIADASIAAACQADAAACADQABAAAAAAQABAAAAABQAAABgBABQAAAAgBACIAAACIADAAIACgCIADAAQACAAAAAFIAAACIgCAKIgCADIAJAAIADAAIAMAAIADACQAFAGAAAHIgFASIgDAFQgCAFgKgDIgDAAQgCADAAAFIACAAIADgDQAFAAAAAIIAAACQAAAAAAgBQAAAAABgBQAAAAAAAAQABAAAAAAIADAAQAFAAACAFIADADIACgDIADgDIACgCIAGgFQAHgDAIADIACAAQAFACgFAFIgFAIQgFAAgCADIgDAAIAAAHIAAAIQAAgBABAAQAAgBAAAAQAAAAABgBQAAAAABAAQAAgCAFgDIACADIAFAAIADAFIAHACQADAAACADIAAAHQgCADACACIAFAAIAFADIAIACIAFAEQADACgDADIAAACIADAAQAFAAAAADIAAAFQAFgFAFAAIACAAIAAgIQAAgDADAAIAFAAIACABIADACIAFADQAHgDAGAFIADAAQAFAIgFAKIgDAFIgCAHIgDADIADAAQAHgDgCAFIAAAFIAFgCIACACIADAKIgDAGIgCAHQAHAAAAADIADAFQACACADgFIACgFIgFgFQAAAAgBgBQAAgBAAAAQAAgBAAAAQABgBAAgBIAFgDIgCgCIAAgDQgBAAAAgBQAAAAAAgBQAAAAAAAAQAAAAABAAIAHgFQgFAAAAgFIgCAAIAAgDQgDgKAIAAIACAAIAAgHIADAAIAAgFQgDgDAAgFIADgIIACgFQAAgFADgDQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIAKgNIADAAIAIAAIAHADIADACQAHADgCAHIAFADIACAAQADAAACACQAAgFADgCQACgIAIAAIACAAQADAFADAAIAAgCIAAgKQAFAAAAACIACAKQAAgFADgCQAAgBAAAAQAAgBAAAAQABAAAAgBQABAAAAAAIADAAIAAAKIAFgFIAAAFQgDAFAFgCQAIAAAAAFIACgIQAFgCAAgFQADgKAKgFIADAAIAMgDIAIAAQACgCADACQAKAIAAAMIAFADIACACIADAAQAFADAAAHQAIAAACAIQABAAAAAAQABgBAAAAQAAAAgBgBQAAAAgBgBIADgCIAFgIIACgCIASACQAAAFAFAIQACADgCAFIgDAPIAAADIgCAFIgDACIAAAFQADAIgFACIACADIADAHIAKgCIAIgIIACgCIADAAIAFAAIAAgFIARACQAFADAAAFQADAHgKADQAAAIAMgLQADgCACAAQAIgDAIADQACAAAAAFQADAKgIAIQgDAFAAACIAIAAIAPgCQgFARgMANIAMANIAAAFQgHAPgNAFIAFAFg");
	this.shape_33.setTransform(371.6,356.4);

	this.shape_34 = new cjs.Shape();
	this.shape_34.graphics.lf(["#CCFF00","#C7D013"],[0,1],6.5,-49.9,37.3,-12.8).s().p("AAAAAIABAAIAAABQgBAAAAAAQAAAAAAgBQAAAAAAAAQAAAAAAAAg");
	this.shape_34.setTransform(334,340.7);

	this.shape_35 = new cjs.Shape();
	this.shape_35.graphics.f("#336600").s().p("AAFDLIgLAAIhOAAIgCgGQAKgFAHgPIAAgHIgMgNQAMgKADgRIgNAAIgHACQAAgFACgDQAIgHgDgKIgCgIIgNAAQgFAAAAADQgNAMAAgHQAIgDAAgKIgFgFIgPgDIgDAAIAAADIgCADIgDAAQgCAAgDACIgFAIQgFAAgCACIgDAAIgCgIIAAgCIgDAAIAAgCQAFAAAAgIIgCgDIAAgCIACgCIADgGIAAgCIACgCIAAgQQADgFgDgFQgFgFAAgFIgPgCQgBAAAAAAQgBAAAAAAQgBAAAAABQAAAAAAABQgFAFAAADIgCACIAAACQgBgHgJAAIAAgDQAAgEgFgDIgDgDIgHgCQAAgMgIgIQgDgBgCABIgIAAIgKADIgFAAIgKAOIgCAIIgDAKQgCgFgFAAQgGAAADgFIAAgFQgFACAAADIAAgKIgDAAIgCACQgDADAAAFIgCgKIgDgCIgCAMIgFgFQgIAAgCAIQgDACAAAFIgCgDIgDAAIgHAAIAAgCQACgFgFgDIgDgCQgCgFgFAAIgDgCIAQgDIAAgDQgLgKASgKQgXgNgFgPQgFgQgCgRQgDgSAFgKQAFgKANAAQANADAHgLQAFgJAFAHIADACQAHgCADACIAMASIADADIAAgFQAAgIAFAAIAIAAIAKAIQAHAHAIgFIgDgFIgCgKQACgFAFgDIAFgCQgHgSgFgRIAHADIAIAHIAAgFIAFgNIAFgHIADgDIAAgFIACgDIAAgCQAAgDAFABIAIAAQACAAAAAEIADgCQACgFADADIACACIAFAKIADgDIAAgFQAFgJAKgDIAIgCIAFAAIAAATIAFADIACAFIAAACIAFAAQAFABgCACIAAADIgDAIIAAAEIAKAAIAKAAIADADQAFAFAAAIIgDAOIAAADIgCAFIAAADIgKACIgDAAIAAgCIgCACIAAADIgDACIAFAAQAFABAAAEIAAADIADAAQAAgBAAAAQAAgBAAAAQABAAAAAAQABgBAAAAQAFAAADAGIAAAEIAFgEQAAgBAAgBQAAAAABAAQAAgBABAAQAAAAABAAIAAgDQACgCAFAAIAAgDIAKAAQADAAACADQADACgDADIgMAKIgDAAIAAAHIAAAIIADgCQACgGAFADIADADIACAAQABAAAAABQAAAAAAAAQAAABAAAAQAAAAgBAAIADAAIAFACQAFAAAAADQACACgCAFIAAAFQAAABABABQAAAAABAAQAAAAAAAAQAAgBAAgBIAIAGQAIAAACAFIADAFQgBABAAAAQgBABAAAAQAAAAABAAQAAABABAAIACAAIADACIACADIAAACQADgFAFAAIACAAIAAgIQAAgEADAAIAFAAIACACIAAACQAAADAFAAQAJgCAFACIACADQAFAKgFAHIgCAFQAAAIgFACIACAAQAIgCgDAHIAAABQADgBACABIADAAQACAFgCAFIAAAFIgDAKQAFgCADAFIACACQAAADAFgDIAAgCQABAAAAgBQAAAAAAAAQAAAAAAgBQAAAAgBgBIgCgIQgBAAAAgBQgBAAAAgBQAAgBABAAQAAgBABAAIACgGIgCgCIAAgCIAHgDIgFgDIAAgDIgCgDQAAgJAFACIACgDIADgEIAAgGQgDgCAAgFIADgKIACgFQAAgGADgCIACgFQAAgDAFgFIAAADIAAAFQAGAQAZAMQgSAKAKANIAAACQgCADgNgDQADAKACAAQADAAACABQADAFgIACQgCADACAFIgKgCQgFgDgDADQgCgBgDADIADAKQAKAPASAFQgIACgCADIAKAKIgIADQgFACADAFIAFAGQAFAEgFADIgDgDQgNgCgPAHIgHAGQgBAAAAABQgBAAAAABQAAABABAAQAAABABAAQACASAFAQIAFAHIAFAFIANAPQAAAAgBAAQAAAAgBABQAAAAAAABQAAAAAAABQgBABAAAAQgBABAAABQAAAAABABQAAAAABABQgDAAgDACQgFADAAAFIAFACIgKAIQAAAAgBAAQAAAAgBABQAAAAAAAAQAAABAAAAIAAAIIEsAFIAAADg");
	this.shape_35.setTransform(333.5,351.8);

	this.shape_36 = new cjs.Shape();
	this.shape_36.graphics.f("#003300").s().p("AhqE1IgDgCIgIgDIADgHIAAgGQADgFgDgHIgDAAQgFAAAAACIAAgFQADgFgIAAIADgHIACgFQAFgKgFgIIgCgCIgPgDQgCAAgBABQgBAAAAAAQgBAAAAAAQAAgBAAAAIAAgDIgDgCIgFAAQgBAAAAAAQgBAAAAAAQAAABAAAAQAAABAAAAIAAALIgDAAQgFAAgCAFIAAgDIgDgCQAAgDgFAAIgDgCQADgGgDAAIgKgHIgHgDIgDAAQgCAAACgFIAAgHIgFAAIgFgDIgCAAQACgCgFgDIgCAAQgFgCgDAFQgBAAAAAAQgBAAAAAAQgBABAAAAQAAABAAAAQgCgCACgFIAAgIIADgCQAIAAAFgIQACgFgFgDIgCAAQgIgCgFACIgFAFIgDADIAAADIgFAFIgCgFQAAgGgFAAIgFAAQAAgHgFAAIgFACQAAgFACgCIADAAQAHACAFgFIADgFIACgRQAAgIgFgFIgCgDIgNAAIgHAAQAAgHACgFIAAgDIgCgFIgDAAIgCADIgDAAIADgDQAAgFgDAAIgFgCIAAgDIgDgRIgCAAQgPAAgIAPIAAACIgCAFQAAgFgIgFIAAgCQgBgBAAAAQgBgBAAAAQgBAAgBABQAAAAgBABIgCACIgDADQABgCAAgBQAAAAAAgBQAAgBAAAAQAAAAgBAAIgKgDQgDAAAAAFIAAADQgBAAAAAAQgBAAAAAAQAAABAAAAQAAABAAAAIAAADQgBAAgBAAQAAAAAAAAQgBABAAAAQAAABAAAAIAAADQgCAAgDAHIgFAKIAAAFIgHgHQgFgDgFADQACgIgHgCQgIgDgDADQgCACAAAFIgDAIIAAACIgCgCIgDgFIgFAAQgCgFgDACIgCgCQgFgFgIACIgFADQAAAFgFAAIAAgFIgFgUQgDgSAGgKQAFgKAMAAQAKACAFgHIAFgFQAFgKAFAKIAGACQAFgFACAFIANASIACAAIAAgFQAAgFAIgDIAFADIAKAFQAIAKAHgFIgCgFQgDgFAAgFQADgFAHgFIADgDIgVgzQgFgRALgIIAFAAQAOAFALAKIgDgFQAAgKANAFQAKgKAIANIACgGQANgHAKgDIgFgbQAAgkAPADQAKACAUAaQANgpAHAXQAIAUgIANQABgBAAAAQABgBAAAAQAAAAAAABQABAAAAABQAMAHADAPIAFgHQANgNAHAPIAAgCIAAgFQAFgKAKAFQgMgSgFgUIAAgFIACgIIADgHQAMgNAIASQAAgFAIgDQACgCAFACIAPAUIADgFIACgCIADAAQAMgDAFAKIAAADQADANgDAKIAIgFIADAAIAFAAQAFACAAAIQACgFADACIAMAAQAFADADgDIAAgCIACAAQAFgDAAAFIgCAFIgDAIQABAAAAABQABAAAAAAQAAAAAAAAQABgBAAAAQAFAAADACIAAADIACAFIgCAKIgDACIgFAGIgKACQACAFgCAFIAAAFQgFAKgHAAQAEAAAAADIAAAKIgCAFQgFAFAAAIIACgCIAIgGQAAAAAAgBQAAAAAAgBQAAAAABAAQAAAAABAAQAIgFAHAHIAAADQADgDADADIAFADIADAFIACgDIAAgIQAAgFADgCIAKAAQACAAADACIACAFIgCADIACAAQAFASAIARIgFADQgFACgDAFIADALIACAFQgHAFgIgIIgKgIIgFAAQgFAAAAAIIAAAFIgDgCIgNgSQgCgDgIADIgCgDQgFgHgFAKQgIAKgNgDQgMAAgFALQgFAKACARQADASAFAPQAFAPAXANQgSAKAKANIAAACIgPADIACACIgHAAIgDAAIgKANIAAAFIgFAFIAAAHIgCALIACAHIAAAFIgCAAIAAAFQAAABgBAAQAAAAAAAAQAAAAgBAAQAAAAgBgBQgHAAACAKIADADIAAACQAAADAFACQgDAAgFADIAAACIADADIgDACIgCAIIAFAFIAAAFIgDAAIgCADIAAgDgACdBaQgDgFgFAAQgBAAgBAAQAAAAAAAAQgBABAAAAQAAABAAAAIgCAAIAAgCQAAgFgFAAIgFAAIACgDQACgDADAAIAKgCQAGgDACgHIgFAAIADgPQAAgIgGgFIgCgCIgKAAIgKgFIACgIIAAgDQADgCgFAAIgFAAIAAgDIgDgFIgFgCIAAgSIgFAAIgHACQAKgHAKgDIgDAAQgHgHAMgIQADgSAMAFQgCgCACgFQADgPAFgNQgKgFgIgKQgWgZAKgKQAKgIAcgDQgLgjAQAIQARAKADAPQAAgBAAAAQAAgBAAAAQAAgBABAAQAAAAABAAQAKgFANAIIAAgKQAAgSAPADIgCgDIgDgFQgCgKAKgFQgSgDgPgKIgDgFIgCgCQgDgDAAgHQADgXAPAIQgFgGAFgHQACgFADAAQAKgDANAFIAAgHIAAgFIAAgDQAHgMAKACIADADIANAPIACgIIAAgFIAFgFQAFAAADADQAAgFACgDQAFgFAFgCIADgIIAAgDIACgCQADgFACACIAAAFIAFALIAAgDQADgFAFAAIACAAIADADIADAHIACAFIgCAIQAAAHgGADIAIAHIAAAFQADAKgFAIQAAAAABgBQAAAAABAAQABAAAAAAQABABABAAQAFADAAAFIACAFQAAAHAFAIIAAgDIAAgFIAAgHIADgFQACgKAIgDIACADQAAgGAFAAIADgCIAFAAIAFAAIgDgFIgCgFQgDgDAAgFIAFgHQADgDADAAQACAAADADIACAAIAcAMIAAAFQgDAIAAAIIAIACIACADQAAAKgKAAQgFAAgFAFIgHAFQgDAFADAFIAFACIgFADIgSAAQgFgDgDAKQAAABAAAAQAAABgBAAQAAAAAAgBQgBAAAAgBQgKAAACANQAAANgHAKQgKAMACALQADAMAMAIIAaARQAKAIAWgNQgFAXANAAIACADIgKAMIAFADIgFAHQgBAAAAAAQgBAAAAABQAAAAAAABQAAAAAAABIAAAPIAAAFIACAIIADAFIAFAKIACACIAGALQgDAFAFAFIADAAIAHAXIgFAFIAAgFQgCgFgFAAIgDAAIgDAFIgCgDQgDgFgCAFIgDAAIAAgFIgCgFQgDgNgHgCIgDAAQgHAFgFAHIgDADIgCAAQgBgBgBAAQAAAAAAAAQgBAAAAAAQAAAAAAABIgCAFQgDADADACIACAFIAAADIgCACQgDgCgFACQgDADgCgFQgFgIgIAKIgCAFQgFAFgKAAIgDAAQgPgCgFAKIAAAFIgDAFIAAgDIgCAAIgDgHIAAgDQAFgHAAgIQAAgHgFACIgFADIgFAKIgCAKIAAAFIAAAHIgFgCQgDgDgFAIIAAADIgCACIAAgCQgDgGgFAGIgCAFIAAgIIAAgDQAHgFAAgHIgCgFIgIgPQgDgIgHADIgDAAIgHAKIgIAKIgFgNIAAgCQgCgDgDADIAAACIgFADQgCgFgDACIgCADIgDAAIgKgNQgBAAgBAAQAAAAAAABQgBAAAAABQAAAAAAABQgKAMADASIAAAFIACAFQgFgFgFACIgFAAQgCAAAAAGIAAACQgFAAgDADIAAACQgBAAAAAAQgBAAAAABQAAAAAAABQAAAAAAABIgFAFg");
	this.shape_36.setTransform(307.8,333);

	this.shape_37 = new cjs.Shape();
	this.shape_37.graphics.f("#234600").s().p("AhZDHIgFgFQANgFAHgPIAAgFIgMgNQAMgNAFgRIgPACIgIAAQAAgCADgFQAIgIgDgKQAAgFgCAAQgIgDgIADQgCAAgDACQgMALAAgIQAKgDgDgHQAAgFgFgDIgRgCIAAAFIgFAAIgDAAIgCACIgIAIIgKACIgDgHIgCgDQAFgCgDgIIAAgFIADgCIACgFIAAgDIADgPQACgFgCgFQgFgIAAgFIgSgCIgCACIgFAIIgDACQABABAAAAQABABAAAAQAAAAgBABQAAAAgBAAQgCgIgIAAQAAgHgFgDIgDAAIgCgCIgFgDQAAgMgKgGQgDgCgCACIgIAAIgMABIgDAAQgKAFgDAKQAAAFgFACIgCAIQAAgFgIAAQgFACADgFIAAgFIgFAFIAAgKIgDAAQAAAAgBAAQAAABAAAAQgBAAAAABQAAAAAAABQgDACAAAFIgCgKQAAgCgFAAIAAAKIAAACQgDAAgDgFIgCAAQgIAAgCAIQgDACAAAFQgCgCgDAAIgCAAIgFgDQACgHgHgDIgDgCIgHgDIgDgFQAPADADgFIgDgDQgHgLARgKQgZgKgFgRQgFgQgDgPQgCgRAFgKQAFgLANAAQAPADAHgNQAIgKAFAKIACADQAIgFADAFIAMASQADAAACACIgCgFQAAgIAHAAIAIAAQAHADADAFQAKAIAFgFIgCgFQgDgGADgCQACgIAFgCIAFgDIgMgjIAHADQAIACAAAFIAAgCQAAgBAAAAQAAgBABAAQAAAAAAgBQABAAAAAAIADgNQACgFAFgCIAAgDIADgCIAAgDIACAAIAAgCQAAgFADAAIAKAAIAFAFIAAgDQABAAABgBQAAAAABAAQABAAAAAAQABABAAAAIADADQAHACAAAIIADgFIAAgDQAKgPAPAAIACAAIADASIAAACIAFAAIADAIIAAACIAFgCQAFAAgDAFIgCAPIAKAAIAMAAIADAAQAFAIAAAHIgDASIgCAFQgFAFgIgDIgCAAQgDADAAAFIAFAAQAFgCgCAFIAAAFIACgDIAFAAQAFgCAAAFIADAFIAFgDIACgFIAIgFQAFgCAIACIAFAAQACADgCAFQgFAHgKAAIAAADIgDAHQAAAFADADIAAgFQACgDAIADIACAAIAFAFIAIACIACADQABAAAAABQABABAAAAQAAABgBAAQAAABgBABIAAAIIAFAAIAIACIAKAIIADACQgDAFADAAIACAAQADAAACADIAAAFQADgFAHAAIADgIQAAgFACAAIAFAAIADADIAAACQAAADADgDIASADIACACQAFAKgHAIIAAAFIgFAKIACgDQAFAAAAAGIgCADQACgBAFAAIADABQACAFgCAFIAAAFIgDAHIAIADIACACQAAAFAFgFIADgCIgFgIQgDgCADgFIACgDIgCgCIAAgDIAHgCQgCAAAAgDIgDgDIgCgDQAAgKAHADIAAgDIADgFIACAAIAAgCIgCgDIAAgHIACgKIAAgFIADgIIACgFIAKgKIADgDIAKAAIAIAFIACADQAFACgCAIIAHADIADAAIACACIADgKQAFgFAHAAIADAAIADAFIAAgDIACgKQADAAACADQADACgDAFIAFgFIAAgCIADAAIACAKQAAgDAFgCIAAAFIAAACQAIAAACAIQAAgFADgFIAFgIQACgKAIgFIAFAAQAFgFAFAAQAFADADgDQAFAAACADQAIAHAAANIAHACIADAAIACADQAFACAAAIQAIAAADAIIAAgFIAFgIIACgDIASADIACAAIADANIACAHQgCAIAAAHIAAADIgDACIgCAFIgDADIADACIAAADQACAGgHAAIACACIAAADIADAHIACAAQADgCAFAAIACAAIAFgIIAGgCIAFAAIAAgDIACAAIASADQAFAAAAAFQAAAHgIADQgCAHAPgMIAFgDIANADIAFAFQACAKgKAHIgDAIIALAAIAMgDQgCASgPAKQAKAIACAHQADADgDACQgFAPgNAGQAGAHAHADIAIACIAKAKIgZADIAbAWIgjALQAcARAFAXg");
	this.shape_37.setTransform(413.5,352.1);

	this.shape_38 = new cjs.Shape();
	this.shape_38.graphics.f("#002000").s().p("ABhDXIgDgFQAAgDgHAAIACgHIADgGIgDgKIgCgCIgFACIAAgFQACgFgHADIAAgDIACgHIADgFQAFgKgFgIIgDAAQgIgFgHADIgFgDIgDgDIgCgCIgFAAQgDAAAAAFIAAAIIgCAAQgFAAgFAFIAAgFQAAgDgFAAIgDAAIAAgCQADgDgDgDIgFgFIgIgCIgFgDIgFAAQgCgCACgDIAAgHQgCgDgBAAIgHgCIgDgFIgFAAIgCgDQgFADAAACQgBAAAAABQgBAAAAAAQAAAAgBABQAAAAAAABIAAgIIAAgHIADAAQACgDAFAAIAFgIQAFgFgFgCIgCAAQgIgDgHADIgGAFIgCACIgDADIgCADIgDgDQgCgFgFAAIgDAAQAAAAgBAAQAAAAgBABQAAAAAAAAQAAABAAAAIAAgCQAAgIgFAAIgDADIgCAAQAAgFACgDIADAAQAKADACgFIADgFIAFgSQAAgHgFgGIgDgCIgMAAIgDAAIgJAAIACgDIACgKIAAgCQAAgFgCAAIgDAAIgCACIgDAAIAAgCQABgBAAgBQAAgBAAgBQAAAAAAgBQAAAAgBAAQgCgDgDAAIAAAAIgDgSIgCAAQgSAAgHANIgDAFIAAADQgCgGgFgCIgDgDQgCgCgDACIgCADIgDAAQADgDgDgCIgKAAQgDAAgCAFIAAACIgDADIAAADQgFACgCAFIgFANIAAAFIgIgIQgCgCgDAAQgCAAgDACIAAgCQAAgIgHgCQgGAAgFACQgFADADACIAAAFIgDADIAAAFIgCgDIgDgCIgCgDIgDgCIgHgDQgIgHgHAFIgDAAIgCACIgGAFIAAgCIgCgFQgDgKAAgIQgCgIAAgKIACgFIAAgFQAFgKAQACIACAAQAKAAAFgFIADgFQAHgKAFAIQADAFACgDQAGgCACACIADADIAMAPIADACIAAgFQAAgHAHAAIAIACIAKAFQAHAIAIgDQgCgCAAgFIgDgIQADgFAFgCIAFgFIgIgXIgFgKIgFgNIgDgHQgFgSANgIIAFAAQAPAFAKALIgCgFQACgLAKAFQANgKAIANIAFgDQAKgKAMgCQgFgNAAgPQAAgjAPACQANADAXAZQAKgjAIAUQAIAXgIAMIACgCQANAHAFASQAAgFAFgDQANgMAHARIAAgHIADgDQAFgKAKAFQgNgUgFgUIAAgFIAAgFQAAgDAFgFQANgMAKARQgCgHAKAAQACgDADADIAFACIAMAPIADgFIAFgCIACAAQANgDAFAKIADADIgDAXIAIgFIAFAAQACgDADADQAFACAAAIQACgFADACIANADIAHgDIADgCIACAAIADACIAAAFIgDAKIADAAQAFgCACACIAAADIADAFIgDAKIgCACIgFAGIgKACIAAAKIgDAFQgFAKgKAAQAHABAAAEIgCAKIAAAFQgFAFgDAIIADAAQAAAAAAgBQAAAAAAAAQABgBAAAAQABAAAAAAIAIgGQAAAAAAgBQABAAAAAAQAAgBABAAQAAAAABAAQAKgFAFAHIAAADQAFgDACADIAFADIAFAFIADgDIAAgIIACgFIAKgCQAGAAAAACIACAIIAAADIADgDIAMAjIgFADQgFACgCAIQgDACADAGIACAFQgFAFgKgIQgDgFgHgDIgIAAQgHAAAAAIIACAFQgCgCgDAAIgMgSQgDgFgIAFIgCgDQgFgKgIAKQgHANgPgDQgNAAgFALQgFAKACAPQADAPAFAQQAFARAZAKQgRAKAHANIADADQgDAFgPgDIADAFIgIAAIgDAAIgKANQAAAAgBAAQAAAAgBABQAAAAAAAAQAAABAAAAQgDADAAAFIgCAFIgDAKQAAAFADADIAAAFIgDAAIAAAHIgCAAQgIAAADAKIAAADIACAAQAAAFAFAAIgHAFQgBAAAAAAQgBAAAAABQAAAAABAAQAAABABAAIAAADIACACIgFADQAAABgBABQAAAAAAABQAAABAAAAQABABAAAAIAFAFIgCAFQgBABAAABQAAABgBAAQAAAAgBABQAAAAgBAAIgBgBg");
	this.shape_38.setTransform(364.2,342.7);

	this.shape_39 = new cjs.Shape();
	this.shape_39.graphics.f("#11430E").s().p("ACJE0IgFgEIANAAIgGAEgAouEwIAAgDIAAgHIAAgDQAGgFAFgCIgDgDQgCgFAFgCQACgDADAAQgDgCADgDQAAgBAAAAQAAgBABAAQAAAAAAAAQABgBAAAAIgMgPIgCgFIgIgIQgGgPgCgRIACgGIAIgEQANgIAMACIADADQACgCgFgGIgCgEQgCgFAEgDIAGgCIgIgKIAIgIQgQgDgJgPIgEgKQADgDADAAQADgCAFAAQACADAHACQgEgFAEgCQAFgDgCgFIgDgCQgEAAgDgLQAPADAAgGIAAgCQgKgKASgKQgXgNgFgPIgDgHQAGAAAAgGIAEgCQAIgDAFAFIACADQADgCADAFIAEAAIADAEIADADIAAgDIACgHQAAgFACgCQADgDAIADQAIACgDAIQAFgDAFADIAIAHIAAgFIAFgKQACgIACAAIAAgCQAAgBABAAQAAgBAAAAQAAAAABAAQAAAAABAAIAAgDQAAgBAAAAQAAgBABAAQAAAAAAAAQABgBABAAIAAgCQAAgFACAAIAKADQABAAAAAAQABAAAAABQAAAAgBABQAAABgBABIADgCIADgCQAAgBABAAQAAgBABAAQABAAAAABQABAAAAABIAAACQAIAFAAAFIACgFIAAgDQAIgNAPAAIACAAIADAQIAAADIAGACQACAAAAAFIgCADIACAAIADgDIACAAIACAFIAAACQgCAGAAAHIAIAAIAMAAIACACQAGAGAAAHIgDASIgDAFQgEAFgIgDIgCAAQgDADAAAFIAFgCQAFAAAAAHIAFAAQAFAAAAAFIADAGIAEgGIAAgCIADgDIAFgFQAGgDAHADIACAAQAFADgCAFQgFAHgHABIgDACIAAAIQgDAEADADQAAgBAAAAQAAgBAAAAQABAAAAAAQABgBABAAQACgFAFADIACAAQAFADgCACIADAAIAEACIAGAAIAAAIQgDAFADAAIACAAIAIADIAKAHQACAAgCAFIACADQAFAAABADIACACIAAACQADgEAFAAIACAAIAAgLQAAgBAAAAQAAgBABAAQAAAAAAAAQABgBAAAAIAFAAIADADIAAADQAAAAAAABQABAAAAAAQABAAABAAQABgBABAAIAPACIADADQAEAIgEAJIgDAGIgDAHQAIAAgCAFIAAAFQAAgCAEAAIADAAQADAHgDAFIAAAGIgDAHIAIADIADACQAAAFADgFIACAAIAAgFIgFgFIADgIIACgCIgCgDIAAgDQAEgCADAAQgFgDAAgCIAAgDIgCgCQgDgKAHAAQABABABAAQAAAAAAAAQABAAAAAAQAAAAAAgBIAAgFIADAAIAAgFIgDgHIADgLIAAgHIAEgGIAAgEIALgNIACAAIAIAAQAFAAACAFIADACQAFADgDAFIAAACIAIAAIACAAIADADQAAgFADgCQACgIAIAAIAEAFIADgMIACACIADAKQAAgFADgDIACgCIACAAIAAAKQABgDAFgCIAAAFQgDAFAGAAQAEAAADAFIACgKIADgIIAKgOIAFAAIAKgDIAIAAQACgCADACQAHAIAAAMIAIACIADADQAEADAAAEIAAADQAKAAAAAHIAAgCIADgCQAAgDAFgFQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAAAAAIAQACQAAAFAFAFQACAFgCAFIAAAQIgDACIAAACIgCAGIgCACIAAACIACADQAAAIgGAAIAAACIAEAAIAAACIACAIIACAAQADgCAFAAIADgIQACgCADAAIADAAIACgDIAAgDIACAAIAQADIAEAFQABAKgIADQAAAHANgMQAAgDAFAAIANAAIACAIQADAKgIAHQgDADAAAFIAIgCIANAAQgDARgMAKIAMANIAAAHQgIAPgKAFIADAGgACqCEIgDgCQgCgFgFACIADgKIAAgFQACgFgCgFIgDAAQgCgCgDACIAAgDQADgHgIACIgCAAQAEgCABgIIACgFQAFgHgFgKIgCgDQgGgCgKACQgFAAAAgDIAAgCIgDgCIgFAAQgCAAAAAEIAAAIIgCAAQgFAAgDAFIAAgCIgCgDIgDgCIgDAAQAAAAAAgBQgBAAAAAAQAAAAABgBQAAAAAAgBIgCgFQgCgFgIAAIgIgGQAAABAAABQAAAAgBAAQAAAAAAAAQgBgBAAgBIAAgFQACgFgCgCQgBgDgFAAIgEgCIgDAAQABAAAAAAQAAAAAAgBQAAAAAAAAQAAgBgBAAIgCAAIgDgDQgFgDgCAGIgDACIAAgIIAAgHIADAAIAMgKQACgBgCgCQgDgDgCAAIgKAAQAAgFADAAIAEAAQAGgDAFAGIgDgFIAAgFQgCgSAJgMQAAgBABgBQAAAAAAAAQAAgBABAAQAAAAABAAIAKANIADAAIACgDQADgDADAGIAEgDIAAgDQADgCADACIAAADIAEANIAIgKIAHgKIADAAQAHgDADAHIAIAQIADAEQAAAIgIAFIgDAAQAAABAAAAQAAABABAAQAAABABAAQAAAAABAAIAAAHIADgFQAEgFADAFIAAADIACgDIAAgCQAFgIADADIAFACIAAgHIAAgGIADgKIAEgKIAGgCQAEgDAAAIQAAAIgEAHIAAACIACAIIACAAIAAADQAAAKADAGQAAAJADALIACAEQgFAFAAADIgDAFQgCACAAAGIgCAFIgDAKQAAAFADACIAAAGIgDAEIgDADQgEgCAAAJIACADIAAADIAFAFIgHADIAAACIACACIgCAGQgBAAAAABQgBAAAAABQAAABABAAQAAABABAAIACAIQABABAAAAQAAABAAAAQAAAAAAAAQAAABgBAAIAAACIgEABQAAAAAAAAQgBAAAAAAQAAAAAAAAQAAgBAAAAgAErgZIgKgFIgIgDQgHAAgBAIIAAAFIgCgDIgNgOIgCgDIACgDIAAgCIgCgFQgDgDADgCIACgFQAAgBABAAQAAAAAAAAQAAAAABAAQAAAAABABIADAAIACgDQAFgHAIgFIACAAQAIACACANIACAFIAAAFIAAADIADgDQACgFADAFIACADIADgGIADAAQAFAAADAGIAAAEQgFADgDAFIACAIQABAEACADIgEABQgGAAgFgGgAE3hHQgFgFADgFIAFAKgAEthgIgFgKIgDgEIgCgIIAAgGIAAgOQAAgBAAgBQAAAAABAAQAAgBAAAAQABAAAAAAIAGgIIAHgEIADAAQAFgDADAFIAAACIAEgEIADgDIADAAIACADIAAgDIgCgDIgDgEQAAgLADgFIACgCIAFAAIgFgKQABgBAAAAQABgBABAAQAAAAABABQAAAAABABIAFAEIgCgHIAAgDIACgCQAFAAAAAFIACgHIAAACQADAFADgDQAFgHACADIgFgIIAAgHQgFgLADgKIACgFIAFgKIAFgIIAGgEQAJgBAIAIIAFgDIACgCIADAAQAFgCADAEQAHgHAGADIgDgDIAAgKIAAgDQAFgKAFgFIACAAQAFAFAFAAIAGAFIAHANIAAACIADADIAAAFIAAACIACADIACADQAGACgGAIIAGAAIAFACIAAgCIAFgIIACgCQgCgDAAgHIACgGIADgCIAAgIIAMgNIAGADQAHAFgFAIQAFAFAAgSIADgHIAKgKIAEAAQAIAHAAAKIAAAIQAAAFAIgNIAHgKQAIAKgCAUIAOgCIADAFQAFARgFANIAFAAIgSAmIgEgCQgDgDgDADQgKgBADAIQgKgRgNAMQgFAFAAADIAAAEIAAAGQAFAUANAUQgKgFgGAKIgCACIAAAIQgIgSgMANQgFADAAAEQgFgRgNgIIgCADQAIgNgIgWQgIgVgMAkQgXgagNgCQgPgCAAAjQAAAPAFAMQgNADgKAKIgEADQgIgNgNAKQgKgFgCAKIACAGQgLgLgOgFIgFAAQgNAHAFATIACAHg");
	this.shape_39.setTransform(319.6,341.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_39},{t:this.shape_38},{t:this.shape_37},{t:this.shape_36},{t:this.shape_35},{t:this.shape_34},{t:this.shape_33},{t:this.shape_32},{t:this.shape_31}]}).wait(1));

	// Layer 23
	this.shape_40 = new cjs.Shape();
	this.shape_40.graphics.f("#42950B").s().p("AAtCsQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIAAACQAAAAAAgBQAAAAAAAAQgBAAAAAAQgBABAAAAgAgsirIAAACIgCAEg");
	this.shape_40.setTransform(68.1,221.8);

	this.shape_41 = new cjs.Shape();
	this.shape_41.graphics.f("#336600").s().p("ACsLnQhJAyhHhkQoNkZDHmOIAAgIQAUANAZgIQAFAAAAgFIADghQAcAKAUgPQgFgNgIgHIgKgQQADgCAFAAQAUACAIgPQAFgHgDgDQgCgMgLgNQgFgFgCAAQgcgKAFgFQAPAKAFgNQAFgCgCgKQAAgIgPgZIgDgDIgCADQgDACgCgFIgFgFIgFgFQgIgCgIACIAAgCIgMgNIAAgCIAFgLIAAgFIAAgFQAHAKAFgKIAGgFIACgFIAFAAIAIAAIACAAIADgCIAUgPQAHgDADgKQADgNAFgHQAAAAABAAQAAgBAAAAQAAAAAAgBQgBAAAAgBQgFgRgLgKIgFgDQgKgDgFADQAAAAgBAAQAAAAAAABQAAAAgBAAQAAAAAAABQACgLgKgOIADgCQAHgFgCgNIgDgFIAAgCIgFgQIADAAQAMgPAAgWQAAgIgFgFIgHgNQgIgKgKgFIgFgFQgNgHgPAFIgMACQgIgCgFAHQAFgHgIgNQgFgFAIgFIACgDQgFgFgEADQAJgFAAgIIgCgFIgDAAIgMADQAHgFADgIQACgFgFgFIgPAKIAAgKIgCgIQgIgKgKADQgHAAgGAFIAAgCIAAgBIgCgHIgFgNIAAgCQAKgIgFgNIAAgFQACgKgFgFIAAgCIADgFQANAUAFAAIACgFQAFgcAeASQgMg4AMgZQANgZAPgXQASgXAPgFQASgFAMAaQAPAZASAAQASgDgDAUQAAAIADADQAKAHgDAKIgFApIADAHQAAgHAFgDQAHgFAIAKIAIAPIACAXQAAAXANAHIACgKQADgKAFgDQAKgCAKAIIAIACIAWg8QAIAFACAMQADAKgFAKIACgCIAFgDIASgFQAKgFAIAIIAAgDIAFAAIAAgCIAFAAIADAAQAFgFAFAHQAFAFADAKQACAFgFAFIADAAIAFAAQAHAAgCAIIAAAIQACAPgHAHIAHgCIAFgDQAcgCAPAcIADAHQgFANgNAHIAAADIACAMQAAAFgHADIAAADIAFAHIACADQAGAFgIAFIgUAKIAKAPIAMAZIADAFQgDANgHAKIgZAPIgIAAQgKAAgFgSIgDgCQgFgDgFAFIAFAIQAFAIgFAFIgFACIAFADIADAFQAHAHgFAKIgCAIIAKACIAFAAIADAAQAHAAAFAFIAPAVIADAKQAAAHgIAAQgMAAgKgMQgBgBAAAAQgBgBAAAAQgBAAAAABQAAAAAAABIgKAFQgBAAAAAAQgBAAAAAAQgBABAAAAQAAABAAAAQgFADAAAFQADgDACADQAIgDADANIACAIIAAACIgCAKIACADIAFAMQADAFgDAFQAAAFgFAAQgIADAAAFIADAFIAAADIAFAPQAFAMAAANQAAAFgFADQgFAAADAFIACACIADANIgDACIgCAFQAKgCAHAMIADAAIAHgHQADgFAFAHIAHAFQADAFgDADIgCAFQgDADAFAHQANANAFARIgCAFQgDASgSADQgFAAgCACQgIADgHgDIAFAFQAHAKgHADIgFACIAFAKIACAFQgCALgIAEIgHAEIgNACQAIAKAAAIIgDAHQgDAFAIAAIADAAIAFADIAAgDIACgPQADgHAFAAIAHACIAAgCIAAgFQADgFACACIAKAKQgCgHACgFIAAgGIADgFQAKgNAFANIADADIAKgDIACADQAAgBAAAAQAAgBABAAQAAAAABgBQAAAAABAAIAAgFQACgIAFgCIAPgKIAGgDIAMgFIAIAAQAMgCAKAHIAFADQAGAFACAKQAFAHgCAKIAAAEQACAMgHAFIACAPIADADQACAIgFACIADAAIACgCQAFgGAIAAQAKAAAHALIADAFIAAAKQAHgFAIgDIADAIQgDAHgIAFIANgCIADAAIACAFIgJAPQAEgFAFAIQAAAAgBAAQAAAAgBAAQAAABAAAAQAAABAAAAQgIADADAHQAKAKgFAKQAFgHAHACIAKgCQASgFANAHIAFADQAKAHAFAKIAKANQAFAFAAAIQAAAUgPAPIAAADIACAMIADAFIAAAFQAFANgKAFIAAACQAHANgFANIADgDIAFAAIAMgCIAGACQAMANAFARQABAAAAAAQAAABAAAAQAAAAAAABQAAAAgBABQgHAIgDAMIgHANQgLAFgKAKIgCAAQgDAAgCACIgFAAQgDgCgFACIAAADIgFAFQgFAKgIgHIAAAFIAAAFIgHAMQAAAAABgBQAAAAAAAAQABAAAAAAQAAABAAAAQAIADADAKIACACQAIgCAHACIAFAFQADAAAAAFQAFAGAAgDIAFgDIACADQAQAZAAAIIgDAMQgIAKgMgKQgIAIAZAIIALAHQAKAKACANQADAFgFAHQgKAQgSgDIgKADIAMAPQAIAHAFANQgXANgZgIIgCAhIgIAHQgXAFgRgMQgFAPAHAUIADAKIACAhIAPAFQALBZgnAAQgaAAgxgpgAk2n9IAAAAIAAAAg");
	this.shape_41.setTransform(99.6,260.6);

	this.shape_42 = new cjs.Shape();
	this.shape_42.graphics.f("#295C07").s().p("AgBgBIADABIAAACg");
	this.shape_42.setTransform(48.7,195.2);

	this.shape_43 = new cjs.Shape();
	this.shape_43.graphics.lf(["#CCFF00","#C7D013"],[0,1],70,25.5,10.6,87.3).s().p("AAAABIABgBIAAABg");
	this.shape_43.setTransform(46,160);

	this.shape_44 = new cjs.Shape();
	this.shape_44.graphics.f("#11430E").s().p("AEeKrIAAAIIqvxIIAAgCIAHgLQABAAAAgBQABAAABAAQAAAAABAAQAAABABAAIAUALQADACgDgNQADgHAIAFQAHACAAAFQADgKAFAAQACAAAFAFQgCgUACgRIADgQQgDgKAFgHIAagzQAFgFAFAAIAMAIQAXAMAKAcIADAIQAHAAgCgNIACgKQADgNAKALIAHAFIADgaQAHgCALAKQgQghAIgjQACgNAGAAIAKADQAFACACAKIAIAXQACgPAFAFQAKAIADgLQACgFgCgCQgDgKAFgKQAQAWAFAAIAAgFQAFgbAeARQgNg6ANgXIAFgHIAKACIAIADQAPAKgDASIAAAFQAFAAAAAMIAAAIQADAFgDADIADAMIACgCQAIgDACgFQAGgFAFADQAHAFAFAKQAIARgNAIQAKAFAAAPIADASQAAgBAAAAQAAgBAAAAQABAAAAgBQAAAAABAAIAQgFQAKgFAIAFIAFgCIAFAAIACAAIAFgDQAFgCADAFIAHAPQAFAFgHAFIACAAIAFACQAIgCgCAHQAAAGACACQAAASgIAHIAIgCIAFgFQAcAAARAZQADAFgDACQgFANgMAKIAAADIACAKQAAAFgHAFIAAACIAFAIIACAAQAFAIgHACIgUAKQAHAIADAKIAPAXIAAAFQAAAPgKAHIgXAPIgCAAIgIADQgIgDgHgRIgDgDIADAAIAAgCIgDACQgFgCgFAFIAFAHQAFAIgFAFIgCAFIAFADIACACQAIAKgDAIIgCAKIAHAAIAIACQAIAAAFAFQAKAFAFANIACAKQAAAIgHACQgNAAgKgPIgDAAIgKAFIgCADQgFACAAAFIAFAAQAHAAADAKIACAIIADACQAAAIgFADIACACIAGANQACAFgCAFQAAAFgGACQgHADAAACQAAAFACAAIADADQAAAKACAIQAGAKAAAMIgDAIQgFACAAADIACACIADANIAAADIgFAFQAKgDAIANIAAACIAKgKQAFgCACAFIAFAHQADAFgDADIAAACQgCAFAFAFQAMAQADAPIAAAFQgFAPgSADQgCAAgFADIgKACIgFgCIAFAFQAHAKgHAFIgFACQACAAADAIIACAFQgCAKgIAFIgFAFQgIAAgHAFQAKAIgDAKIgCAFQgDAFAIAAIACACIAGAAIAAgCIACgPQADgIAFAAQAFAAACAFIAAgFIAAgCQADgIACAFIAKAIQgCgIACgFIADgFIAAgIQAKgMAHAMIAAADIALgDIACADIADgDIACgFQAAgFAFgFQAIgFAHgCIAIgFIAKgDIAIgCQAMAAAKAFQADAAACACIAIANIAAACQAFAFgDAKIAAAFQAFANgKAIIAAACIAFANIADAHIAAABIgCAGIACgEQAFgFAIAAQAKgDAHAKIADAIIAAAKIAPgKQAFAFgDAFQgCAIgIAFIANgDIACAAIADAFQAAAIgKAFIAAAAIAAAAQAFgDAFAFIgDADQgHAFAFAFQAIANgGAHQAGgHAHACIANgCQAPgFAMAHIAGAFQAKAFAHAKIAIANQAFAFAAAIQAAAWgNAPIgCAAIAFAQIAAACIACAFQADANgIAFIgCACQAJAOgBALQAAgBAAAAQAAAAAAAAQABAAAAgBQAAAAABAAQAFgDAKADIAFADQAKAKAFARQABABAAAAQAAABAAAAQAAABAAAAQAAAAgBAAQgFAHgDANQgCAKgIADIgUAPIgCACIgDAAIgHAAIgFAAIgDAFIgFAFQgFAKgIgKIAAAFIAAAFIgFALQgBAAAAAAQAAAAgBAAQAAABAAAAQAAABAAAAQAAAAABgBQAAAAAAAAQABAAAAAAQAAABAAAAIANANIAAACQAIgCAHACIAFAFIAFAFQADAFACgCIADgDIACADQAPAZAAAIQADAKgFACQgFANgPgKQgFAFAbAKQADAAAFAFQAKANADAMQACADgFAHQgIAPgUgCQgFAAgCACIAKAQQAHAHAFANQgUAPgbgKIgDAjQAAAFgFAAQgKADgIAAQgPAAgMgIg");
	this.shape_44.setTransform(41,192.9);

	this.shape_45 = new cjs.Shape();
	this.shape_45.graphics.f("#003300").s().p("Ai7GzIgDgCQgHAAACgFIADgFQACgKgKgIQAIgFAHAAIAGgFQAHgFADgKIgDgFQgCgIgDAAIAFgCQAIgFgIgKIAAgDIAKgCQAFgDADAAQARgDAFgRIAAgFQgCgPgNgQQgFgFADgFIAAgCQACgDgCgFIgFgHQgDgFgFACIgKAKIAAgCQgHgNgLADIAFgFIAAgDIgCgNIgDgCQAAgDAFgCIADgIQAAgMgFgKQgDgIAAgKIgCgDQgDAAAAgFQAAgCAIgDQAFgCAAgFQADgFgDgFIgFgNIgDgCQAFgDAAgIIgCgCIgDgIQgCgKgIAAIgFAAQAAgFAFgCIADgDIAKgFIACAAQALAPAMAAQAIgCAAgIIgDgKQgFgNgKgFQgFgFgIAAIgHgCIgIAAIADgKQACgIgHgKIgDgCIgFgDIADgFQAFgFgFgIIgFgGQAFgEAFACIACACQAIAQAHADIAIgDIADAAIAWgPQAKgFAAgPIAAgFIgPgXQgCgKgIgIIAUgKQAIgCgFgIIgDAAIgFgIIAAgCQAIgFAAgFIgDgKIAAgDQANgKAFgNQADgCgDgFQgSgZgbAAIgFAFIgIACQAIgHAAgSQgDgCAAgGQADgHgIACIgFgCIgDAAQAIgFgFgFIgIgPQgCgFgFACIgFADIgDAAIgFAAIgFACQgHgFgLAFIgRAFQgBAAAAAAQgBAAAAABQgBAAAAABQAAAAAAABIgCgSQAAgPgKgFQAMgIgHgRQgFgKgIgFQgFgDgFAFQgDAFgHADIgDACIgCgMQACgDgCgFIAAgIQAAgMgFAAIAAgFQACgSgPgKIgHgDIgKgCIAFgKQAHgSAKgMQASgXAPgFQASgFAMAZQANASANAFIAHACQAUgCgFAUQAAAHADADQAKAFgDAKIgFArIADAIQAAgIAFgDQAHgHAIANIAIAPQAFAKgDAMQAAAXANAIIACgKQADgLAHgCQAIgDAKAIIAIADQARgzAXgtQANgcAUAKQAFACAAAGQAMAdAAAdQAAgGADgFQANgKAHAcQAVAIgKAbIAMADQAVAKARASQAKgZAPgSQAmgpANAcQANAXgGBCQA1gVgPAmQgRAmgXgCQAFACAAAFQAFAcgNAZQAFgCAGACQAeAIgKAjQACgIADAAQACgCADAAQARgFAGAZQAHgrASgeIAFgIIAKgFQACgCALACQAbAKgKAfQAFgIANANIAFAKQACAZgKAZIAKAAIAIACIAFAEQASASgFAUIgFAHQgLANgPAKIANAIQACAAAGAFIACAHQADANgIAHQAIAAACAIIANAZIAHAIIAFACIAFAFQADADgDAFIgHACIgNAIIADACQAFAIAAAIIgDAFIgCAFQgFAHgKAAQgBABgBAAQAAAAgBAAQAAAAgBAAQgBAAAAgBIgLgCQgHgFgFgKIAAACQgIAFgFAFIgHADQgPACgLgPQAFAKgFAFIgMAKIgIADQgKAAgKAHIAIAAQAHAGAFAAIAIAFQAMAKAAARIAAAFQAFAAAAANIAAAIQADAFgDACIAAANQADgDADAAIAKgHQACgIAIAFQAHAFAFAKIADANQAAAFgFAFIgDAAIAAADIgWA8IgIgCQgKgIgKACQgFADgDAKIgCAKQgNgHAAgXIgCgXIgIgPQgIgKgHAFQgFADAAAHIgDgHIAFgpQADgKgKgHQgDgDAAgIQADgUgSADQgSAAgPgZQgMgagSAFQgPAFgSAXQgPAXgNAZQgMAZAMA4QgcgSgFAcIgCAFQgFAAgNgUIgDAFIgHgNQgDgCgCAAQgKgFgNAAIgHACIgLADIgHAFQgIACgHAFQgFAFAAAFIgDAFIgCADIgDgDIgKADIAAgDQgIgMgKAMIAAAIIgCAFQgDAFADAIIgKgIQgDgFgCAIIAAACIAAAFQgDgFgFAAQgFAAgCAIIgDAPIAAACg");
	this.shape_45.setTransform(66.3,160.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_45},{t:this.shape_44},{t:this.shape_43},{t:this.shape_42},{t:this.shape_41},{t:this.shape_40}]}).wait(1));

	// Layer 22
	this.shape_46 = new cjs.Shape();
	this.shape_46.graphics.f("rgba(0,0,0,0.302)").s().p("APiCuQgFgDgGADIgHAAIgcACIgKgCIgCAAIAAgFIgFgFQgLgFgHgLQgIgKgCgKIAAgMI8NAAIAAAMIgKAUIgSAQIgHAFIAAAFIgDAAIgHACIgfgCIgHAAQgDgDgFADIgeAFIgPgFQgKgFgDgIIgFgFIAAgKIADgKIACgKIAIgSIACAAQAAgFADgCIAAgFIgDgDIgCgFQgIgDAAgFQAAgFAIgCIAMAAIgRgpIgFgHIgDgFIAAgKIAAgLIAIgKIACgFIAFgFIADAAQACgDAFAAQAFAAAFAFICQhGIA/ghQAIgIAFAAIgDgjIAAgFIAAgIIAFAIQASAPAmANQADAAACACIAjAAIA6ADIBoAHIElAIIAFgFIAAAFIHnAAIAAgFIADAFIEngIIBlgHIA8gDIAkAAIACgCQApgNARgPIAFgIIAAAIIgCAFIAAAjQAFAAAHAIIBAAhICNBGQAHgFAFAAQAFAAADADIADAAIAFAFIACAFQAFAFAAAFIADALIgDAKIAAAFIgFAHIgCAAIgSApIAPAAQAIACAAAFQAAAFgIADIgFAFIAAAIIADAHIAHASIAFAKIAAAKQADAFgDAFIgCAFQgDAIgKAFIgSAFgAPkCkIADAAIgDgDgAvmCkIADAAIAAgDQgBAAAAAAQgBABAAAAQAAAAgBABQAAAAAAABgAPnChIACgCIgCAAgAvoChIACgCIgCAAgAO5B7QAFAFAFAAQAIAAAFgFIACgCQAFAAADgFIgoAAgAvUB5IACACQAIAFAHAAQAGAAAFgFIAHgHIgoAAgAF/A6IAIgDIgNAAgAl+A6IAFgDIgNAAgACzAZIAAgDIAAgFIgDgFgAiyARIAAAFIAAADIADgNgAtjgLIADAAIAAgDgAgEhwIgCADIAGAAIAEAAIAAgDIgEgCg");
	this.shape_46.setTransform(214,142.2);

	this.timeline.addTween(cjs.Tween.get(this.shape_46).wait(1));

	// Layer 21
	this.shape_47 = new cjs.Shape();
	this.shape_47.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("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");
	this.shape_47.setTransform(161.2,139.2);

	this.shape_48 = new cjs.Shape();
	this.shape_48.graphics.f("#F1CDB5").s().p("Ag3AqIABgBQAAgBAAAAQABgBAAAAQAAAAABAAQAAAAABAAIAMgIIAQgKIACgCIAagSIAjgdIAFgCIgUARIgUAOIAUgOIAUgRIASgPIAAgDIACAAQAFAAgHAKQgFALg4AkQg1AlgPACg");
	this.shape_48.setTransform(142.8,135.5);

	this.shape_49 = new cjs.Shape();
	this.shape_49.graphics.f("#E1B8AA").s().p("Aj5AAQgJgCAvAAIAPAAIARAAIAKAAIBHAAIApAAICkAAIByAAQA8ACg6AAQhbAEi2AAQi4AAgPgEg");
	this.shape_49.setTransform(145.7,151.4);

	this.shape_50 = new cjs.Shape();
	this.shape_50.graphics.f("#B27F6D").s().p("AhNApIgHgFIgIgFQgFgFAAgFQgCgFAAgIIAAgHIACgDQAAgFAFgFQAIgKAKAAIACAAQAKgDAIAKIADAFQAFAFAAAGIAAAFIAAACIgDANIgFAKIgPAKgAiYAiIgDgDIgIgKIAAgNIAAgHIAAgBIAIgMQAFgIAFgCIANAAQAHAAAFAHQAFADAAAFIADAIIACAFIAAACIgFANQAAAFgFAFQgHAKgKAAQgKAAgFgHgAkPApIgKAAIgKgHQgFgFAAgIIgDgIIAAgCIADgKIAAgBIAFgMIACAAIADgDIAFgCIACgDIADAAIAAAAQABAAAAgBQABAAAAAAQABAAABAAQAAABABAAQAKgCAIAKIACACIADAFIACAGIAAAHIgCANIAAACIgDAGIgCACIgDAFIgKAFgAjdAfIgFgKQgCgFAAgIIACgKIAFgNIADgCIAIgDIAAgCIAKgDIAFADIAFAAIAHAHIAFAFIADALIAAAHIAMAAIAAANIgPAAIADgNIgDANIgFAIQgHAKgKAAIgFAAQgJAAgHgIgAFwAnQgIgDgFgHIgFgIIgCgNIACgKIADgKIACgDQAIgKAKAAIACAAQAIAAAIAFIACADIAIASIAAAHIgDANIgFAIQgFAHgKADgADWAnQgIgDgFgFQgFgFAAgFQgFgFADgIIAAgIIAHgPQAIgKAKAAIAFAAQAIADAFAFIACACIAIAQIAAAHIgFANIgDAIIgCAAQgFAHgKADgACIAnQgIgDgFgFIgFgKQgCgFAAgIIAAgHIAFgIIACgFQAIgKAMgDQAIAAAFAFIAFADIAAACQAIAIAAAIIAAAHIgDANIgFAIIgNAKgAAyAdQgFgDAAgFQgDgFAAgIIAAgHQAAgIAIgIIACgCQAGgIAKAAQAHAAAIAFIACADIADAFQAFAFAAAIIAAAFIAAACIgDANIgFAIQgHAKgKAAQgKAAgIgKgAgTAfIgHgKIAAgNIAAgHQAAgIAHgIIAFgCIADgDQAFgFAFAAQAIAAAKAKIADADIAFANIAAAFIAAACIgDANIgFAIQgKAKgIAAQgKAAgIgIgAIPAkQgKAAgIgKIgFgFIAAgDQgCgFAAgFIAAgHIAAgIIAHgKQAFgIAIgCIAFAAIACAAIAAA/gAG+AkQgHAAgIgHIgFgIIgDgNIADgHQAAgGACgFIAGgFQAFgKAKAAIACAAQAIgCAHAHIADADIAHAMIAAAGIAAAHIgCANIgFAIQgFAHgKAAgAEdAdIgFgIIgFgNIAAgHIACgIIADgDIAFgHQAHgKAKAAIAFAAQAIACAHAIIADAFIAAACIADAIIAAAKIgDANIgFAFQgIAKgKAAQgKAAgHgHgAlTAfIgIgFIgHgNIAAgCIAAgKIAAgDIAAgFIAFgKQAAgBAAAAQAAgBAAAAQABAAAAgBQABAAAAAAIADgFIAFgCIACAAIAFgDIADAAQAIAAAFAFIACAFIAFAFIADAIIAAAHIAAABIgDAMIAAADIgCAFIgDACIgCAGIgIACIgIADgAmNAdIgKgIIgFgKIAAgFQgDgDADgDQgDgCADgDIAAgFQAAgFAFgEIAAgBIAAgCIAFgFIAFgDIAAgDIACAAIAFAAQAKAAAIAIIADAFIAAADIACAAIAAAHIADADIAAAFIAAACIgDAIIAAAFIgCADIgDAFIgCACIgIAGIgIACgAnHAaIgKgIQgFgFAAgHIgDgDIAAgFIAAgFIADgFIAAgFIAFgIIAIgIIAEgCIAFAAQAKAAAIAIIADACIAAAFIACAIIAAAHIAAALIgCACIgDAFIAAADIgFAFQgDAFgFAAQgCAAgDADgAn3AXIgIAAIgKgHIgFgIIgCgFIAAgIIAAgMIAHgKIADgGIAFgFIACAAQADgCAFAAQAFAAAFAFIAFAFIACADIAAACIADADIADAFIAAAKIAAAKIgDAAIAAADQgDACAAADIgCACIgDAFIgHAFgAnFgjIgEACQAAgBABAAQAAAAABgBQAAAAABAAQAAAAABAAg");
	this.shape_50.setTransform(161.2,143.8);

	this.shape_51 = new cjs.Shape();
	this.shape_51.graphics.lf(["#CF8972","#F3A898"],[0.047,1],0,7.3,0,-7.2).s().p("AF8BJIAAgIIAAgHIgHgNIgDgCIgbgkIgQgUIgWgpIAPAAQAKAXANAUIAMASQAXAkAcAegAExBJIAAgIIgHgUIgDgCQgMgSgJgSIgKgUIgMgpIASAAIAMApIAKAUQAPAfAUAgIAAADgADoBJIAAgNIgDgHIAAgDIgDgFIgMgjIgFgaQgFgSAAgUIAAgCIANACQACAUAFASQACANAGAKIASA4IACAHIAAADgACXBJIAAgIIgDh5IAVAAIAKCBgABJBJIACgIIAUh5IASAAIgPB3IAAAKgAgBBJIABgDIASgtQAEgFAAgIIAPgkIAIgZIAAgKIAPAAIAAAKIgHAZIgKAkIgGASIgJASIgDAHIgFAKIAAAIgAhFBJIAAgDIAkg3IACgFIARgkIAKgXIAEgKIAKAAIgDAKIgIAXIgUAmIAAADIgVAeIgCADQgHAHAAAKIAAAIgAiOBJQAXgZAWghIAKgPIAUgaIAKgUIAKgPIAOAAIgJAMIgMAXIgUAcIgKANIgXAcIgDACIgEADQgIAHAAAKIAAAIgAlxAmQBJgsAjgcQAhgZADgHIAKgDIAAADIAAACIgSAPIgFADIgkAeIgbAQIgCACIgQAKIgMAIQgBAAgBAAQAAAAAAAAQgBABAAAAQAAABAAAAIgBABIgJAEQgMAFgNAIgAiWAPIAQgQIAWgcIAKgRIAKgPIAIAAIgIAPQgIAHgFAKIgZAcIgOAQIgaAXgAmwAgQA+ghA4gjIAogcIANgCIgtAeQg6AkhAAgIgEADgAGYAFIgOgQQgTgSgOgWIAMAAIAhArIAMANIAPARIAAAIgAiigaIAEgFIALgPIANgPIAKAAIgQAPIgPAPIgFAHIgRANgAjQgiIALgKIAOgUIAKAAIgUASIgMAMIgDADIgHAFQACgFAFgDgAlBhFIAHgDQgKAIgNAFg");
	this.shape_51.setTransform(170.8,137.4);

	this.shape_52 = new cjs.Shape();
	this.shape_52.graphics.lf(["#CF8972","#E7A87E","#FFFFFF"],[0.141,0.651,1],-1.7,18.3,-1.7,-12.4).s().p("AGyAOIgNgSQgNgVgKgWQAAgIADgCIACgDIADAAIAHgFIADAAIAFAAIAIAFIACADIAFAHIADAFQAPAXASASIAPAPIAZAZIAAAGIgDAAIgFAAQgHACgFAIIgIAKIAAAKIAAAHQgcgegWgjgAGWBNQgUghgPgeIgKgVIgNgoQgDgIAFgFIADAAIAAgCQAFgDAIAAQACAAADADIACAAIAFACIAFANIAXAoIAPAVIAcAjQgIgHgHACIgDAAQgKAAgFAKIgFAFQgDAFAAAIIgCAHgAD3gyQAAgHAFgFIADAAIAFgGIAFAAIACAAIAIAGIACAAQAFAFAAAHIAAADQAAAUAFASIAFAZIANAkQgIgIgHgCIgFAAQgKAAgIAKIgFAHIgCADIgDAKIAAAHgACXBIQAAgKgIgIIAAgCIgFgDQgFgFgHAAQgNADgHAKIgDAFIADgIIAKgSIAFgRIAKgkIAHgZIAAgKQADgIAFgCQADgDACAAQADgDAFADIACAAQADADACAAQAIACAAAKIgUB6IgDAHgABLBNIAAgFQAAgKgFgFIgCgFIgDgDQgHgFgIAAQgKAAgFAIIAUgfIAAgCIAUgnIAKgWIADgKQAAgIAFgCIAAgDIAFgDQADgCAFAAIAHACIADAAIAAADQAAAAABAAQAAAAABABQAAAAAAABQAAAAAAABIADAAIACAKIAAAKIgHAZIgPAkQAAAHgFAFIgSAuIgDACgAhABNIAAgFQAAgIgFgFIgDgFQgHgKgKADIgDAAIAAgDIAAADQgKAAgHAKQgFAFAAAFIgNAMIAAgCIgDgFIgCgKQAAgFgFgDQgFgHgIAAIgMAAIAegXIAhgcIghAcIgeAXQgFACgFAIIgIAMIAAADIgNAHIAAgHIgCgNIgFgFIgIgHIgFAAIgFgDIgKADIAjgXIApgcIAPgQIAHgFIADgCIANgNIAUgRIAFgFQACgIAIAAIANACQACAAADADIAFAIIAAACIgNAPIgKAPIgFAFIgPAQIARgNIAFgIIAQgPIAPgPIAAgCQAMgNAIACIACADIAIAFIADAFIgLAPIgKASIgWAaIgQARIgUAXIAagXIAPgRIAZgaQAFgKAIgIIAHgPIAFgFQABgIAFAAQACgCAFACIAFADIAFADIADACIAFAFIgKAPIgKAUIgSAaIgLAPQgWAhgXAZgAkHBIIgDgIIgCgFIgDgCQgHgKgLACQAAAAgBAAQgBgBAAAAQgBAAAAABQgBAAAAAAIACgCIgCACIgBAAIgCAAIgDADIgFACIgCADIgDAAIgPAMIAAgHIgCgIIgFgFIgDgFQgFgFgIAAIgCAAQA/ghA6gkIAtgeQASgSANAIIAAACIAFAIQgDAIghAZQgjAbhJAsIAAADQANgIAMgFIAJgEIgLAGQAPgCA3gkQA4gmAFgKQAHgKgFAAIgCAAIAAgCIAKgIIAKACIAFAAIADADIAFAIIgPAUIgLAKQgFACgCAFIgPAQIgpAcIgjAXIAAACIgIADIgCACIgFANIgUAMgAFIBFIgSg3QgFgNgDgKQgFgSgCgUQAAgIAFgFIACgCIALgDIACAAQAFAAADADIACACQAFAFAAAIIANAoIAKAVQAIARAMASQgHgFgIAAIgDAAQgKAAgHAKIgDADIgCAKIgDAFIAAAFgAAHBIIgFgPIgCgDQgIgKgLAAQgFAAgFAFIAXgcIAIgMIAUgdIANgWIAIgNIACgHQADgGAFAAIAHAAIAFAAIADADIAFADIAFAKIgFAKIgKAWIgSAkIgCAFIgkA4gADeA2IgDgCQgFgFgHgDIgFAAQgLAAgHAKIgIAPIAPh3QAAgHAFgFIADAAQAAgBAAAAQABgBAAAAQAAgBABAAQAAAAABAAQACgDAFAAIAFADQADAAACADIADAAQAFAFAAAHIACB6gAm6A5IgDgIIAAgFIgCgCQgIgIgKAAIgFAAICIhFQAcgPAhgPQghAPgcAPIiIBFIgpAAICQhFIA/ghQAIgHAFAAQACgDADAAIAFADIAFAFIADAFQAFgIAHgFIAIADIACAAIAFAHIADAGIgPAKIgNAFIgMAKIAMgKIANgFQAMgFAKgIIALgFQACgFAFgDQAFgCAFAFIADACIACADIAIANIgpAbQg3Aig/AjIgDADIgFACIgCAFQgBAAAAABQgBAAAAAAQAAAAgBABQAAAAAAABQgMAFgDAFIgCgDIAAgHIgDAAIAAgDIgCgFQgIgIgKAAIgFAAQAZgMAZgSQAagPAWgNIAPgIQAAgCAFAAQADgDAIgCQgIACgDADQgFAAAAACIgPAIQgWANgaAPQgZASgZAMIgDAAIAAADIgFADIgFAFIAAACIAAABIgRAOgAkbglIAZgPgAnlAwIgDAEIgKAKgAn/AiIAFAFIADADIAAACIADADIACAFIAAAKgAklAxgAnjAsIAIgIIgKAMgAjkAvgAnZAiIADAAIgFACgAHxALIgNgNIghgrIAAgFQAAgFADgCQAFgIAHADQALAAAFAFIAAACIAFAFIACAFIAXAXIAAAzg");
	this.shape_52.setTransform(163,136.7);

	this.shape_53 = new cjs.Shape();
	this.shape_53.graphics.lf(["#CF8972","#D6D2CA","#FFFFFF"],[0.141,0.651,1],-0.4,15.9,-0.4,-14.8).s().p("AnzCSIAAgfICBADQgvABAIAEQAPADC5AAQC4AABZgDQA6gDg9gCIHQAAIAAAfgAA9B2IhvAAIinAAIgoAAIhHAAIgKAAIgRAAIgPAAIiBgDIgag1IAKAIIAIAAIAFAAIAHgFIADgFIADgCQAAgDACgDIAAgCIADAAIAHAAIgHAAIAAgNIAKgKIAAAGIgDAEIgHAAIAHAAIAAAGIAAAHIADACQAAAIAFAFIAKAIIAHADQADgDADAAQAEAAADgFIAFgGIAAgCIADgFIADgDIAAgMIARgPQgFAFAAAFIAAAFIgMAAIAMAAQgDACADADQgDAFADACIgMAAIAMAAIAAAGIAFAKIAKAIIAIACIAHgCIAIgGIADgCIACgGIACgCIAAgFIADgKIAAgCIAAgGQACgEANgGIgFAKIAAAGIgKAAIAKAAIAAAEIAAALIgNgDIANADIAAACIAHANIAIAFIAHACIAIgCIAIgCIACgGIADgCIADgGIAAgCIAKAAIgKAAIACgNIAAgCIAPgMIgFAMIgKAAIAKAAIAAACIgCALIAAACIACAIQAAAHAFAFIAKAIIAKAAIAFAAIALgFIACgFIACgCIADgGIAAgCIADgNIAUgMIgDAMQAAAHADAGIAEAKQAIAKANgCQAKgBAIgJIAEgIIAQAAIAIAKIACACQAFAIAKAAQAKAAAIgKQAEgFAAgFIAGgNIAMgMIgCAEIAAAIIgKAAIAKAAQAAAHACAGIgSAAIASAAQAAAFAGAFIAHAFIAHAFIAIAAIAPgKIAGgKIACgNIAUAAIAAANIAIAKQAHAIAKAAQAIgBAKgJIAGgIIAWAAQAAAFAFADQAIAJAKABQAKgBAIgJIAEgIIAcAAIAGAKQAEAFAIADIAHACIAGgCIAMgKIAGgIIACgNIAZAAQgCAHAEAGIgdAAIAdAAQAAAFAGAFQAFAFAHADIAIAAQAKgDAFgHIADAAIACgIIAmAAIAFAIQAIAHAKAAQAKAAAHgKIAFgFIAZAAIAGAIQAEAHAIADIAKAAQALgDAFgHIAEgIIAcAAIAFAIQAIAHAIAAIAHAAQAKAAAFgHIAFgIIAeAAIAGAFQAHAKAKAAIACAAIAAAjgAgeBEIgWAAgAjlBEIgXAAgAjoA3IgRAAgAH0BEIgeAAIACgNIAaAAQAAAFACAFIAAADgAGKBEIADgNIAWAAIADANgAFBBEIADgNIAUAAIACANgAEUBEgADuBEIAFgNIAcAAIAFANgABWBEIADgNIAXAAQAAAHACAGgAATBEIACgNIASAAQgBAHADAGgAizA3IANgIIAAAIgAksAtgAlmArgAmgAogAnLAJIACAAQAAABgBAAQAAAAgBAAQAAAAgBABQAAAAgBABgAH3hFIAKAAIgKAAIgDgFIgEgFIAAgCQgGgFgKgBQgHgDgFAJQgDACAAAFIAAAFIgMAAIgDgFIgFgHIgCgDIgIgFIBaACQgCABgFAFIgCACIAAAFIgDAFIAMANIgMgNIADgFIAAgFIAJAAIAAAXIAAAKgAH3hFgAGNhIIgFgMIgFgDIgDAAQgCgCgCAAQgIAAgGACIAAADIgCAAQgFAFADAHIgSAAQAAgHgFgFIgCgDQgDgCgFAAIgCAAIgLACIgDADQgEAFAAAHIgNgCQAAgHgFgGIgCAAIgIgFICSADIgIAFIgCAAIgCADQgDACAAAHgADwhKQAAgHgEgGIgDAAQgCgCgDAAIgFgDQgFAAgCADQgBAAgBAAQAAAAAAAAQgBABAAAAQAAABAAAAIgDAAQgFAGAAAHIgSAAQABgKgIgDQgCAAgDgCIgCAAQgFgDgDADQgDAAgCACQgGADgCAHIgPAAIgCgKIgDAAQAAAAAAgBQAAAAgBgBQAAAAAAAAQgBAAgBAAIAAgDIgCAAIgHgDQgFAAgDADIgFADIAAACQgFADAAAHIgKAAIgGgKIgEgCIgDgDIgFAAIgHAAQgFAAgDAFIgCAIIgOAAIgEgFIgDgDIgFgCIgFgDQgFgCgCACQgFAAgDAIIgFAFIgGAAIgDgFIgHgFIgDgDQgHgCgMANIAAACIgKAAIAAgCIgGgIQgCgDgCAAIgOgDQgHABgDAHIgFAGIgKAAIgEgIIgDgDIgFAAIgKgDIgKAIIgKADIgGgIIAAgDQgMgHgSASIgNADIgHgOIgDgCIgCgCQgFgFgFACQgFACgCAFIgLAGIgHACIgDgFIgFgHIgCAAIgIgDQgHAFgFAIIgDgGIgFgEIgGgDQgCAAgCADIgDgjIAAgGIAAgHIAFAHQASAQAmAMQACAAADADIAjAAIA6ACIBnAIICWAEIADAAICLADIAFgFIAAAFIgFAAIgGAFIgDAAQgEAGAAAHg");
	this.shape_53.setTransform(161.6,139.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_53},{t:this.shape_52},{t:this.shape_51},{t:this.shape_50},{t:this.shape_49},{t:this.shape_48},{t:this.shape_47}]}).wait(1));

	// Layer 20
	this.shape_54 = new cjs.Shape();
	this.shape_54.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AAVARQAHgHADgKIACgOIAAgMIgCgDIAHADIAFACIAKgCIAFAAQAAgDADAAAAhgaIAFAAABHAFIAAgDIgHgQIgDgKIAAgFIAFgFQAIgDAAgFQAAgFgIgFIgyAAQgFADAAAXIAAAMQgDAKgHADIgBABIgMACIgKgCIgKgGABCAHIgCgCIgDAAQgDgDgCADQgDAAAAACQgFgCAAACIAAADQAAAFAFgDIAAAIQADgDACADIAAgDIADADIADgIQAFgCgDgDgABHAFIgCAAQgDAAAAACAAQAyIACgDQAAgCADAAIAFgDQAFAAAAgFIAAgCIACgDIAIgHIACgDIAFgCIAFAAAAwAHQgHgCgDAFIgCACIAAADIgDAFIgCACQgFADgDgDIAAgCQAAgDADAAIgFAAAAaARIAAAAAA4AqIgDADIAAACIgFAAQgFAAAAgCQgCgDACgCIADgFIAFAAIACAAIADgFQAAgDACAAIAAADIAAAFQADgDADAAIACADQADAFgDAAIgCAFIgDAAgAA1AjIAAACIADADIAAACAAQAyIAbACIAPgCIADAAIANgNIACgHIgCgDQgIgKgFADABMAeIAAgIIgCgKIgDgHAgoAqIAFADIADAAIAHAAIAKAAIADgDQACgFAFADQAAgFADgDQADgCACAAQgCgDACgCIAFgIQADgCAFAAIAFACAhLgGIACALQADAKAHAKIASAPIAFACIAAAIIADAAIAKAAQAPACAMgCIAGAAIACgDIAAgCIgCgDIgFAAIAAADIACAAIAAACIgCAAIAAgCAADAoIAFAAIADgDIAAgFQAAgCgDACIgCgCIgDAAAADAjIADAAQACACgCAAIgDgCQgCAAAAACIACADAAQAyQgFgDgFADAABAqQAAgCACAA");
	this.shape_54.setTransform(311.8,155.1);

	this.shape_55 = new cjs.Shape();
	this.shape_55.graphics.lf(["#CF8972","#D6D2CA","#FFFFFF"],[0.141,0.651,1],-0.8,-4.5,2.1,6.1).s().p("AAQAyIACgDQAAAAABgBQAAAAAAAAQAAgBABAAQAAAAABAAIAFgCQAFgBAAgFIAAgCIADgDIAHgHIADgDIAEgCIAGAAIgGAAIgEACIgDADIgHAHIgDADIAAACQAAAFgFABIgFACQgBAAAAAAQgBAAAAABQAAAAAAAAQgBABAAAAIgCADQgFgCgFACIADgDIAAgCIgDgCIgFAAQAAgBAAgBQAAAAAAAAQAAgBABAAQAAAAABAAQgBAAAAAAQgBAAAAABQAAAAAAAAQAAABAAABIAAACIACAAIAAACIgCAAIAAgCIAAACIACAAIAAgCIgCAAIAAgCIAFAAIADACIAAACIgDADIgGAAQgNACgOgCIgKAAIgDAAIAAgHIgFgDIgSgPQgHgLgDgKIgDgKIArAAIAKAGIAKACIANgCIABgBQAHgCADgLIAAgMQAAgXAFgCIAyAAQAIAFAAAEQAAAFgIADIgEAFIAAAGIACAJIAIAQIAAACIACAIIACAKIAAAIIgCgCIgBgBIAAgBQgEgFgDgBIgCgBIgBAAIgBAAIAAABIAAgBIABAAIABAAIACABQADABAEAFIAAABIABABIACACIgCAHIgMANIgEAAIgOADgAAsAtQAAADAEgBIAGAAIAAgCIACgCIgCACIAAACIgGAAQgEABAAgDIgCgDIACgCIACgFIAFAAIADAAIACgFQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAAAAAIAAACIAAAFQADgDADAAIACADIACAEQAAAAAAAAQAAABgBAAQAAAAAAAAQAAAAgBAAIgCAFIgCAAIgGgCIAAgDIgCgDIAAgCIAAACIACADIAAADIAGACIACAAIACgFQABAAAAAAQAAAAAAAAQABAAAAgBQAAAAAAAAIgCgEIgCgDQgDAAgDADIAAgFIAAgCQAAAAgBAAQAAAAAAAAQgBAAAAABQAAAAAAABIgCAFIgDAAIgFAAIgCAFIgCACIACADgAgPAtIACgCIABgBQAAgBABAAQAAgBAAAAQABAAAAgBQABAAABAAIAAAAIAAAAIADABIAAAAQAAgFACgDQAEgCACAAIADAAIADACQAAAAABAAQAAgBAAAAQABAAAAABQAAAAAAAAIAAAFIgCADIgGAAIAGAAIACgDIAAgFQAAAAAAAAQAAgBgBAAQAAAAAAABQgBAAAAAAIgDgCIgDAAIgBgDIABgCIAGgIQACgDAFAAIAFADIgFgDQgFAAgCADIgGAIIgBACIABADQgCAAgEACQgCADAAAFIAAAAIgDgBIAAAAIAAAAQgBAAgBAAQAAABgBAAQAAAAAAABQgBAAAAABIgBABIgCACIgKAAIgIAAIgCAAIgFgCIAFACIACAAIAIAAIAKAAgAABAlIACADIgCgDQAAAAAAgBQAAAAAAAAQAAgBABAAQAAAAABAAIADACIABAAIgBgCIgDAAIADAAIABACIgBAAIgDgCQgBAAAAAAQgBAAAAABQAAAAAAAAQAAABAAAAIAAAAgAAfAWIADgCIACgGIAAgCIACgCIABgBIAAAAQABgDAEAAIAAAAIABAAIACABIABAAIAAADQAAAAABABQAAABAAAAQAAAAABABQAAAAABAAIADgBIAAAIQAAgBABAAQAAgBABAAQABAAAAABQABAAAAABIAAgDIAEADIACgIQABAAAAgBQABAAABgBQAAAAAAgBQAAAAAAAAIgBgCQAAgBABAAQAAgBAAAAQAAAAABAAQAAgBABAAIADAAIgDAAQgBAAAAABQgBAAAAAAQAAAAAAABQgBAAAAABIgCgDIgCAAQgDgCgDACQgBAAAAABQgBAAAAAAQAAAAAAABQAAAAAAABIgEgBQgBAAAAAAQAAAAAAAAQAAAAAAAAQgBABAAAAIgBAAIgCgBIgBAAIAAAAQgEAAgBADIAAAAIgBABIgCACIAAACIgCAGIgDACIgEACIgDgBIAAgBIAAgCQAAgBAAgBQAAAAAAAAQAAgBABAAQAAAAABAAIgFAAQAHgHADgKIADgOIAAgMIgDgDIAHADIAGADIAKgDIAEAAQAAgBABAAQAAgBAAAAQABAAAAAAQABgBABAAQgBAAgBABQAAAAgBAAQAAAAAAABQgBAAAAABIgEAAIgKADIgGgDIgHgDIADADIAAAMIgDAOQgDAKgHAHIAFAAQgBAAAAAAQgBAAAAABQAAAAAAAAQAAABAAABIAAACIAAABIADABIAEgCgAAmgaIgEAAgAAGAygAA4ArgABMAegAADAeIAAAAgAA6ARIAAADQAAgBgBAAQAAgBgBAAQgBAAAAABQgBAAAAABIAAgIIgDABQgBAAAAAAQgBgBAAAAQAAAAAAgBQgBgBAAAAIAAgDQAAAAABgBQAAAAAAAAQAAAAAAAAQAAAAABAAIAEABQAAgBAAAAQAAgBAAAAQAAAAABAAQAAgBABAAQADgCADACIACAAIACADIABACQAAAAAAAAQAAABAAAAQgBABgBAAQAAABgBAAIgCAIgABCAHIAAAAg");
	this.shape_55.setTransform(311.8,155.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_55},{t:this.shape_54}]}).wait(1));

	// Layer 19
	this.shape_56 = new cjs.Shape();
	this.shape_56.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("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");
	this.shape_56.setTransform(266.6,139.2);

	this.shape_57 = new cjs.Shape();
	this.shape_57.graphics.f("#E1B8AA").s().p("AjfAAQgjgBANgBIAbAAIByAAIACAAICiAAIAmAAQAAgCBTACIBFACQgNAEi4AAQi5AAhbgEg");
	this.shape_57.setTransform(282.1,151.6);

	this.shape_58 = new cjs.Shape();
	this.shape_58.graphics.f("#F1CDB5").s().p("AgBAHQg4ghgHgKQgFgKAFAAIAUAPIAFAFIAIAHIAHAFQAKAFAKAJIAaAUIADACIAMAIIAPAHIADADIADAAIACACIAFADIADACQgQgCg1gmg");
	this.shape_58.setTransform(285.1,135.6);

	this.shape_59 = new cjs.Shape();
	this.shape_59.graphics.f("#B27F6D").s().p("AERApIgHAAIgIgFIgFgFIgCgCIgDgFIAAgDQgCgIACgFIAAgCQgCgDACgDIAAgHIADgDIACgFIADAAQAFgHAMAAIAFAAIADACIACAAIAFADIADACIAAADIAIAMIAAALIAAAKIgIAKIgKAKgAC2AdIgCgIIgFgPIACgGQAAgHADgFIACgFIAIgFIAFgDIAIAAIAKADIAFAFIACACIAIANIAAAIIAAACIAAANIgIAIQgHAKgKAAQgKAAgLgKgAB3AdIgFgIIgCgPIAAgGIACgHIAFgIIAAgCQAFgFAIgDIAIAAIAFADQAHACAFAFIAIAPIAAAGIAKAAIAFAPIgSAAIADgNIAAgCIAAACIgDANIgFAKIgCADIgPAFQgNAAgIgKgABHAnQgKgDgFgHIgHgIIAAgPQgDgDADgDIACgKIAFgFIAAgCQAIgIAKAAIACAAQALADAHAHIAFANIAAACIADAGIAAACIgDANIgFAKIgHAFIgIADgAgPAdIgFgIIgCgPIAAgFQAAgIAFgIIACgCQAIgKAIACQAHAAAIAFIAFAFIAIAQIAAAFIgDAPIgFAIQgIAKgMAAQgIgDgIgHgAiSAnIgPgKIgFgIIgDgPIADgFQAAgLAFgHIACAAIAFgDQAFgFAIAAQAKAAAIAKIAFAFIACALIAAAFQADAHgDAIIgHAIIgKAKgAhWAaIgFgFIgCgPIAAgDIAAgCIAFgQIACgCIAFgFQAIgFAIAAQAHAAAIAHIACADIAIASIAAAFIgDAPIgFAIQgHAHgKAAQgNAAgIgKgAjeAkQgHAAgIgHIgCgIIgDgDQgDgFAAgHIAAgGQABgKAHgHIADgDQAFgFAHAAIADAAQAMAAAIAKIAHANIAAAIIgCAPIgFAIQgFAHgIAAgAlAAaIgDgFIgFgPIAAgLIADgHIAFgIIAMgHIAFAAQANAAAIAHIACAIIADAFIACAIIAAAFIgCAPIgFAFQgIAKgNAAQgKAAgHgKgAl6AkQgIgCgFgIQgFgCAAgGQgCgFAAgHIAAgGQAAgKAFgHIAFgDQAFgHAKAAIACAAQAKACAIAIIAAACIAFAKIADALIgDAPIgFAIIgNAHgAnGAkIgPgKQgDgCAAgGIgFgMIAAgFIADgIIAFgKIACgDQAIgHAKAAIADAAQAHACAIAIIAFAHIACALIAAAFIgCAMIgFALIgNAHgAoPAiIgDAAIAAhAIADAAIAHADIANAKQAFAFAAAFIADAIIAAAFIgDAMIgFAIQgGAIgJAAIgFAAgAFLAfIgFAAIgHgFIgFgFIAAgDIgDgFIAAgCIgCAAQgDgFADgGIAAgCIAAgIIACgFIADgFIAAgCQACgDADAAQAFgIAHAAIADABIAFACIACADIAFACIADAFQAFAFAAAIIADAFIAAADIAAAHIgDADQAAAHgFAGIgKAHgAGIAdIgIAAIgHgFIgDgGIgCgCIgDgFIAAgDIgCgKIAAgDIAAgFIAAgCIACgFIAAgDIAFgFQAFgKAKACQADgCAFACIACAAIAGAGIAFAFIAFAKIAAAHIAAAFIAAAGIAAACIgIANQgCAFgIADgAHCAaIgIAAIgHgFIgDgFIgCgDIgDgFIAAgCIgCgLIAAgKIACgHIAFgFQAIgLAHAAIAFAAIADADIACAAIAGAFIAFADIACAKIADACIAAAIIAAAFIAAADIAAAFIgFAMIgLAIgAH0AVIgHgFIgFgDIAAgCIgDgFIgCgDIAAgCQgDgDADgFIAAgDQgDgCADgFIACgIIAAgCIADgGQACgCADAAQAFgFAHAAQAFAAADACQAFAAAAADIAFAFQAFAFAAAIIADAKIAAACIgDAIIgCAFIgFAIIgIAFIgIADgACvAGg");
	this.shape_59.setTransform(266.6,144.1);

	this.shape_60 = new cjs.Shape();
	this.shape_60.graphics.lf(["#CF8972","#F3A898"],[0.047,1],-0.1,7.4,-0.1,-7.1).s().p("AB9BHIAAgFIgIgRIgFgFIgZgcIgKgPIgVgdIgMgUIgIgMIANAAIAHAMIANAUIASAdIAKAPQAWAhAXAWgAA2BHIAAgFIgIgUIgCgCIgUgfIgVgmIgIgZIgFgKIALAAIAFAMIAKAXIASAhIACAFQAPAfASAbgAgUBHIAAgFIgCgMIgDgFIgKgVIgFgPIgKgkIgIgbIAAgIIANAAIACAIIAIAbIANAkIAFANIASArIAAACgAhgBHIAAgKIgPh3IAPAAIAXB8IAAAFgAizBHIANh+IAUgDIgDB7IAAAGgAj5BHIACgHIASg4IAHgZIAFgkIAAgCIAPAAIAAACIgFAkIgHAZIgNAkIgCAHIgDAIIAAAMgAlIBHIADgCQAUgeANgfIAKgXQAKgSAFgUIAPgCIAAACIgKAmIgKAXIgUAkIgFACQgFAIAAAKIAAAHgAmTBHQAZgbAZgmIANgSIAWgpIANAAQgIAUgPAVIgMASIgfAmIgCACIgFAKIgDAKIAAAFgAFbAaIgBAAIgEgDIgDgCIgDAAIgCgDIgPgHIgNgIIgCgCIgcgSQgKgLgKgFIgIgFIgHgHIgFgFIgSgPIgCgDIAJAAIAmAhIBqBKgACTAQIgRgQIgXgeIgNgPIgKgPIAKAAIAIAPIAMAPIAVAeIARAQIASAXgAGuAfQhCgfg6gmIgrgbIANACIAoAZQA1AkBCAkgAmyAXQAIgKAKgHIANgQIAggrIANAAIghArIgPAQIgcAZgACigZIgHgFIgNgPIgPgSIAKAAIAKASIANAPIAFAFIANAPgADXgZIgFgFIgCgDIgPgMIgSgSIAIAAIARASIAKAMIAFAFIAQAQgAE8hGIAIAAIAcARg");
	this.shape_60.setTransform(257,137.5);

	this.shape_61 = new cjs.Shape();
	this.shape_61.graphics.lf(["#CF8972","#D6D2CA","#FFFFFF"],[0.141,0.651,1],0.1,15.8,0.1,-14.8).s().p("AoPCUIAAgeIAAgjIACAAQANACAIgKIAFgIIACgMIAXAAIAFAMQAAAGACACIAQAKIAKAAIAMgHIAFgLIADgMIAZAAQAAAHADAFQAAAGAFACQAFAIAHACIANAAIAMgHIAGgIIACgPIASAAIAFAPIACAFQAIAKAKAAQANAAAHgKIAFgFIAjgDIADADIADAIQAHAHAIAAIAFADIAFgDQAHAAAFgHIAFgIIADgPIAXAAIACAPIAFAIIAPAKIANAAIAKgKIAIgIQACgIgCgHIAWAAIADAPIAFAFQAIAKAMAAQAKAAAIgHIAFgIIACgPIAQAAIACAAIADAPIAFAIQAHAHAIADQANAAAHgKIAFgIIAXAAIAIAIQAFAHAKADIAHAAIAIgDIAIgFIAFgKIACgNIAAgCIgCgIIAAgCIAMAKIADAPIgPAAIAPAAIAFAIQAHAKANAAIAPgFIADgDIAFgKIARAAIADAIQAKAKAKAAQAKAAAIgKIAHgIIAAgNIAAgCIAAgKIAVAKIgVAAIAVAAIAAACQgDAFADAIIgVAAIAVAAIAAADIACAFIADACIAFAFIAHAFIAIAAIAHAAIAKgKIAIgKIAAgKIAKAAIADAAIAAACIACAFIAAADIAFAFIAIAFIAFAAIAHAAIAKgHQAGgGAAgHIACgDIAAgHIAAgFIAKgDIAAADIADAMIgNAAIANAAIAAADIACAFIADACIACAGIAIAFIAHAAIAFAAQAIgDADgFIAHgNIAAgCIAAgIIAAgFIAAgHIgFgKIAPARIgKAAIAKAAIADANIgNAAIANAAIAAACIACAFIADADIACAFIAIAFIAHAAIAIAAIAKgIIAFgMIAAgFIAKAAIAAACIADADIACAFIAAACIAFADIAIAFIAFADIAIgDIAHgFIgXAyIkVADIgFACIAFgCIEVgDIAAAhgAhYB4QgNABAkACQBYAFC7AAQC4AAANgFIhEgDQhTgCAAACIgmAAIikAAIgDAAIhvAAIgcAAIm3gCgAgRBGIgXAAgAhYBGIgZAAgAikBGIgcAAgAlABGIgZAAgAmJBDIgfAAgAnVBDIgeAAgAByA3IgKAAgAD7BGgAAzBGIgXAAIADgPIASAAIACAAIAAAPgAkQA3IAeAAQAAAHACAFIgjADgAIQA5IgCAFIgDADgAEtA8IAAgNIgIgMIASAKIAAACIgKAAIAKAAQgCAIACAFgAGuA3gAD7A3gACnA3IAAgIIAKAIgAHlAyIAAAAgAHbAyIAAgFIAAgFIAAgIIAKAFIAAADIgKAAIAKAAQgCAFACAFgAFlAoQAAgIgGgFIASAKIAAAFIgKADgAoPg5IAAgUIAKAAIACADIAAAHIALgCIgXAWgAoPg5IAMgKgAoDhKIgCgDIgDgFIgHgFID6gFIACAAICNgDIADAAICVgCIBlgIIA9gCIAjgDIADgCQAogNAPgPIAIgHIgDAHIAAAIIAAAgIgFAAQgFAAgCADQgDAAgCAFIgDACIgKgKQgFgCgFACIAAADQgDAAgCAFIgDAFIgHAAQgFgFgFgDQgFgFgDAAQgFgCgFAFQAAAAgBAAQAAAAgBAAQAAABAAAAQAAABAAAAQgBAAgBAAQAAABAAAAQgBAAAAABQAAAAAAABQgFACgDALIgMgDQgUgUgKAKIgDAAIgFAIIgKAAIAAAAQgFgIgFAAQgFgDgDADIgFACIgCADQgFADgDAFIgIAAIgFgFQgFgIgFAAQgHgDgIAFIgCAAQgFAGAAACIAAADIgKAAIgDgDQgKgNgIAFIgFADIgFAFIgCAFIgIAAIgDgFIgHgKIgIAAIgFACIgIADIAAADIgFAHIgMAAIgDgHQgCgGgFAAQgFgCgFACIgDAAIgFADQgBAAAAAAQgBABAAAAQAAAAgBABQAAAAAAABQgFACAAAFIgMAAIgDgHIgCgDQAAgBAAAAQgBgBAAAAQAAAAgBgBQAAAAgBAAIgKgCIgFAAIgCACIgDAAIgDADQgFAFAAAIIgMAAQgFgKgFgDQgDgDgCAAIgFAAIgDADQgCAAgDADQgIACgCAIIgPAAQAAgIgFgCIgDgDIgCgDIgIAAIgFAAQgCAAgDADIgCAAIAAADQgGAFAAAFIgUACQAAgHgFgFIgCgDIgFgDIgFAAIgFAAQgDAAgFADIAAADQgFAFAAAHIgPAAIgDgKIgCAAIgDgCQgCgGgIAAIgCADIgFADQgBAAAAAAQgBAAAAAAQAAABgBAAQAAABAAAAQgHAFAAAFIgQADQAAgIgFgFIgCgCQgFgGgFADQgDgDgFADIgCADQgBAAAAAAQgBAAAAAAQAAABgBAAQAAABAAAAIgHAKIAAADIgNAAIAAgDIgFgKIgDAAQgFgFgFAAIgFAAQgFAAgCAFQgBAAAAAAQgBABAAAAQAAAAgBABQAAAAAAABIgFAHQAAAAgBAAQAAABgBAAQAAAAAAABQAAAAAAABIgNAAIAAgDIgDgHIgCgDQgFgFgIAAQgHAAgFAFIgDADIgCAFIgFAFIgLACgAkVhcIAAgDIACADg");
	this.shape_61.setTransform(266.3,139.2);

	this.shape_62 = new cjs.Shape();
	this.shape_62.graphics.lf(["#CF8972","#E7A87E","#FFFFFF"],[0.141,0.651,1],1.8,18.2,1.8,-12.5).s().p("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");
	this.shape_62.setTransform(264.7,136.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_62},{t:this.shape_61},{t:this.shape_60},{t:this.shape_59},{t:this.shape_58},{t:this.shape_57},{t:this.shape_56}]}).wait(1));

	// Layer 18
	this.shape_63 = new cjs.Shape();
	this.shape_63.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AgUARIgKgRQgDgGAAgIIAAgMIgFAAIgDACIgMgCIgFAAIgDgDIgCgFQgKgDACgFQAAgFAIgFIAwAAQAHADgCAXIACAMQAAAKAIADIAAABIANACIANgCIAHgGAghgaIADgDIgIADAAAAeIgDgFQAAgFgFgDIgHgCIgFACAgwAHQACAAgCADQAAAFgFgDQAAAFACADIADAAIAFACIACADIAIAHIAFAFQgCAFAFAAIAHADIADACIAAADAgwAHQAHgCADAFIACACIAAADIADAFIADACQAFADACgDIADAAIAAgCIgDgDIADAAAg4AqIADADIACACIAFAAIADgCIACgFIgFgFIgFAAIgCAAIAAACIgDADgAAAAoIgFAAIgFgDQAAgCACAAQgCgDACAAQAAgCADACIAAgCIAFAAAAAAoIAAgFIgDAAIgCACIAAgCIACAAAApAqIgDADIgCAAIgIAAIgKAAIgDgDQgCgFgFADIgFgIQgDgCAAAAAAAAtIAAAFIgFAAIAAgDQgDAAADgCIAAgDIAFAAIAAADIgDAAIADACIAAgCIAAAAIAAgFAgFAyQgDgDgHADIgcACIgPgCQgKgFgFgIIgDgHIAFgDQAIgKACADIAAgBAApAqIAAAIIgDAAIgHAAQgQACgPgCABNgGIgEALIgHAUIgSAPIgHACAg9gdIAAAFIAAAKIgCAAIgIAQIAAADIADAAQACAAADACIACgCQADgDACADIADACQAFgCAAACAg/AHIAAgCIACAAAg6ARQgCAAgBACIAAgHQgFgCADgDAhHAFIgCAHQgDAFAAAFIAAAIAg1AjIAAgFIgDgDQgCAAAAADIAAAFIgDgDIgFADIAAAFIAFAFIAFgD");
	this.shape_63.setTransform(116,155.1);

	this.shape_64 = new cjs.Shape();
	this.shape_64.graphics.lf(["#CF8972","#D6D2CA","#FFFFFF"],[0.141,0.651,1],0.8,-4.5,-2.1,6.1).s().p("Ag6AyQgKgFgFgIIgCgHIAEgCIACgDIABgBIAAAAIADgDIABgBIACgBIABAAIAAABIAAgBIgBAAIgCABIgBABIgDADIAAAAIgBABIgCADIgEACIAAgIQgBgFADgFIACgIIAAgCIAIgQIACAAIAAgJIAAgGIgCgFQgKgDACgFQAAgEAIgFIAwAAQAIACgDAXIACAMQAAALAIACIAAABIANACIANgCIAHgGIAsAAIgDAKIgIAVIgRAPIgIADIgCACIgDAAIgHAAIgLAAIgDgCQgBgEgDAAIAAAAIAAAAIgDABIAAAAIgFgIQgCgCgBAAQABAAACACIAFAIIAAAAIADgBIAAAAIAAAAQADAAABAEIADACIALAAIAHAAIADAAIACgCIAAAHIgCAAIgIAAQgPACgQgCIAAgFIAAgFIAAAFIAAAAIAAgCIgFAAIAAACIgBACIABAAIAAADQgCgCgIACIgcADgAgPAyIAAgDIgDgCIgHgCQgBAAAAgBQgBAAgBAAQAAAAAAgBQAAAAAAgBIAAgDIgFgFIgHgHIgDgDIgFgCIgCAAQgDgDAAgFIACABIABAAIAAAAQAAAAABAAQAAgBAAAAQABAAAAgBQAAgBAAAAIABgCIgBgBIABAAIAAAAIABAAIACgBIAAAAIABAAQADAAACADIAAAAIAAABIACACIAAACIADAGIADACQAFADADgDIACAAIAAgCIgCgDIACAAIAFgDIAHADQAGACAAAGIACAFIgCgFQAAgGgGgCIgHgDIgFADIgKgRQgDgGAAgIIAAgMIgFAAIAIgDIgDADIADgDIgIADIgCADIgNgDIgFAAIgDgDIADADIAFAAIANADIACgDIAFAAIAAAMQAAAIADAGIAKARIgCAAIACADIAAACIgCAAQgDADgFgDIgDgCIgDgGIAAgCIgCgCIAAgBIAAAAQgCgDgDAAIgBAAIAAAAIgCABIgBAAIAAAAIgBAAIABABIgBACQAAAAAAABQAAABgBAAQAAAAAAABQgBAAAAAAIAAAAIgBAAIgCgBQAAAFADADIACAAIAFACIADADIAHAHIAFAFIAAADQAAABAAAAQAAABAAAAQABAAABAAQAAABABAAIAHACIADACgAg4ArIADACIADACIAEAAIADgCIADgFIgGgFIgEAAIgDAAIAAgFIgDgCQAAAAgBAAQAAAAAAAAQgBAAAAABQAAAAAAABIAAAFIgDgDIgEADIAAAFIAEAFIAFgCIgFACIgEgFIAAgFIAEgDIADADIAAgFQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAAAAAIADACIAAAFIADAAIAEAAIAGAFIgDAFIgDACIgEAAIgDgCIgDgCIAAgDIADgDIAAgCIAAACIgDADIAAADgAAAAoIAAgFIgCAAIgDAAIAAACIADgCIACAAIAAAFIgFAAIgFgDQAAAAAAgBQAAAAABAAQAAgBAAAAQABAAAAAAIgBgCIABgBQAAAAABAAQAAgBAAAAQAAAAABABQAAAAABAAIAAgCIAFAAIgFAAIAAACQgBAAAAAAQgBgBAAAAQAAAAAAABQgBAAAAAAIgBABIABACQAAAAgBAAQAAAAAAABQgBAAAAAAQAAABAAAAIAFADIAFAAgAg9ATQABAAAAgBQAAAAAAAAQAAgBABAAQAAAAABAAQgBAAAAAAQgBAAAAABQAAAAAAAAQAAABgBAAIAAgHQAAAAgBgBQgBAAAAgBQAAAAgBgBQAAAAAAAAIABgCQgDgDgCAAIgDAAIADAAQACAAADADIgBACQAAAAAAAAQABABAAAAQAAABABAAQABABAAAAgAgwAHQAAgBAAAAQgBAAAAAAQgBAAgBAAQgBAAgBABIgDgDQgCgCgDACIgCAAIAAADIACgDQADgCACACIADADIAEgBQAAAAAAAAQABAAAAAAQAAAAAAAAQAAABAAAAgAgFAyIAAgDIgBAAIABgCIAAgCIAFAAIAAACIgCAAIACACIAAgCIAAAAIAAAFgAgCAtIACAAIAAACgAgFAjIADAAIgDACgAgCAjgAg1AjgAg/AEIACAAIgCADgAg9AEgAgmgag");
	this.shape_64.setTransform(116,155.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_64},{t:this.shape_63}]}).wait(1));

	// Layer 17
	this.shape_65 = new cjs.Shape();
	this.shape_65.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("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");
	this.shape_65.setTransform(161.2,139.7);

	this.shape_66 = new cjs.Shape();
	this.shape_66.graphics.f("#F1CDB5").s().p("AhAAvgAhCAvQACgCAFAAIgFACgAg7AtIAFgFIADAAIAMgKIAQgIQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIAXgSIASgLQAAgCADgDIAogeIAAgCIACAAQAFAAgHAKQgIAKg1AkQgwAjgSACg");
	this.shape_66.setTransform(142.8,135.9);

	this.shape_67 = new cjs.Shape();
	this.shape_67.graphics.f("#E1B8AA").s().p("Aj5ABQgNgEBTAAIByAAIAFAAICnAAIByAAQA8ADg6ABIkRACQi7ABgMgDg");
	this.shape_67.setTransform(145.7,151.9);

	this.shape_68 = new cjs.Shape();
	this.shape_68.graphics.f("#B27F6D").s().p("AhXAkIgFgFQgFgFAAgFQgCgFAAgIIAAgHIACgDQAAgFAFgFQAIgKAHAAIAFAAQAKgDAIAKIADAFIAFALIAAAFIAAACIgDANIgFAIQgIAKgHAAIgKACgAibAiIgCgDIgGgKQgCgFAAgIIACgHIAAgBIAIgMQADgIAHgCIANAAQAHAAAFAHQAFADAAAFIADAIIAAAFIAAACIgDANIgFAIQgHAKgKACQgKAAgIgHgAkPApIgKAAIgKgHQgFgFAAgIIgDgIIAAgCQAAgIADgDIAFgMIACAAIADgDIAFgCIAAgDIACAAIAIgCQAKAAAIAKIACACIADAFIACAFIAAAIIgCANIAAADIgDAFIgCACIgDAFIgKAFgADWAnIgPgKIgFgIIAwAAIgFAIIgPAKgACIAnQgIgDgFgFIgFgKQgCgFAAgIIAAgHQAAgDAFgFIAAgDIACgFQAIgHAMgDQAIAAAFAFIACADIADACIAIAQIAAAHIgDANIgFAIIgNAKgAAvAdIgFgIQgCgFAAgIIAAgHQACgIAFgIIAFgCQAGgIAKAAQAHAAAFAFQADAAACADIADACQAFAIAAAIIAAAHIgDANIgFAFQgHAKgKADQgNAAgIgKgAgTAdIgHgIQgDgFAAgIIAAgHQADgIAHgIIADgCQAHgIAIAAQAIAAAKAKIADADIAFANIAAAFIAAACIgFANIgDAIQgKAKgIAAQgKAAgIgKgAjdAfIgFgKQgCgFAAgIIACgKQAAgIAFgFIADgCQACgDADAAIADgCIAFgDIACAAIAIADIAFAAIAHAHIADADQAFAHAAAGIAAAHIgDANIgFAIQgHAKgNAAQgKAAgIgIgAIPAkQgKAAgKgKIgDgFIAAgDQgCgFAAgFIAAgHQgDgDADgFIAHgKQAFgIAIgCIAFAAIACAAIAAA/gAG8AkQgIAAgFgHIgFgIIgDgNIAAgHIAFgLIADgFQAIgKAKAAQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAQAIAAAHAHIADADIAHAKIAAAIIAAAHIAAAFIgCAIIgFAFQgIAKgHAAgAFwAkQgIAAgFgHQgFgDAAgFIgCgNIAAgCIAAgFIAAgDIAFgKIACgDQAIgKAKAAIACAAQAIgCAIAHIACADIAIASIAAAHIgDANIgFAIQgHAHgIAAgAEaAaIgCgFQgFgFAAgIIAAgHIACgIIADgFIAFgFQAHgKAKAAIAFAAQAIACAHAIIADAFIAAACIADAIIAAAKIgDANIgFAFQgIAKgKAAQgKAAgKgKgAlTAfIgKgHIgFgNIAAgKIAAgDIAAgFIAFgNIAFgFIAFgCIACgDIAFAAQALAAAFAFIACADIADAFIACACIAAAIIADAFIAAACIgDALIAAACIAAADQgCACAAADIgDADIgCACQgFAFgDAAIgIADgAmNAdIgKgIIgFgKIAAgFQgDgDADgDQgBAAAAgBQgBgBAAAAQAAgBABAAQAAgBABgBIAAgFIACgKIAIgHIAFgDIAAgCIAHAAQAKgDAIAKIADAFIAAADIACAHIADADIAAAFIgDAKIAAADIgFAFIAAAFIgFADIgFAFIgIACgAnAAaIgHAAIgKgIQgFgFAAgHIgDgDIAAgFIAAgFIADgFIAAgFIAFgIIAFgFQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIABAAIAAgBQACgCACAAIAFAAQAKgDAIALIAAACIADAAIAAAFIACAIIAAAHIAAALIgCACIgDAFIAAADIgFAFIgIAFgAn6AYIgHAAIgIgIIgFgIIgCgFIAAgIIAAgMIAHgNIADgCIAFgGQAFgCACAAQAIAAAFAFQACAAADAFIACADQAAACADADIADAFIAAAKIgDAKIAAADQgDACAAADIgCACIgDAFIgHAGgAD0AAIAAAIIgCANgAC/AIIADANgAiyAVIADgNIAKAAQAAAIACAFgAC/AGIADgIIAFgNQAKgKAKAAIAFAAIANAIIAAACQAHAIAAAHIg1AIg");
	this.shape_68.setTransform(161.2,144.3);

	this.shape_69 = new cjs.Shape();
	this.shape_69.graphics.lf(["#CF8972","#F3A898"],[0.047,1],0,7.3,0,-7.2).s().p("AF8BIIAAgHIAAgKIgHgKIgDgDIgbglIgQgSIgWgpIAPAAQAKAWANAVIAMAQQAXAmAaAdgAExBIIAAgHIgHgUIgDgDQgMgRgJgSIgKgXQgHgSgFgUIAPAAIAPApIAKAUIAhA9IACAEgADoBIIAAgMIgDgIIAAgCIgDgFQgHgSgFgUIgFgXQgFgSAAgUIAAgCIANAAIAAACIAHAmQACANAGAKQAFAcAKAbIAFAIIAAACgACUBIIAAgIIAAAAIAAh4IAVAAIAKCAgABJBIIAAgHIAWh8IASADIgPB0IgDAKIAAACgAgBBIIABgCIASguIAEgMIANgkIAIgZIACgKIAPAAIgDAHIgEAcIgNAkIgFASIgHASIgDAEIAAADQgFAFAAAFIAAAHgAhFBIIAAgCIAhg6IADgDIATgkIAKgWIAEgKIAKAAIgDAKIgLAWIgRAnIgVAhIgFADQgFAHgCAKIAAAHgAiOBIQAXgZAWggIAKgPIASgdIAMgUIAIgNIAQAAIgJANIgMAWIgXAdQgEAFgDAIIgcAeIgCADQgIAHgCAKIAAAHgAlxAlQBJgtAjgaQAhgZADgKIAKAAIAAADIAAACIgpAeQgCACAAADIgSANIgZAQQgBAAAAAAQgBAAAAAAQAAAAAAABQAAAAAAABIgQAHIgMAKIgDAAIgFAFQgFABgDACIADAAIgZANgAiWAPIAQgQIAWgcIAKgRIAKgQIAIAAIgIAQQgIAHgFAKIgZAcIgOAQIgaAWgAk6glQAVgPATgMIAKgCIgqAdQg9AlhBAhgAGYAFIgOgQQgTgSgRgZIAMACIAkArIAMAOIAPARIAAAIgAiigbIACgEIANgPIANgSIAHACIgNAQIgPAPIgFAEIgRAQgAjTgiIAOgMIAOgSIAKAAIgUASIgMAMIgGADIgEAEgAlBhIIAHAAIgjASIgQAIg");
	this.shape_69.setTransform(170.8,137.9);

	this.shape_70 = new cjs.Shape();
	this.shape_70.graphics.lf(["#CF8972","#E7A87E","#FFFFFF"],[0.141,0.651,1],-1.7,18,-1.7,-12.6).s().p("AHiBPQgagegWgmIgNgPQgNgVgKgWIAAgDQAAgFADgCIACgDQADgCAFAAIAFgDIADAAIACADIABAAIAHACIAAADIAHAHIAAADQASAZASASIAPAPIAZAaIAAAFIgDAAIgFAAQgHACgFAIIgIAKQgCAFACAFIAAAHgAGlg+IgKAAgABLBIQAAgKgFgIIgCgCQgDgDgCAAQgFgFgIAAQgKAAgFAIIAUghIASgnIAMgWIADgKQAAgFAFgFIAAgDIAFgCQADgDAFAAQACAAADADIAFAAIAAACIACAAIAAADQAFACAAAIIgCAKIgIAZIgMAkIgFAMIgSAuIgDACgAhABNIAAgFIgFgNIgDgFQgHgKgKADIgFAAIACgDIgCADQgIAAgHAKQgFAFAAAFIgNAMIgDAAIAAgCIAAgFIgCgKQAAgFgFgDQgFgHgIAAIgMAAIAegXIAhgcIARgQIAFgFIAQgPIAMgPIADgCQAKgNAKADIACACIAIAFIADAFIgLAPIgKASIgWAaIgQARIgUAXIAagXIAPgRIAZgaQAFgKAIgIIAHgPIADgFQADgHAFAAQACgDAFADIAFACIAFADIADAAIACAHIgHANIgNAUIgPAcIgLAPQgWAhgXAZgAjBBIQAAgIgFgHIgCgDIgIgHIgFAAIgHgDIgDAAQAPgHAPgNIApgcIAPgQIgPAQIgpAcQgPANgPAHIgFADIgCACQgDAAgDADIgCACQgFAFAAAIIgUAMIAAgKIgDgFIgCgFIgDgCQgHgKgLAAIgHACIACgCIADAAIgDAAIgCACIgDAAIAAADIgFACIgCADIgDAAIgPAKIgCgFIAAgIIgDgCIgCgFIgDgDQgFgFgKAAIgFAAQBBghA9gkIArgeQAUgUANAKIAAADIAFAFQgDAKghAZQgjAZhJAuIAAADIAZgNQASgDAyghQA1gmAIgKQAHgKgFAAIgCAAIAAgCQAFgIAFAAIAKADIAFAAIADACIAFAIIgPARIgNANIgFAHIAFgFIAFgCIANgNIAUgRIAFgFQACgIAIAAIANADQACAAADACIAFAIIgNARIgNAPIgCAFIgPAQIghAcIgeAXQgIACgCAIIgIAMIAAADIgNAHgAFFBFQgKgbgFgcQgFgNgDgKIgHgmIAAgDQAAgFAFgFIACgCIALgDIACAAIAIADIACACQAFAFAAAIQAFAUAIASIAKAXQAIARAMASQgHgHgIACIgDAAQgKAAgHAKIgDADIgFAKIAAAFIAAAFgAAHBIIgFgPIgCgDQgIgKgLAAQgHAAgIAIIAcgfQADgHADgFIAWgdIANgWIAIgNIACgHQADgFAFAAQACgDAFADIADAAIAFACIACADIAIAKIgFAKIgKAWIgUAkIgDADIghA6gACPA2IgCgCIgDgDQgFgFgHAAQgNADgHAHIAHgRIAFgSIANgkIAFgcIACgHQADgIAFgFIAFAAQADgCAFACIACAAIAFADQAIACAAAIIgXB8gADbA2IAAgCIgMgIIgFAAQgLAAgKAKIgFANIAPh1QAAgHAFgFIADgDIADAAQACgCAFAAIADAAIACACIAFAAIAAADIADAAQAFAFAAAHIAAB5QAAgJgIgIgAm6A5IgDgIIAAgFIgCAAIAAgCQgIgLgKADIgFAAICIhFQAcgPAhgPIAMgNQADgCAFAFIAHAIIADACIgrAZIgKAIQgBAAAAAAQgBAAAAABQAAAAgBAAQAAABAAAAIgPAIQgWANgaAPQgZASgcANIAAACIgFADIgHAHIgPAPgAmAA7IgDgHIAAgDIgCgFQgIgKgKADIgIAAQAcgNAZgSQAagPAWgNIAPgIQAAAAAAgBQABAAAAAAQAAgBABAAQAAAAABAAIAKgIIAPgHIAjgSIALgFQACgFAFgDQAFgCAFAFIAFAFIAIANQgUAMgVAPIh2BFIgDADIgFACIgFAFQgMAIgDAFgAnyA0IgCgFQgDgDAAgCIgDgDQgCgFgDAAICQhFIA/ggIANgLIAFAAIAFADIAFAFIADAFQghAPgcAPIiIBFIgDAAIgCADIAAAAIAAAAQgBAAAAAAQgBAAAAABQAAAAgBAAQAAABAAAAIgFAFIgFAIIgKAKgAkoAxgAiiAvgAHxALIgNgNIgjgrIAAgFIACgHIADAAQACgFAFAAIAAAAIANAAIAFAFIADACIAFAFIACAFIAXAXIAAAzgAHNg+IAFgDIAIADg");
	this.shape_70.setTransform(163,137.2);

	this.shape_71 = new cjs.Shape();
	this.shape_71.graphics.lf(["#CF8972","#D6D2CA","#FFFFFF"],[0.141,0.651,1],-0.4,15.6,-0.4,-15.1).s().p("AnzCSIAAghIESAFIhxAAQhTAAAMAFQANADC7AAIERgDQA6gCg9gDIHQAAIAAAegAA9B2IhvAAIipAAIgGAAIkSgFIgagzIAIAIIAIAAIAHAAIAHgFIADgFIADgDQAAgCACgDIAAgCIAKAAIgKAAIADgNIAKgKIAAAFIgDAFIgHAAIAHAAIAAAFIAAAIIADACQAAAIAFAFIAKAIIAHAAIAGAAIAHgFIAFgGIAAgCIADgFIADgDIAAgMIAOgPIgCAKIAAAFIgMAAIAMAAQgBAAAAABQAAABAAAAQAAABAAAAQAAABABABQgDAFADACIgMAAIAMAAIAAAFIAFALIAKAHIAIADIAHgDIAFgFIAGgCIAAgGIAEgFIAAgCIADgNIAKAAIgKAAIAAgFQACgFANgHIgFAMIAAAFIAAAFIAAAKIgNgCIANACIAFANIAKAIIAHACIAIgCQADAAAFgFIACgDIADgCQAAgDADgDIAAgCIAKAAIgKAAIAAgDIACgMIAKAAIgKAAIAAgDIAPgKIgFANQgDAFABAHIAAADIACAIQAAAHAFAFIAKAIIAKAAIAFAAIALgFIACgFIACgDIADgFIAAgCIAXAAIAEAKQAJAHAJAAQANAAAIgKIAEgHIAQAAIAFAKIADACQAHAIAKAAQAKgDAIgKIAEgHIASAAQAAAFAGAFIAEAFIAIAFIAKgDQAHAAAIgKIAGgHIACgNIASAAQgBAHADAGIgWAAIAWAAIAIAHQAHAKAKAAQAIAAAKgKIADgHIAFgNIAPAAQAAAHADAGIgXAAIAXAAIAEAHQAIAKANAAQAKgCAIgKIAEgFIAcAAIAGAKQAEAFAIACIANAAIAMgKIAGgHIACgNIAWAAIADANIgbAAIAbAAIAFAHIAPAKIAIAAIAPgKIAFgHIAmAAIADAFQAJAKALAAQAKAAAHgKIAFgFIAZAAQAAAFAGACQAEAIAIAAIAFACIAFgCQAIAAAIgIIAEgHIADgNIAWAAIADANIAFAHQAFAIAIAAIAKAAQAIAAAHgKIAFgFIACgIIAAgFIAYAAIACAAQAAAFACAFIAAADIgeAAIAeAAIADAFQAKAKAKAAIACAAIAAAjgAGmBEIgcAAgAA9B2gAFBBEIADgNIAUAAIACANgAEUBEgADuBEIACgNIAfAAQAAAHAFAGgAByBEgABWBEIADgNIAXAAQAAAHACAGgAhlBEgAh3BEIADgNIADAAIAMgNIgCAFIAAAIIgKAAIAKAAQAAAHACAGgAj8BEIADgNIAUgNIgDANQAAAHADAGgAjoA3IgRAAgAizA3IANgIIgDAIgAmgAogAndAlgAnLAJIADAAQgDAAgCADgAH3hFIAKAAIAMAKIAAAMgAIBhFIgKAAIgDgFIgEgFIgDgDIgFgFIAlAAQgFAAgCAFIgCADIAJAAIAAAUgAIBhKIAAAFIAAgFIADgFgAH3hFgAHChIIAAgCIgHgIIAAgCIgIgDIAoAAQgFAAgCAFIgDAAIgDAIIAAAFgADwhKQAAgIgEgFIgDAAIAAgCIgFAAIgCgDIgDAAQgFAAgCADIgDAAIgDACQgFAFAAAIIgSgDQABgHgIgDIgFgCIgCAAQgFgDgDADIgFAAQgGAFgCAHIgPAAQAAgHgFgDIAAgCIgDAAIAAgDIgFAAQgCgDgCAAQgFAAgDADIgFADIAAACQgFAFAAAFIgKAAIgIgKIgCgCIgGgDIgCAAQgFgDgCADQgFAAgDAFIgCAIIgQAAIgCgIIgDAAIgFgCIgFgDQgFgDgCADQgGAAgEAIIgDAFIgGAAIgDgFIgHgFIgDgDQgJgDgKANIgDADIgHgDIgGgHQgCgDgCAAIgOgDQgHAAgDAIIgFAFIgKAAIgEgHIgDgDIgFAAIgKgDQgGAAgEAIIgKAAIgGgFIAAgDQgMgKgVAVIgKACIgHgNIgFgFQgFgFgFADQgFACgCAFIgLAGIgHAAIgDgDIgHgIQgFgFgDADIgMANIgDgGIgFgFIgGgCIgEAAIgDghIAAgFIAAgHIAFAHQASAPAmANIAFAAIAjACIA6ADIBnAHICWAFIADAAICLADIACgFIAAAFIgCAAIgJADIAAACQgEAFAAAIg");
	this.shape_71.setTransform(161.6,139.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_71},{t:this.shape_70},{t:this.shape_69},{t:this.shape_68},{t:this.shape_67},{t:this.shape_66},{t:this.shape_65}]}).wait(1));

	// Layer 16
	this.shape_72 = new cjs.Shape();
	this.shape_72.graphics.lf(["#865039","#E3AA66","#FAE9A5"],[0,0.396,0.965],0.1,4.8,0.1,-4.8).s().p("ABoArQgDgDAAgIIAAgFQAAgIADAAQAIAAAAAIIAAAIQgCAFAHAAIAMgDIAAgSQADgHAFAAQAFAAAAAFQgCACAAAIIgDAKIAPAAQADADgBgFIAGgNIAAgDQACgCADAAQAIAAgDAFIgFANIgFAIIgGACIgEAAIgQgCIgTACQgGAAgCADgAhaAtIgKgFIgIgDIgUAIQgIACgDgEQgCgDAHgDIASgCQgPgIAAgCQgNgBgHAGQgFACAAgHQAAgBAAgBQAAAAABAAQAAgBAAAAQABAAABAAIAWgDIAHgEQAAgBABgBQAAAAAAAAQAAgBABAAQAAAAABAAQABgBAAAAQABAAAAAAQABAAABAAQAAAAABABQABAAAAAAQAAAAAAABQAAAAAAAAQAAABgBABIgFAEIAVAAIgIgEIgDAAQgBgBAAgBQAAAAAAAAQAAgBAAAAQAAAAABAAQAAgBAAAAQAAAAABAAQAAAAAAAAQABAAABABQANACACAFIAWADQADAAgDAFQgCAFgHgCQgDgDgDAAIgHgCIgKAHIARAIIAGAFIgGADgAhvAeQAIgDADgCIgYAAQAAACANADgAinAlIgCgCIgFghIgHANQgIAHgCgDIgDgCIAAgCIAFgGQAIgFAEgNIAAgHIgOADQgIAAAAgDQAAgGAIABIAMAAIgCgIIAAgIQgDgHAFgCIAFAJIADAQIAHAAQAGAAgBACQABAGgGgDIgHAAIACAKIAQAGQACAEgCADQgGACgCgEIgIgGIAIAkQACAKgCADQgFAAgBgLgAAIArQgCgDAKAAIAUgNQADgFgDgDQgDgEgFAEQgRAQgTAIIgJgDQgDgFAKAAQATgDAPgPIACgFIgCAAQgDgCgFAFQgSAOgNADIgIgCQABgGAFABQAMgDALgHIAIgGQgDgCgDACIgIADIgPAHQgKADAAgFQgDgDAIgCQARAAAJgMIgVAFIgKAAQgDAHgJADQgIgBAAgCQAAgCAFgDQAHgDAAgFQAAgHgHACIgHAAIACArQADAIgFAAQgGAAAAgIIAAgmIgCghQgDgFAFgDQAGgCAEACIAIADIAIACQAMAIgIAFIgHAFQgDAGAGAEIACAJIAIgBIAhgCIAlACIAGABIAFAAIAAACQgIAIgIgDIgEgCQgNgGgNABQgCgBgCADIAAADIACAAQASACAKAIQAEAFgCAFIgFAHQgHAIgSAFQgHAAgBgCgAgvgJIAEgEQALgJgBgFQADgCgFgFIgFAAIgHAAgAC0APQghgLg8ALQgNAEADgEQgBgFAIgDIAfgCIAAgdQgDgFADAAQAEgDADADIAWgNQADgEADAAIACACQAIAHgIADIgZAHQgDAAABAGQgDAFAFgDIAhAAIAFAAQAHAAADADQgDAFgHAAIgDAAIAAACQgDABgCANIAZACIANAAIAFAAIgDAFQgHACgCADgAB6gJIAAAOIAcAAQAAgJACgFIgCgCIgSAAIgFAAQgFAAAAACgAh2AHIgNACQgIADAAgFIgCgCIgFgFQgDgDAIAAIAUgDIAAgFQgKAAAAACQgIAAgCgEQgDgDABgFIgGgKQgEgGAHAAIAUAAIAAgCQgCgHAEAAIAGAEIAAAFIAUAAQAMAFgEAFIgGALQAAAFgEAAIgOAAIAAAFIAQAAQAPADgFADIgCAFIgIACIgNAAIgDAFQgEgCAAgDgAiIAAIAFAFIANgDIgDgDIgNAAQgEAAACABgAhyACIAQAAQADADgBgFIgCgBIgQAAgAh0gQIAKAAIAGgDIAAgCIAAgGQgBgEgHAAQgBgDgJAAIACAHIAIAAQAFABAAACQAAADgFAAIgIAAgAiIgTIACADIAKAAIAAgFIgHAAIgFAAIgDAAgAiLgfQAAAAgBAAQAAAAAAAAQgBAAAAABQAAAAAAABIACAFIAPgDIAAgEIgIgCIgHACgAgCgJQgDgCAAgFIAAgSQAAgIAIADIAVAAQAOACgEAIIgDAQQAAAHgKgDIgSAAIgDACIgCgCgAAAgTQAAAGADAAIASAAIADgGIACgCIgXAAQgDAAAAACgAAAgdQAAADADgBIAXAAIAAgEQAAgDgFAAIgSAAQgDAAAAAFg");
	this.shape_72.setTransform(215.1,121.3);

	this.timeline.addTween(cjs.Tween.get(this.shape_72).wait(1));

	// Layer 15
	this.shape_73 = new cjs.Shape();
	this.shape_73.graphics.f("#8D5656").s().p("AlFARIgLgRIgCgJIACAAIADAIQACAFAIALIACAFgAFNACIADgFQADgIgDgFIAAgDIADANIAAAGIgDAFg");
	this.shape_73.setTransform(214.1,122.3);

	this.shape_74 = new cjs.Shape();
	this.shape_74.graphics.f("#AA7373").s().p("AlOAFIgCgGIACgFIADgCIAAAHQAAAIAFAKIACAFQgHgKgDgHgAFNgBIgFgKQgFgFACAAIALAIQACAFgCAFIgDAIQgDgFADgGgAlJgNQAAgBABAAQAAgBAAAAQAAAAABgBQAAAAABAAIAAgCIACAAIADACIgDADIgFACgAE+gSQgBgBAAgBQAAAAAAgBQAAAAAAAAQAAAAABAAIACAAQADAAAAAFQgFAAAAgCg");
	this.shape_74.setTransform(214.2,121.5);

	this.shape_75 = new cjs.Shape();
	this.shape_75.graphics.lf(["#561414","#99573C","#D3BE81"],[0.122,0.655,1],-2.4,-17.3,-2.4,9.3).s().p("AFIACIgtADIozAAIgwgDQgIAFgCAFIAHgQIADgCQASgKAeAKIIzAAQAfgKAOAKQADAAACACIAGAQg");
	this.shape_75.setTransform(214.1,114.2);

	this.shape_76 = new cjs.Shape();
	this.shape_76.graphics.lf(["#561414","#99573C","#D3BE81"],[0.122,0.655,1],28,-5.8,28,23.1).s().p("ADbBDQAagCAMgMIA6hkQANgTgegDIgPAAIAtgCIAKAKQAFAEgFAIQgCANgLAEQgKAFAIABIANAKIAAACIAAADIgLgHQgCAAAFAEIAFAKQgDAGADAFIADADQgGAJgHAGQgNAHgRADQgKAAAFAKQAFAIgSAKQgPAKgSAAQgUAAgDgGgAE+gVQAAADAFAAQAAgGgDAAIgCAAQgBAAAAAAQAAAAAAABQAAAAAAABQAAAAABABgAkUA/QgPgKAFgIQAFgKgMAAQgPgDgNgHIgDgFIgCgGQgFgJAAgJIAAgHIgDADIgCAEIgDAAQAAgHAFgFIAIgIIAFgCQAFgBgIgFQgKgEgFgNQgCgIACgEQADgGAHgEIAwACIgPAAQghADANATIA9BkQAKAMAbACQgFAGgRAAQgSAAgSgKgAlGgSQgBAAAAAAQgBAAAAAAQAAAAAAABQgBAAAAABIAAACIAFgCIADgCIgDgDIgCAAg");
	this.shape_76.setTransform(214.2,121.8);

	this.shape_77 = new cjs.Shape();
	this.shape_77.graphics.lf(["#5E1717","#E38E26","#FEECBA"],[0.082,0.655,1],-1.8,-9.5,-1.8,20.4).s().p("AjEA2QgcgDgMgKIguhMQgMgPAbgDIIYAAQAZADgKAPIgtBMQgSAKgXADg");
	this.shape_77.setTransform(214.3,121.9);

	this.shape_78 = new cjs.Shape();
	this.shape_78.graphics.lf(["#5E1717","#E38E26","#FEECBA"],[0.082,0.655,1],-1.3,-13.7,-1.3,35.2).s().p("AjZBFQgbgDgKgMIg9hjQgNgUAhgDIAPAAII0AAIAPAAQAeADgNAUIg6BjQgMAMgaADgAkngmIA1BXQANAKAZACIGcAAQAZgCANgKIA1hXQAMgRgegDIouAAQgcADAKARg");
	this.shape_78.setTransform(214.1,121.6);

	this.shape_79 = new cjs.Shape();
	this.shape_79.graphics.lf(["#5E1717","#E38E26","#FEECBA"],[0.082,0.655,1],0,-2.6,-2.5,38.4).s().p("AjOA8QgZgCgNgLIg1hWQgKgSAcgCIIuAAQAeACgMASIg1BWQgNALgZACgAkagiIAuBNQAMAJAcADIGKAAQAXgDASgJIAthNQAKgPgZgCIoYAAQgbACAMAPg");
	this.shape_79.setTransform(214.3,121.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_79},{t:this.shape_78},{t:this.shape_77},{t:this.shape_76},{t:this.shape_75},{t:this.shape_74},{t:this.shape_73}]}).wait(1));

	// Layer 14
	this.shape_80 = new cjs.Shape();
	this.shape_80.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AgOggQAFgFAIgCIAGgIIAAgCQAFgLAPgCIARgFIAAAAIAFgCIAAgBIABAAIAEgCIAAAAQAKgIAFAIIADAFIACACIAAAFIgFADIAAACQADAGgFAAIAAACIgDAFIgFADIgCAAIgFAFIgDACIgFADIgFAAIgHgFIAFgDIACAAIADgCIAFAAIAKAAAARgRIAIgCQAHgDAFAFIADADQAFgFACAHQADgFAHADQAFAAADAHQAAAHgFACIgNgCQgHAEgDgEQgCgEgIACIgCAAIgIgFIgCgFIgDAAIgFgCABLgRQACADgCAAIgFgDIACAFQgFAFAAAFQAFgHANgDIAHgCIADAAIACAAIAGAAIgDASIgDAAIgHgEQgDAMgHgKQAAAFgFAFIACAAQAIAFgFAHIgFASIACAFIAKASIADAHIACAFABLgRIAAgCIAFgFIAFAAIAFgFQgIAAgHAFQgIACADAFABhg0IAGAAIAAADIACAFIgCAFIgDACIAAAFIgKADABIg8IAPgCQAIAAAAAHIACAAABnggIAAAFIgGAFIgPAFIgHAAABnggIACAAIAAABIADACIAAAHIAAADQAAAHgFgCABkggIADAAABLAMIgKAAIgNACABDgCIgCAEIAAAKAgTgYIAFgFIAAgDAgTgYIAAgDIgDgCIADgDIAFAAAgMgRIgCADQgIAAAAgFQgCgDACgCIADAAAgvAYIANgKQAHgCADgFIAKgLIACgKIAAgDIADgCIADAFIAFAAIADgFIAFAFIAFgIIAFAFIgCAFAgvAYIANgCQAFgFACAAIADgDQAAgCACAAIADAAAhrAvQAWgSAkgFIACAA");
	this.shape_80.setTransform(280.9,124.6);

	this.shape_81 = new cjs.Shape();
	this.shape_81.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],5.3,-0.5,-9,1.9).s().p("AgvAfQAWgSAhgEIADAAIAMgJQAIgCACgFIAKgNIADgKIADACIAAADIgDAIQgFAHgCAIIgDAAQgBAAAAAAQgBAAAAABQAAAAAAAAQgBABAAAAIgCABQgDAAgFAFIgMADIAMgDIgCADIgFACIgDAAIgSAFIgFAAIAAADIgMAEQgDAAgDADIgRAFg");
	this.shape_81.setTransform(274.9,126.2);

	this.shape_82 = new cjs.Shape();
	this.shape_82.graphics.lf(["#F1AC50","#FEF995"],[0,1],0.2,0.2,0.1,-0.4).s().p("AAAABIgCgBIACgCIADAAIAAACIgDADg");
	this.shape_82.setTransform(278.9,121.6);

	this.shape_83 = new cjs.Shape();
	this.shape_83.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],1,-0.1,-0.5,0.2).s().p("AgBARIAAgCQgDgDADgFIABgHIAAgBIACgFIAAgFIgCgCIgBAAIAAgDIABAAQAHAFgFAIIgDAPIABAFg");
	this.shape_83.setTransform(288.7,127.6);

	this.shape_84 = new cjs.Shape();
	this.shape_84.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],1.3,0.2,-0.9,0.5).s().p("AgDASIAAgCIADgDIAAgDIAAgFQABgFgEAAIgCAAIACAAIAEAAIgBASgAABAAIACAAIAAgDIADgCIgDgIIAAgEIADACIAAAHIAAADQAAAFgDAAIgCAAg");
	this.shape_84.setTransform(291.1,123.2);

	this.shape_85 = new cjs.Shape();
	this.shape_85.graphics.lf(["#BD7D48","#D7B068"],[0,1],-4.6,-2.8,-1.7,14.2).s().p("AgCABIAFgBIgDABg");
	this.shape_85.setTransform(286.8,120.4);

	this.shape_86 = new cjs.Shape();
	this.shape_86.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],0.2,-0.6,0.1,0.6).s().p("AAAgCIAOAAQAAAAABgBQAAAAAAAAQABAAAAAAQAAABAAAAIgIACIgIAAIgFAAIgDABIgCAAIgFADQAFgEAKgCg");
	this.shape_86.setTransform(285.5,120.6);

	this.shape_87 = new cjs.Shape();
	this.shape_87.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],1.7,0.2,-1.4,0.7).s().p("AAOAEIAAgDIACAFIgCAFgAADgFIAAgDIgDAAIgIAAIgFAAIgCAAIAPgCQAGAAAAAIg");
	this.shape_87.setTransform(289.8,119.4);

	this.shape_88 = new cjs.Shape();
	this.shape_88.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],1.9,1.5,-1.6,-1).s().p("AgKAAIAQgDIgDACQgBAAAAABQgBAAAAAAQAAAAAAAAQgBAAAAAAIgIACIgJADIgGAAIgHAIQAFgLAPgCgAAGgDIAFgCIgDACgAALgGIAFgCIgCACgAAcgIIgCgDIgFAAIgFADQAKgIAFAIg");
	this.shape_88.setTransform(284.5,118.2);

	this.shape_89 = new cjs.Shape();
	this.shape_89.graphics.lf(["#F1AC50","#FEF995"],[0,1],4.6,-9.3,-1.5,7.1).s().p("AAtBRIgDgEQgPgZgrAEIgVAFIhGgZIADAAIARgFQACgDADAAIANgEIAAgDIAEAAIAVgFIADAAIAEgCIADgDQAFgFACAAIADgCQAAgBAAgBQAAAAABAAQAAgBAAAAQABAAAAAAIADAAQADgGAEgHIADgIIAAgDIgDgCIAAgCIgCACQgHAAgBgFQAAgBAAAAQgBgBAAAAQAAgBABgBQAAAAAAgBIADAAIAFgFIAAgDQAFgEAIgDIAFgHIAAgDIAIgIIAFAAIAKgDIAKgCQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAABAAIACgDIAAAAIADAAIADgCIAAgBIAAAAIACAAIACgBIAAgBIAFgDIAGAAIACADIACAAIADAFIADADIAAAEIgGADIAAADIABADQAAAAAAABQAAAAAAAAQgBABgBAAQAAAAgBAAIAAADIgCAEIACgEIAAgDQABAAAAAAQABAAABgBQAAAAAAAAQAAgBAAAAIgBgDIAAgDIAGgDIACAAIAFAAIAHAAIAGAAIAAADIACADIACAAIAAACIAGAAIAAADIAAADIAAAHIgCADIAAAEIgLADIALgDIACAAIAAAGIgGAEIgOAGIgIAAIAAgDIAFgFIAFAAIAFgFQgHAAgIAFQgGACAAADIABADIgBgDQAAgDAGgCQAIgFAHAAIgFAFIgFAAIgFAFIAAADIABABIgBABIgFgCIADAEQgGAFAAAGIgCAFIAAAIIAAgIIACgFQAFgIANgDIAIgCIACAAIgCAAIgIACQgNADgFAIQAAgGAGgFIgDgEIAFACIABgBIgBgBIAIAAIAOgGIAGgEIAAgGIACAAIAAABIAAAFIADAHIgDADIAAACIgCAAIgGAAIgCAAIACACQAGABgCAEIAAAGIAAACIgEADIAAACIgHgFQgCALgIgIQAAAFgFADIgKAAIgNADIANgDIAKAAIAAADIACAAIADACIAAAFIgDAFIAAACIgCAIQgDAFADADIAAACIACAAIAKASIADAHIADAGIgwAUgAA8gHQAFgDAAgIQgDgHgFAAIgBAAIAAAAIgDgBIAAAAIAAAAQgEAAgBADIAAAAIgBgBIAAAAQAAgBgBAAQAAgBgBAAQAAAAgBgBQAAAAgBAAIAAAAIAAAAIgDACIgDgCIAAgBIgBAAQgDgDgEAAIAAAAIgBAAIgDABIgBAAIgHADIgFgGIgFAIIAFgIIAFAGIgDAEIADAFIAHAGIADAAIAEgBIAAAAIABAAQADAAACADIAAAAQACAGAJgGgAAMgXIACAAIgCAAIgFgCgAAHgZIgFgFIgDAFIgFAAIgDgFIgDADIADgDIADAFIAFAAIADgFgAAlgoIAFAAIAGgDIACgCIAFgFIACAAIAGgDQAAAAgBAAQAAgBAAAAQAAAAgBABQAAAAgBAAIgPAAQgLADgEAFgAAwgKQgJAGgCgGIAAAAQgCgDgDAAIgBAAIAAAAIgEABIgDAAIgHgGIgDgFIADgEIAHgDIABAAIADgBIABAAIAAAAQAEAAADADIABAAIAAABIADACIADgCIAAAAIAAAAQABAAAAAAQABABAAAAQABAAAAABQABAAAAABIAAAAIABABIAAAAQABgDAEAAIAAAAIAAAAIADABIAAAAIABAAQAFAAADAHQAAAIgFADgABDgMIAAAAgABLgbIAAAAgABLgbgAARgbgAAlgoIgHgFIAEgCIADAAIADgDIAFAAIAKAAIgFAFIgCACIgGADgAAegtg");
	this.shape_89.setTransform(280.9,125.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_89},{t:this.shape_88},{t:this.shape_87},{t:this.shape_86},{t:this.shape_85},{t:this.shape_84},{t:this.shape_83},{t:this.shape_82},{t:this.shape_81},{t:this.shape_80}]}).wait(1));

	// Layer 13
	this.shape_90 = new cjs.Shape();
	this.shape_90.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AANggQgFgFgHgCQgDgDAAgFIgDgCIAAgDIgFgDIAAgCIgPgFQgNgDgMgHQgDgDgCAAQgKgFgFANIAAACIgDADIAAACIAFADIAAACQgCAGAHAAIAAACIADAFIAFADIACAAIAIAAIAFAAIACACIADAAIAFADIgFAFIgIAAIgFgDIgCgCIgDgFAgRgRIgIgCQgIgDgCAFIgDADQgHgFgDAHQgCgFgFADQgIAAAAAHQgCAHAFACIAMgCQAIAEACgEQADgEAHACIADAAQAIgCAAgDIACgFIAAgFIgCAAIAFgFIAFAIIAFgFIADAFIAFAAIACgFIADACIAAADQAAAFAFAFIAHALIAKAHIAQAKQAlAFAXASAgPgMIAFAAIADgCAANggIAFAAIADADIgDACIAAADIgCgFgAASgYIAFAAQADACgDADIgHAFIgFgDAhiggIgDgFIgCgCIAAgFIAAgFQAAgDACAAIADAAAhngOQgFACAAgKQAAgFACgCIAAgDIADAAIAFAAAhMgRIgFAAIgRgFQgFgCAAgDIAAgCIAAgDAhig3IACAAQADgHAFAAIAPACAhMgRQADgCgDAAIgFgFIgCAAIgIgFIgHgDAhEgCQgCgHgNgDIgKgCIgDAAIgHAAAhbgdIADAAQAFAAAHAFQALACgDAFIgCAFIACAKAhMgRIAAADIAIgDAhBAMIAAgKIgDgEAhMAMIALAAIAPACAhMAMIgFgKQgHAKgDgMIgHAEQgFgGAAgMAhdBNIACgFIADgHIAKgSIACgFIgFgSQgFgHAIgFIACAAAAVAMIAMAKAAkAWIANAC");
	this.shape_90.setTransform(147,124.6);

	this.shape_91 = new cjs.Shape();
	this.shape_91.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],-1,-0.1,0.5,0.2).s().p("AAAARIACgFIgDgPQgFgIAGgFIACAAIAAADIgCAAQAAAAAAAAQAAAAgBAAQAAAAAAABQAAAAAAABIAAACIABADIAAAFIACABQAAAEACADIgCAIIAAACg");
	this.shape_91.setTransform(139,127.6);

	this.shape_92 = new cjs.Shape();
	this.shape_92.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],-2.1,0.9,1,1.4).s().p("AgKADQADgFAFAAIANACIgIAAIgFAAIgDAAIgCABIAAACg");
	this.shape_92.setTransform(138.4,118.6);

	this.shape_93 = new cjs.Shape();
	this.shape_93.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],-1.1,0.2,1,0.6).s().p("AAAAAIAGAAIAAAAQgFABAAAEIAAAGIACACIAAAFQgDgHAAgLgAgFgIQAAgFADgCIAAgDIACAAIAAADIgCADIAAAHIAAAFIACAAIgCAAQgDAAAAgIg");
	this.shape_93.setTransform(136.6,123.2);

	this.shape_94 = new cjs.Shape();
	this.shape_94.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],-5.3,-0.5,8.9,1.9).s().p("AAtAfIgSgFIgCgDIgNgEIgDgDIgFAAIgSgFIgCAAIgFgCIAAgDIgDAAIgIgGQgCgCgCAAIgIgPIgFgIIAAgDIADgCQAAAFAEAFIAIANIAKAHIAQAJIgNgDIANADQAjAEAXASgAgIAJg");
	this.shape_94.setTransform(152.9,126.2);

	this.shape_95 = new cjs.Shape();
	this.shape_95.graphics.lf(["#F1AC50","#FEF995"],[0,1],-0.1,0.2,0,-0.4).s().p("AAAAAIgCgCIADAAIACACIgCABIAAACg");
	this.shape_95.setTransform(148.7,121.6);

	this.shape_96 = new cjs.Shape();
	this.shape_96.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],0,-0.7,0.1,0.6).s().p("AAJABIgCAAIgCgBIgFAAIgGAAIgCAAIgGgCIAOAAQAMACADADg");
	this.shape_96.setTransform(142.3,120.6);

	this.shape_97 = new cjs.Shape();
	this.shape_97.graphics.lf(["#F6ED8D","#FFFFFF"],[0,1],-1.5,1.9,2,-0.6).s().p("AAWAJIgIgDIgKgCIgCgDIgDgBIgDAAIgCgCIgDAAIgCgCIgDgDIgHAAIgDADIgFAEQAFgMAKAFQADAAACADQALAFAMADIAQAFg");
	this.shape_97.setTransform(142.8,117.8);

	this.shape_98 = new cjs.Shape();
	this.shape_98.graphics.lf(["#F1AC50","#FEF995"],[0,1],-4.7,-9.3,1.3,7.1).s().p("AheBDIACgGIACgHIAKgSIADAAIAAgCIADgIQgDgDAAgFIgDgCIAAgFIgCgDIAAgCQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAAAAAIADAAIAAgDIgFgIQgIAIgCgLIgIAFIAAgFIgCgCIAAgGQAAgEAFgBIAAgCIADAAIAKACQANADACAIIgDgLIADgEIAAgCQABgEgJgCQgHgFgGAAIgCAAIgIgDIgCgEIgCgDIAAgFIAAgFQAAgBAAAAQAAgBAAAAQAAgBABAAQAAAAABAAIACAAIAAgCIADAAIADAAIAAgDIACgDIACAAIAIAAIAIAAIAFADIAAADIgBACQAAADAGAAIAAADIACAEIAGADIACAAIAHAAIAGAAIACADIACAAIAGACQgDgFgNgDIgPAAIgCgEIAAgDQgGAAAAgDIABgCIAAgDIgFgDIAAgCIACgCIAAgDIAGgFIACgDIAIAAIACADIADACIACAAIACADIADAAIAFADIACACIAKACIAIADIAIAAIAAADIAFACIAAADIADADQAAAFADACQAHADAFAEIACADIADAFIAFAAQABABAAAAQAAABAAABQAAAAAAABQAAAAgBABIgIAFIgEgCIgDgDIgDAFIgEAAIgDgFIgGAFIgEgIIgGAGIADAAIAAAEIgDAFQABADgJADIgCAAIgEgBIAAAAIgBAAQgDAAgCADQgCAGgIgGIgNADQgDgCAAgEIABgFQAAgHAIAAIADgBIAAAAIAAAAQACAAACADQABgEADAAIABAAIAAAAIAFACIACgCIABgBIAAAAQABgDAEAAIAAAAIAAAAIADABIABAAIAIADIgIgDIgBAAIgDgBIAAAAIAAAAQgEAAgBADIAAAAIgBABIgCACIgFgCIAAAAIgBAAQgDAAgBAEQgCgDgCAAIAAAAIAAAAIgDABQgIAAAAAHIgBAFQAAAEADACIANgDQAIAGACgGQACgDADAAIABAAIAAAAIAEABIACAAQAJgDgBgDIADgFIAAgEIgDAAIAGgGIAEAIIAGgFIADAFIAEAAIADgFIADADIAAACIgDACIAAADIAFAIIAIANQACAAACADIAIAHIADAAIAAADIAFACIACAAIAUAFIAFAAIADADIANAEIACADIASAFIAFAAIhJAZIgVgFQgrgEgMAZIgGAEIAAAGgAAgALIgMgKgAhCABIAPADIgPgDIAAgIIgDgFIADAFIAAAIgAhCABIgLAAgAgLgXIACgCIgCACIgFAAIAFAAgAgzgtIACACIAFADIAHAAIAGgFIgGAFIgHAAIgFgDIgCgCIgDgFgAhUgXIgKgCIgDAAIgHAAIgDAAIAAgFIAAgHIADgDIAAADQAAACAEACIASAGIAFAAIgFAAIgSgGQgEgCAAgCIAAgDIAAgDIAEAAIAIADIAIAFIACAAIAFAFIABABIgBACIABgCIgBgBIgFgFIgCAAIgIgFIACAAQAGAAAHAFQAJACgBAEIAAACIgIACIAAgCIAAACIAIgCIgDAEIADALQgCgIgNgDgAhcgog");
	this.shape_98.setTransform(147.1,125.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_98},{t:this.shape_97},{t:this.shape_96},{t:this.shape_95},{t:this.shape_94},{t:this.shape_93},{t:this.shape_92},{t:this.shape_91},{t:this.shape_90}]}).wait(1));

	// Layer 12
	this.shape_99 = new cjs.Shape();
	this.shape_99.graphics.f("rgba(0,0,0,0.302)").s().p("AomEwIhYAAIAAhCIh6hRIAAhTIgogrIAAgKIANAAIgDgFIAAgDIADgFIAFAAIgFgFIgHgDQgGgHAAgIQADgKAHgIIADgCIAFgDIgCAAQgLgUgPgtIgCgfIAogCQEjBCBXi2IgWgpIAjgPQAQAfAoACIKiAAQA3gFAGgZIAqASIgeAjQB8CuDhg6IAqACIgDAfIgEAMIgVAwIAGAFQAHAIAAAKQACAIgFAHIgCAAIgFAFIgRALIAHAAIAFAAIACAFIAAACIgCADIgEACIAQAAIAAALIgoAoIAABWIh5BOIAABCIyjAFg");
	this.shape_99.setTransform(215.1,99.9);

	this.timeline.addTween(cjs.Tween.get(this.shape_99).wait(1));

	// Layer 11
	this.shape_100 = new cjs.Shape();
	this.shape_100.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AIUBTIn2ACIgKAAIgFAAIgiAAIhsAAIiLADIhLAAIgmAAIgSAAIiBgDQgDgCgCAAIgKAZIRDgDIgFgPgAAUA+IH0AAIAHAAIAFgCQAPgFgKgXIAAgCIAXgKIANgIQAegXgXgrIyCAAQgZAwAoAXIAcANQgKAbAXAFIAIAAIINAAgAp1hhIgIgHIT7gDIgHAIIgUAZIACAHQASAhgKAZQgIAQgXANIgUAHIgCAAQgDAAAAADQAFAKAAAHIAAADIgFAKIgPAMIgDADIgHAFIgFADIgDAAAIcBQIAFAKAoTBTIgKgLQgKgCgDgIQgMgMAKgUIgUgIQgegPgKgSQgKgZAUgkIgDAAIgUgZITsgCAJjhKIzBAC");
	this.shape_100.setTransform(214,120.3);

	this.shape_101 = new cjs.Shape();
	this.shape_101.graphics.lf(["#FD9058","#F1AC50","#FEF995"],[0.004,0.561,1],-0.2,-11.2,-0.2,10.6).s().p("AIFA7In0AAIgFAAIoNAAIgIAAQgXgFAKgcIgbgNQgpgXAagwISBAAQAXArgeAXIgNAIIgXAKIAAACQAKAXgPAFIgFADg");
	this.shape_101.setTransform(214.2,120.6);

	this.shape_102 = new cjs.Shape();
	this.shape_102.graphics.f("#FFFFCC").s().p("AkoAKIA7gDIAUAAIARAAIAmAAIBMAAICJgCIBsAAIAkAAIAFAAIAKAAIgaACQhdAIj1AAgAjPgIQgKgIBEADIBEAAIDcAAQDegDhbAFQhbAFj3ADg");
	this.shape_102.setTransform(196,128.3);

	this.shape_103 = new cjs.Shape();
	this.shape_103.graphics.f("#F6CF6F").s().p("AjKANQgKgHBEACIEgAAQDegChbAFQhaAFj4ADgAiigHIhsAAIgfgFIA7gDIEggCQDegDhbAFIiDAFIiOADg");
	this.shape_103.setTransform(218.4,112.3);

	this.shape_104 = new cjs.Shape();
	this.shape_104.graphics.lf(["#F1AC50","#FEF995"],[0,1],0,-10.9,0,10.9).s().p("AoTBSIgKgKQgKgCgCgIQgNgMAKgVIgUgHQgfgPgJgSQgLgZAVgkIgDAAIgUgZITrgCIgUAZIzAACITAgCIADAHQARAhgKAZQgHAQgXAMIgUAIIgDAAQAAAAgBAAQAAAAAAABQgBAAAAAAQAAABAAAAQAFALAAAHIAAADIgFAKIgQAMIgCADIgIAFIgEACIgDAAIADAAIAEgCIAGAKIgNgIIANAIIAEAQIxCACgAncBaICOAFQD4AABbgHIAZgDIH2gDIn2ADIgKAAIgFAAIgiAAIhsAAIiLADIhMAAIglAAIgSAAIiBgDQgCgDgDAAQADAAACADICBADIgUAAgAmDBGICLAFQD4gCBagFQBbgGjbADIjfAAIhEAAIgdgBQgkAAAHAGgAovARIAcAMQgKAdAXAEIAHAAIIOAAIAFAAIHzAAIAIAAIAFgCQAPgFgKgXIAAgDIAXgKIANgHQAegXgXgrIyCAAQgZAwAoAXgAidhAICKAFQD5gDBagFQBbgFjeACIkhAAIgdAAQgkAAAIAGgABahhIkgACIg6ADIAfAFIBsAAIBBAAICOgCICEgGQA8gDhOAAIhyABgAp1hhIgIgIIT7gCIgIAIIzrACgAJ2hjg");
	this.shape_104.setTransform(214,120.3);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_104},{t:this.shape_103},{t:this.shape_102},{t:this.shape_101},{t:this.shape_100}]}).wait(1));

	// Layer 10
	this.shape_105 = new cjs.Shape();
	this.shape_105.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("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");
	this.shape_105.setTransform(215.1,99.7);

	this.shape_106 = new cjs.Shape();
	this.shape_106.graphics.f("#DC9F80").s().p("AgQAQIgDgcIAngDIAAAfg");
	this.shape_106.setTransform(135.7,87.8);

	this.shape_107 = new cjs.Shape();
	this.shape_107.graphics.f("#FD955B").s().p("AJmBQIAFAAIAPgHIAAgrIAKgNIANgmIBMhCIAABjIh3BOgArbAKIAAhjIBDA6IAPAwIAJANIAAAog");
	this.shape_107.setTransform(214.9,114.7);

	this.shape_108 = new cjs.Shape();
	this.shape_108.graphics.f("#DB6C37").s().p("AJmBnIB3hOIAYAAIh5BOgAp8BnIh5hRIAaAAIBbA9IAJAHIASANgAJmBdIg4AAIAXgIIA1AAIgPAIgAIxBOIAAgDIAAgPQAKgFAKgDIAAAhIgXAIgAoyBdIgXgKIAAgcIAUAFIAAADQgKAPANAKIAAAFgAplBdIgMAAIgGgDIgJgHIA3AAIAXAKgAqABTgAKegxIgDAAIA6gyIA9AAIAMAAIgpAoIAAgPIgYAAIhMBBgAL1hKIAdgZgArbhNIgaAAIAAASIgogrIBMAAIA5A1IgKAAIAKAeg");
	this.shape_108.setTransform(214.9,113.4);

	this.shape_109 = new cjs.Shape();
	this.shape_109.graphics.f("#FDB86F").s().p("ALdAzIAAhjIAZAAIAAAPIAABUgAr1AxIAAhSIAAgRIAaAAIAABjg");
	this.shape_109.setTransform(214.8,110.8);

	this.shape_110 = new cjs.Shape();
	this.shape_110.graphics.lf(["#F1AC50","#FD8B55"],[0,1],0.1,-43.3,0.1,13.4).s().p("Ap3BmIAGACIAMAAIAAAKgAJFBhIAAghQAXgKAHgNQAKgRgRgYIgDgFIAcgZIgZAAIADgIIAyAAIAKAAIADAAIgNAnIgNAoIghAAIAhAAIgKANIAAArgAqABeIAAgoIgJgNIAYAAIgYAAIgPgwIgKgfIAKAAIg5g0IAAgLIAtArIAWAUIArAAIAAAIIgjAAIAcAZIADAAQgVAaALAUQAJAPAfAKIAAAcgAqOgmIgKAAgAqJApgAJcgeIgFAAIAIgIIgDAIgAJHgeQALgKAKgIIgSASgApPgeIgggoIACACIAmAmgApjgeIAAgIIAKAIgAMShYIg9AAIAAgKIAKAAIgFgDIgDgCIAAgDIADgFIAFAAIAmAAIAHAAIAGAAIACAFIAAADIgCACIgFADIARAAIAAAKgAMNhiIguAAgAsdhaIAAgLIANAAIAEAAIAzAAIgzAAIgEAAIgDgFIAAgCIADgFIAEAAIAuAAIAFAAIACAFIAAACIgCAFIAIAAIAAALgArRhlg");
	this.shape_110.setTransform(214.9,112.3);

	this.shape_111 = new cjs.Shape();
	this.shape_111.graphics.f("#C55824").s().p("Ap2BpIAAhBIAWAAIAAgKIAzAAIAAgGQgNgJAKgPIAAgBIgUgFQgegLgKgOQgKgVAUgbIgDAAIgcgZIAkAAIAKAAIAKAAIAIAAIA8AAIAFAAIADAAIA6AAIAMAAIADAAIA6AAIAUAAIA9AAIARAAIA9AAIAXAAIA3AAIAjAAIA4AAIAtAAIAzAAIAhAAIA4AAIAWAAIA1AAIAXAAIA6AAIAUAAIA6AAIAXAAIA6AAIANAAIA6AAIAFAAIA/AAIACAAIANAAIAFAAIAZAAIgbAZIACAEQASAagKASQgIAMgXAKQgKACgKAGIAAANIAAACIgCAQIA3AAIAAAKIAXAAIAABBg");
	this.shape_111.setTransform(214.3,119.8);

	this.shape_112 = new cjs.Shape();
	this.shape_112.graphics.f("#DEA283").s().p("AgUAQIAAgfIApADIgDAcg");
	this.shape_112.setTransform(294.4,87.8);

	this.shape_113 = new cjs.Shape();
	this.shape_113.graphics.lf(["#B54A17","#FD8B55"],[0,1],-0.1,-6.5,-0.1,6.6).s().p("AJIBBIASgSIAWgUIAHgFIAAgCIADgKIAZgaIAGgFQAEgFAIAAQAHAAAIAHIACAAIADADIA4gwQgDAFAAAKIAFAKIAGAFIAEAFIANAFIAKgCIgRAMIgnAAIgEAAIgDAFIAAADIADACIAEADIgKAAIgRALIARgLIAAAIIg5AyIgKAAIAnglIgnAlIgzAAIgIAIgAKyAWQAAAAABAAQAAAAABAAQAAAAABgBQAAAAABgBIALgKIgLAKQgBABAAAAQgBABAAAAQgBAAAAAAQgBAAAAAAQgLAAgKgHIgCgFIgCAAIgGgKIAAgBIgBgIIABgHIgBAHIABAIIAAABIAGAKIACAAIACAFQAKAHALAAIAAAAgALBAKIADgLIgLgPIALAPgAIGBBIBEhKQgDAIADAGIAAACIAFAIIAFAHIAVAFIACAAIAFAAIgWAUQgLAIgKAKgAHHBBIAkgyQgDAHADAIIACACIAFAIIAFAFIAUAFIAHgFIgRAUgAGBBBIAjg1QgDAIADAHIACADIAFAHIAIAFIADADIAHAAQACACAFgCIAKgDQgHAFgFAIIgIAKgAEwBBIAog3QgCAHACAIIAAACIAFAIIACACQADAAADADQAHAFAKgDIALgCIgQAPIAAACIgIAIgADhBBIAeg3QAAAHADAFIACADIADAHIAFADIAFACQAKAFALAAIAHgCIgFAHIgFAIIgHAKgACWBBIAPg1QAAAIAFAFIACACIAGAFQACAAACADIAIACQAKADAHgDIADAAIAFgFIAAAFIgFAIIAAACQgCADgBAFIgCAAIAAACQgCADAAAFgABIBBIAHg1QAAAIAIAFIACACIAIAFIACAAIAFADIAFAAIADAAIARgDIAFAAIAAAFIgFAKIAAAFIAAADIgCAKgAgNBBIAAgrIAFAKQADAAADADIAIACIAKAAIAIAAQACgCAKgDIACAAIAEgFIgBAFIAAAFIgDAKIAAAIIAAAKgAhyBBIgCgKIgCgFIAAgDIgDgKIgDgFQADAAADADIAHACIADAAIAFAAIAHAAIAIgCIAKgDQAAAAAAgBQAAAAABgBQAAAAAAAAQABAAAAAAIADgDIAIgMIAHAygAjMBBIgFgKIgDgIIgCgHQACAAADACQAKADAKgDIAIgCIACgDIAIgFQAAAAAAgBQAAAAAAgBQAAAAABAAQAAAAABAAQAFgFAAgIIAPAwgAkfBBIgDgCIgDgIIgEgFIAAgDIgDgCIAHACQAKAAAIgFIADAAIAEgCIADgDIAGgFIAAgCIACgDIADgMIAbAygAluBBIgHgIIAAgCIgIgIIgCgCIAHACIAAADIANgDIADAAIAEgCIAGgFIAEgDIAAgCIADgDIAAgCIACgIIgCgHIAmAygAm8BBIgDAAIgHgKIgFgIIAIADQAHACAFgCIACgDIAGAAIAFgFIADgFIACgCQAAgBAAAAQAAgBABAAQAAAAAAgBQABAAAAAAIAAgPIAhAwgAoFBBIgDAAIgKgPIAIAFIAUgFIAFgFIAFgIQAAAAAAgBQAAAAABgBQAAAAAAAAQABAAAAAAIAAgDIAAgKIAfArgApJBBIgmgmIAHADQAKAAALgIIAIgFIAEgHIAAgDQADgHgDgGIA7BHgApbBBIgKgIIgrAAIgXgUIgRgRIARARIgtgpIgHAAIACgFIAAgCIgCgFIgFAAIguAAIgFgFIAKACQAIAAAHgHIACAAIAFgIIADgKIADgCIgDgNIA1AwQgCAFAAAFQgFAGAFAHIAAADIAFAFIAKACQAKAAAIgFIACgCIAFgDIAFgKIAAgCQAFgEgFgHIAaAcIACADIAAACIACADIAhAogAkuAtIACAAgAAsAZIgCACIACgCgALSgBg");
	this.shape_113.setTransform(215.1,102.6);

	this.shape_114 = new cjs.Shape();
	this.shape_114.graphics.f("#FED681").s().p("AoRA7IgFgCIgDgDIAAgCIgCgKIACgFQADgKAKgFIADgDQAHgCAIACIAKADIACACIAFAIIAAAKIAAACQAAAAgBAAQAAAAgBABQAAAAAAABQAAAAAAABIgFAHIgFAFIgUAFgAnDA7IgHgCIgDAAIgFgFIAAgDIgCgCIAAgFQAAgFACgDQADgKAKgFQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAIAPgDQAIADAFAFIAFAFIAAAPQAAAAgBAAQAAAAgBAAQAAABAAAAQAAABAAAAIgDADIgCAFIgGAFIgFAAIgCACIgGABIgHgBgAH4A2IgFgFIgFgHIgDgDQgCgHACgIIAFgFQADgFAHgDIAKAAQADADAFAAIACADQAKAFADAKQACAHgCAIIAAACIgIAFIgHAFgAl3A5IgHgCIgBgBIgFgFIAAgCIgCgFIAAgKQAFgKAKgFIADgDIAPgDQAHADAFAFIADAFIACAIIgCAHIAAADIgDACIAAADIgFACIgFAFIgFADIgCAAIgNACgAktA2IgBAAIgCgHIgDgFQAAgFAFgIQAFgKAKgFIADAAQAHgFAKACQAGAAAFAGIACAHIgCANIgDACIAAADIgFAFIgDACIgFADIgCAAQgIAFgKAAgAG+A2IgIAAIgCgCIgIgFIgFgIIgDgCQgCgIACgHIAFgFIALgIIARACIADADQAKAFACAKQADAIgDAHIAAADIgFAFIgKACIgEABIgDgBgAjQA0QgDgDgDAAIgCgCIgDgFIAAgDQAAgHAFgIQAIgKANgDIACAAQAKgCAIACIAHAIIADAIQAAAHgFAFQgBAAAAAAQgBAAAAABQAAAAgBABQAAAAAAABIgHAFIgDACIgHADIgKABIgKgBgAFlAxQgCgCgDAAIgCgDIgFgHIAAgDQgDgHADgIIACgFQAFgFAIAAIAFgDIAKADIACACQALAGACAKIADAFIgDAKIAAACQAAAAgBAAQAAAAgBABQAAAAAAABQAAAAAAABIgDACIgKADIgHAAQgGAAgFgDgAESAvIgFgDIgFgCIgDgIIgCgCQgDgFAAgIIADgIIAFgFIAFAAQAIgCAFACIAFADIACAAQAKAFAFAKQAFAIgCAHIAAADIgDAFIgCACIgIADQgKAAgKgFgAC/AvIgIgDQgCgCgDAAIgFgFIgCgDQgFgFAAgHIAAgIIAFgFQAAgDAFAAIARgCIADACIAHADQAIAFACAFQAIAIAAAHIAAADIgCAFIgDAAIgFAFIgDAAIgIABIgJgBgAARAvIgKAAIgJgDQgCgCgDAAIgFgKIAAgFQAAgIAFgCQAHgGAJgCIADAAQAMAAAKAFQAIADACAFIAAAFIAAAHIAAABIgFAHIgCAAQgKACgDADgAhmAvIgGAAIgCAAIgIgDQgCgCgDAAIAAgDIgCgHQAAgIAKgHQAFgGAKgCQADgDACAAIAFAAQAIgCAFACIAFADQAHACAAAGIAAAHIgHANIgDACQAAAAgBAAQAAAAgBABQAAAAAAABQAAAAAAABIgKACIgIADgABuAsIgFAAIgFgCIgDAAIgHgFIgDgDQgHgFAAgHIAAgIIACgFQADgDACAAQAIgFAKADIACAAQAIAAAIAFIAHAFQAIAIACAHIgCAIIgDAFIgFAAIgRACgApuAlIgDgDIgCgCIAAgDIgDgCQgCgFACgIIADgIIAMgIIADAAIAHgFIAIAAIAKADIADACIACADQADAIgDAIIAAACIgFAIIgIAFQgKAHgKAAgAJsAlIgDAAIgUgFIgFgIIgFgHIAAgDQgDgIADgGIAFgEQAAgBAAAAQAAgBAAAAQABgBAAAAQABAAAAAAQADgCAIAAQAHAAAIAFIACACIAIAGIAFAKIAAAFIgDAKIAAADIgHAFgAq3AdIgGgFIAAgCQgFgIAFgIQAAgFADgDIAKgHIADAAQAHgFAIAAQAHAAAFAFIADAHQAFAGgFAFIAAACIgFALIgFACIgDADQgHAFgKAAgAsSgMIgIgFQgFgHAAgIQADgKAHgIIADgCIAFgDIAKgCQAHgDAFAFIAIAFIADANIgDADIgDAKIgFAHIgCAAQgIAIgHAAgAL+gTIgFgFIgFgFIgFgLQAAgKACgFIAGgFQAHgFAFADIAPACIAFAFQAIAIAAAKQACAIgFAHIgCAAIgFAFIAAABIgKACIgNgFg");
	this.shape_114.setTransform(215,101.6);

	this.shape_115 = new cjs.Shape();
	this.shape_115.graphics.f("#C77659").s().p("AIBBiIASgVIAHgFIAAgCQADgHgDgIQgCgKgKgFIgDgCQgFgBgCgDIgKAAQgIADgCAFIgFAGIgkAyIgMAAIAHgLQAFgHAIgFIAFgFIAAgCQACgIgCgIQgDgKgKgEIgCgEIgSgCIgKAIIgFAFIgjA1IgXAAIAHgIIAAgDIAQgPIACgCQAAgBAAAAQABgBAAAAQAAAAABAAQAAAAABAAIAAgDIACgKIgCgFQgDgKgKgGIgDgCIgKgCIgFACQgHAAgFAGIgDAEIgoA4IgUAAIAHgLIAFgHIAFgIIADgCIACgFIAAgDQADgHgFgHQgFgKgKgGIgDAAIgFgCQgFgDgIADIgFAAIgFAEIgCAIIgeA4IgXAAQAAgFACgDIAAgDIADAAQAAgEACgDIAAgDIAFgHIAAgFIADAAIADgFIAAgCQAAgIgIgIQgDgEgHgGIgIgCIgCgDIgSADQgFAAAAACIgFAGIAAAHIgPA1IgXAAIADgLIAAgCIAAgFIAFgKIAAgFIACgFIADgIQgDgHgHgHIgIgGQgHgFgIAAIgDAAQgKgDgHAGQgDAAgCACIgDAGIAAAHIgHA1IghAAIAAgLIAAgHIACgKIAAgFQAAgEADgDIAAAAIAAgIIAAgFQgDgFgHgCQgKgGgNAAIgCAAQgJADgHAFQgFADAAAHIAAAFIAAArIguAAIgHgyIAAgIQAAgFgIgDIgFgCQgFgDgHADIgFAAQgDAAgCACQgLADgFAFQgKAHAAAIIADAHIAAADIACAFIADAKIAAADIACAEIADALIgjAAIgQgwIgCgIIgIgIQgHgCgKACIgDAAQgMADgIALQgFAHAAAHIAAADIACAFIADACIADAIIACAHIAFALIgXAAIgbgyIgDgIQgFgFgFgBQgKgCgIAGIgCAAQgKAEgFAKQgFAIAAAFIACAFIADAHIAAAAQACABADACIAAADIAFAEIACAJIADACIgSAAIgmgyIgCgGQgFgFgIgDIgPAEIgDACQgKAFgFAKIAAAKIADAFIAAACIAFAFIAAABIACACIAIAHIAAADIAIAIIgVAAIgggwIgFgFQgFgFgIgCIgPACQgBAAAAAAQgBAAAAABQAAAAgBAAQAAABAAAAQgKAFgCALQgDACAAAFIAAAFIADACIAAADIAFAFIACAAIAFAHIAIALIgNAAIgegrIgFgHIgDgDIgKgDQgHgCgIACIgCADQgKAFgDAKIgDAFIADAKIAAACIADADIAFADIAKAPIgFAAIg6hKIgDgEIgCgDIgLgDIgHAAIgIAGIgCAAIgNAKIgCAIQgDAHADAFIgageIgCgIQgFgFgIAAQgHAAgIAFIgCAAIgKAIIg1gvIgIgEQgFgGgIADIgKADIgCAAQgKgVgQgtIAmAAQADAHAFAGQACAHAFAFIAQAUIAgAZIAFADIAGADQAqAVBZAJIAPADIAKAAIBdACIASAAIAZAAIMrAAIA/AAIAPAAIANAAIAtgCIAogFIAKAAQAwgHAhgLIAIgCIAPgGQAAgBAAAAQAAgBAAAAQABAAAAAAQABAAAAAAIADAAIAXgQQAegWASgmIAoAAIgFANIgUAvIgPgCQgFgCgIAEIgFAGIg4AtIgCgCIgDAAQgHgHgIAAQgHAAgFAEIgFAFIgaAcIAAgEIgFgLIgHgHIgDgDQgHgFgIAAQgHAAgDACQgBAAAAABQgBAAAAAAQAAAAgBABQAAAAAAABIgFAFIhEBMg");
	this.shape_115.setTransform(215.1,99.3);

	this.shape_116 = new cjs.Shape();
	this.shape_116.graphics.f("#F3D7C4").s().p("AHkBJIgPAAIg/AAIsrAAIgZAAIgSAAIhdgDIgKAAIgPgCQhZgKgqgXIgGgDIgFgCIgggZIgQgTQgFgFgCgHQgFgFgDgIIAAghIAFAQIAIAMQAIASAMAMQAZAVAmASIAIACQAhANAwAFIA6AFIARAAIBeADIAMAAIAFAAIBoAAIARAAIA6AAIAXAAIASAAIBqAAIARAAIA5AAIA0AAIASAAICIAAIAQAAIBqAAIAPAAIA/AAIAwAAIAMAAIBWgFIANAAIAZgDQArgFAjgNIAFgCQBOgcAVhFIAAAhQgSAmgeAVIgXAPIgDAAQAAAAgBAAQAAAAgBAAQAAABAAAAQAAABAAAAIgPAGIgIACQghANgwAHIgKAAIgoAFIgtADg");
	this.shape_116.setTransform(215,93.5);

	this.shape_117 = new cjs.Shape();
	this.shape_117.graphics.lf(["#C57154","#ECBD9D","#FFFFFF"],[0,0.651,1],0,12.1,0,-12.1).s().p("AGnBdIgZgSIgCgCQgcgchJhUQgUgZAFAKIAKAUIAAADQgfgrgPguIAzAAQBqCCCXBqIhWAFgAGWB5Ig/AAIg9g6IAIAFQgXgYg9hdIgMgSIAFAPQgSgjgKghIA/AAQA6B/CiBygADeB5QhZh3gWh6IAyAAQAwB6B3B3gABGB5QgSh5ANh4IAjAAQAmB4BEB5gAAAB5Ig5AAQA4h0AIh9IAtAAIAADxgAi0B5QBThyAwh/IAtAAQAGCEhMBtgAjdB5Ig6AAQBghoBCiJIA3AAQgeCEhqBtgAmQB5QCGhgBbiRIAwAAQg1COh0BjgAn/B2QCxhTBbibIA3AAQgRAmgXAhQACgFgKAIQgKARg1A2IgUAWIAPgMIgmAhIgPAMIgPAKIgFAFIgoAagApKBxQgwgFghgNQD6gwBdinIBEAAQhOCgjCBOgAHfA6IgUgVQgPgKhghjQgUgZAFAKIAKAUQgVgZgRgcIBBAAQBZCJDRBOQgjANgrAFIgZADQgwgcgmgegAI6AyIgXgPIgCAAIgGgDIgogbIhJhAQgIgKgFAAQgRgXgPgcIAqAAQB8CsDhg4QgVBDhOAeQhLgcgcgPgAriA0QgMgMgIgSIgIgMIgFgOQEjBABYi0IA3AAQhTCqj/ArQgmgSgZgXgAFKAMIAcAZIACAFQgRgPgNgPgAHLgVIAMAHIAnAdg");
	this.shape_117.setTransform(215,86.7);

	this.shape_118 = new cjs.Shape();
	this.shape_118.graphics.f("#F6DFD0").s().p("AD6AzIgCgFIgcgZIgIgHIgEgGIgDgCIgSgVIgHgHIAAgDIgKgUQgFgKAUAZQBJBUAcAcgACtBIIAAgDIgQgPIgCgDIgDgFQgCgCgDAAIgFgKIgKgNIgMgSIgIgFIgIgNIgIgMIgEgIIAAgCIgDgGIgFgPIAMASQA9BdAXAYgAFdAzIg6gyIghgfIgZgcIgKgUQgFgKAUAZQBgBjAPAKIAUAVgAmZAiQA1g2ALgRQAKgIgDAFIgPAUIgMANQgGAIgZAXIgKAMIgIAIIgOAMgAGVAaIgFgCIgmgdIgNgHIgKgKIgDAAIgRgVQgNgKgDgHQAGAAAHAKIBKBAIAnAbg");
	this.shape_118.setTransform(226,85.8);

	this.shape_119 = new cjs.Shape();
	this.shape_119.graphics.f("#C9795C").s().p("AHHB5Qihhyg7h/IALAAQAPAuAeArIAHAHIASAXIADABIAEAFIAIAHQANAPARAPIAkAfIADACIAYASIArAcgAFJB5Qh3h3gwh6IAKAAQALAhARAjIADAGIAAACIAEAIIAIAMIAIANIAIAHIAMAQIAKANIAFAKQADAAACACIADAFIACADIAQAPIAAADIA8A6gADPB5QhEh5glh4IAKAAQAXB6BYB3gAA1B5IAAjxIAOAAQgNB4ARB5gAhJB5QBMhtgFiEIAKAAQgIB9g3B0gAjFB5QBqhtAeiEIAOAAQgxB/hTBygAknB5QB0hjA1iOIAKAAQhCCJhgBogAmUB5IgMAAIAogaIAGgFIAPgKIAOgMIAmghIAIgIIAKgMQAZgVAGgKIAMgNIAPgUQAXghARgmIANAAQhbCRiFBggAoPB2QDChOBPigIAMAAQhbCbixBTgAIqB0QiYhqhqiCIAKAAQARAcAVAZIAZAcIAhAhIA6AwIAUAQQAmAeAwAcgAF0h4IAKAAQAPAcARAXQADAHANAKIARAVIADAAIAKAKIAzAkIAFACIAYAPIAGADIACAAIAXAPQAcAPBLAcIgEACQjShOhYiJgAqiBdQEAgrBTiqIAMAAQhdCnj6Awg");
	this.shape_119.setTransform(214.9,86.7);

	this.shape_120 = new cjs.Shape();
	this.shape_120.graphics.f("#F2D4C0").s().p("AFqAbIgLAAIhBAAIgKAAIgzAAIgKAAIg/AAIgKAAIgyAAIgLAAIgjAAIgNAAIgrAAIgNAAIgtAAIgNAAIg3AAIgKAAIgwAAIgNAAIg3AAIgNAAIhEAAIgNAAIg3AAIgXgmIAjgPQAQAcAoADIKiAAQA4gFAFgYIArASIgfAhg");
	this.shape_120.setTransform(216.9,71.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_120},{t:this.shape_119},{t:this.shape_118},{t:this.shape_117},{t:this.shape_116},{t:this.shape_115},{t:this.shape_114},{t:this.shape_113},{t:this.shape_112},{t:this.shape_111},{t:this.shape_110},{t:this.shape_109},{t:this.shape_108},{t:this.shape_107},{t:this.shape_106},{t:this.shape_105}]}).wait(1));

	// Layer 9
	this.shape_121 = new cjs.Shape();
	this.shape_121.graphics.f().s("#462D28").ss(0.3,1,1).p("AAsghQgpAkguAf");
	this.shape_121.setTransform(332.7,256.6);

	this.shape_122 = new cjs.Shape();
	this.shape_122.graphics.lf(["#62402B","#B97E5B","#E1C7B8"],[0,0.627,0.984],-0.1,-4.8,-0.1,4.5).s().p("Ag1AuIAAhEIASgXIBZAAIgSAXIAAAjIAAAQIAAARg");
	this.shape_122.setTransform(319.1,275.8);

	this.shape_123 = new cjs.Shape();
	this.shape_123.graphics.lf(["#B9754D","#805746"],[0.004,1],4.2,0,-3.9,0).s().p("AgXg7IAAgKIADAAIA9g1IgcCJIgPAWIAABHIgmAPg");
	this.shape_123.setTransform(314,269.6);

	this.shape_124 = new cjs.Shape();
	this.shape_124.graphics.lf(["#432C1D","#62402B","#B97E5B"],[0.047,0.259,0.776],-19,-14.8,1,-14.9).s().p("AgJgGIASAAIAAANg");
	this.shape_124.setTransform(111.3,282.8);

	this.shape_125 = new cjs.Shape();
	this.shape_125.graphics.lf(["#62402B","#B97E5B","#E1C7B8"],[0,0.627,0.984],0,-4.8,0,4.5).s().p("AgkAuIAAhFIgQgWIBZAAIAQAWIAABFg");
	this.shape_125.setTransform(111.5,275.6);

	this.shape_126 = new cjs.Shape();
	this.shape_126.graphics.lf(["#B9754D","#805746"],[0.004,1],-4,-0.2,4.1,-0.2).s().p("AACBpIAAhGIgNgXIgciGIA+A1IACAeIAPCig");
	this.shape_126.setTransform(116.6,269.6);

	this.shape_127 = new cjs.Shape();
	this.shape_127.graphics.lf(["#7A533A","#A97150"],[0,0.706],-11.9,-4.8,-2.2,-8.7).s().p("AgDgOIACAAIAGAKIgDAIIgDALg");
	this.shape_127.setTransform(119.4,264.1);

	this.shape_128 = new cjs.Shape();
	this.shape_128.graphics.lf(["#B9754D","#805746"],[0.004,1],-6,2.2,9.3,2.2).s().p("Ag6A2IAAiBIBwBTIhwhlIAAgXIBtBlIAcBSIAPAWIAABHgAhdifIAZAAIBeBbIANANIACACg");
	this.shape_128.setTransform(98.3,264.2);

	this.shape_129 = new cjs.Shape();
	this.shape_129.graphics.lf(["#62402B","#B97E5B"],[0,0.776],12.5,3.8,-5.8,3.9).s().p("AAlA0IhdhZIAAgOIAvAqIAYAVIArAog");
	this.shape_129.setTransform(99.7,252);

	this.shape_130 = new cjs.Shape();
	this.shape_130.graphics.lf(["#62402B","#B97E5B","#E1C7B8"],[0,0.627,0.984],-0.5,-7.3,0.4,7.3).s().p("AgXBEIgbhSIgKgmIgDgCIgCgNIAPAAIAUAAIARAAIAzAAIAcCHg");
	this.shape_130.setTransform(108.6,264.1);

	this.shape_131 = new cjs.Shape();
	this.shape_131.graphics.lf(["#432C1D","#62402B","#B97E5B"],[0.047,0.259,0.776],11,18.2,1.9,-10.4).s().p("AABgcIgpAAIAAAdIhHhIIBOAAICHBoIAKAmgABWASIhdhZIAXAAIBgBZIgPAAIACANg");
	this.shape_131.setTransform(92.2,255.4);

	this.shape_132 = new cjs.Shape();
	this.shape_132.graphics.lf(["#8A593C","#B97E5B"],[0,1],-7.4,11,13.8,9).s().p("AgFAIQAFgIgFgMIANAPIgPAKg");
	this.shape_132.setTransform(106.1,246.5);

	this.shape_133 = new cjs.Shape();
	this.shape_133.graphics.f("#E3CCBD").s().p("AGVBUIgGgCIgeACQgHADgDAFIAAAAIgMAAIhCgDQgNgIgNABQgCgDgCAAQgSAAgNAFIgFAFIhyAAIgHAAIgFAAIgKAAIhwAAIgPAAIgDAAQgMgIgRADIgGAAIgKAFIg9AAIhIAAIgGAAIgjAAIh+AAIiAAAIiQAAIhcgHIgwgDIhcgKIgOgCIgCAAQgHgIgLAAIgPAAIg/gQQhYgUgtgtIgcgfQAeAfAoAUQBRAmC2ASQBRAICUgDIVYAAIBtACIB8gEQB5gLBggbQg9AYg0AOIgDAAIgSACQgBAAAAAAQgBAAAAABQAAAAAAAAQgBABAAAAIhYAKQkGALhRAAIhBACIgXAAQgNgDgKADIgKACIgfADIhGADgAy2hbIACAAIAXAfg");
	this.shape_133.setTransform(208.8,238.5);

	this.shape_134 = new cjs.Shape();
	this.shape_134.graphics.f("#5E3E2F").s().p("AQJBGIDKiGIAqAAIjPCGgAwwBEIjNiJIArAAICaBoIBbAAIAmASIhUAAIgUAAIAUAPgAQJA1IhdAAIAlgQIBbAAIgWAQg");
	this.shape_134.setTransform(215.9,276.7);

	this.shape_135 = new cjs.Shape();
	this.shape_135.graphics.lf(["#62402B","#B97E5B"],[0,0.776],-12.8,3.8,5.5,3.9).s().p("Ag3AyIBEg9IASgPIAZgXIAAAPIheBUg");
	this.shape_135.setTransform(332.2,252.2);

	this.shape_136 = new cjs.Shape();
	this.shape_136.graphics.lf(["#62402B","#B97E5B","#E1C7B8"],[0,0.627,0.984],0.3,-7.2,-0.6,7.4).s().p("AhCBFIAdiJIA6AAIAXAAIARAAIAFAAIgWBFIgUBEg");
	this.shape_136.setTransform(322.1,264.2);

	this.shape_137 = new cjs.Shape();
	this.shape_137.graphics.lf(["#B9754D","#805746"],[0.004,1],10,0,-9.9,0).s().p("AhjCIIAMAAIAXgQIAAgRIBlhRIhlBCIAAgmIASgWIAUhDIB+hxIAAAWIhvBoIBvhWIAACBIjHCJg");
	this.shape_137.setTransform(329.3,268.4);

	this.shape_138 = new cjs.Shape();
	this.shape_138.graphics.f("#4E2E00").s().p("ATTgwIAAASIhyBUgAzSgjIAAgSIByBlg");
	this.shape_138.setTransform(215.9,260.2);

	this.shape_139 = new cjs.Shape();
	this.shape_139.graphics.f("#633A00").s().p("AhjCHIBlhCIhlBRgABLiVIAZAAIg5AuQgpAmguAeg");
	this.shape_139.setTransform(332.8,263.7);

	this.shape_140 = new cjs.Shape();
	this.shape_140.graphics.lf(["#432C1D","#62402B","#B97E5B"],[0.047,0.259,0.776],-9.9,17.3,-0.9,-11.3).s().p("AhgAHIgFAAIBghUIAXAAIh3BwQAwgeApgkIA3guIBMAAIhEBHIAAgcIgrAAIh+Bwg");
	this.shape_140.setTransform(338.5,256.5);

	this.shape_141 = new cjs.Shape();
	this.shape_141.graphics.f("#BE8D78").s().p("As9A4IhEhHIANAFQASACAPgKIAKgHQgFAZAmA4QAeAwA8BBIgZADQgog6gug6gAiiCiIgDAAQgFg1gKg1IgchrQADAFAHADQAPAHAVgCIAPgFQgaAZALBKQAFAtAUBCgAkeCkIgDAAIgghsIgrhhIAMALQAPAFASgFIAPgIQgUAZAUBFQANAwAcA/gApwCkQgmg1gtg3QghgpgmgjIAPAFQAPACASgKIAHgHQgFAbArA7QAcAtA6A/IgNACIgMgCgALPCiQA1g9AhgtQAqg9gHgcIAKAIQAPAKASgDIAPgHIhMBRIhOBqIgDAAIgKABIgMgBgAntCiIhRhqIhJhRQAFAFAIACQARADAQgKIAKgIQgIAcAtA9QAfAtA1A9IgLABIgMgBgAC4A4QAMhMgbgcIAPAFQAUACAPgHIANgIQgSA4gNA4QgKAygCAzIgDAAIgZAFQASg9AFgtgAmGCfIgCAAQgZgyghg1Ig6hRIAMAFQAQACARgKIANgIQgPAfAjA9QAXArAoA8IgMACIgLgCgAEuA4QAXhKgUgcIANAFQAUAFAPgFIAMgHQgZAzgUA1QgUAygNAzIgZACQAcg6AMgtgAJYCaQAzg3AegrQAwhCgFgcIAHAIQASAHAPgCIAPgFIhQBWIhKBlgAgVCYIAAhgIAAh3QAFAMAKAFQANAKAXgCIAMgDQgbAXgPBKQgJArgFA6QgFAAgCgFgAHsCYIgXgDIBMhdQA1hFgIgeIAKAHQAPAIASgCIAMgFQgqArgmAwQgmAwghAwgAFwCVIA6hdQAohHgPgfIANAIQASAKAPgCIAMgIQgjAugeAwQgcAwgXAtIgCAAIgMACIgLgCgAuuA4Qgyg4g6g5QAFAEAHAAQASAAAPgKIAKgIQgKAmBTBZQAUAZAfAeIgaADgAN1BlIgWgFIAtgoQBihZgHgrIAHAKQAQAKARAAIADAAIAMgCIgrAjQgtAmgmApIgoAtgAQ4gFIARgXQAZghAAgWIgCgFIAFACIAFAIQAPAKASACIAKgCIhHA/gAxSgFIgrgpIgZgWIAPACQAPAAASgKIACAAIAFgHIAAAMQAAAUAXAhIAHANgATriDQARgaAAgOQAAAEAIAFIAIAFQAKAIAMAAIAPAAIgbASgA0piIIgHgFIAPACQAPAAAPgNIAIgKQAAAKAKAQg");
	this.shape_141.setTransform(216.2,257.9);

	this.shape_142 = new cjs.Shape();
	this.shape_142.graphics.f("#D8B8A5").s().p("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");
	this.shape_142.setTransform(216,252.4);

	this.shape_143 = new cjs.Shape();
	this.shape_143.graphics.f("#59382B").s().p("AsoBQQgmg3AFgaQAHgFAFgKIAAgDQAFgKgFgMIBbB5IA1BKIgCAKQgFAPgSAKQgFACgDADQg8hCgegwgAgLC4IgDgDQAFg6AJgrQAPhLAbgVIASgIIADgCIAKgKQgKA6gDA6IgCBjQgQAKgUACQgUAAgNgHgAiiBQQgLhLAagYQAMgFAIgFIACgCQANgKADgNIARCGIAKBUIgHAKQgSAKgUAFQgIAAgCACQgUhBgFgugAkwBQQgUhGAUgYIAPgMIADgDIAKgWIAmCDIAXBRIgFAIQgNAPgSAFIgMACQgcg/gNgwgAqtBQQgrg8AFgaIAKgNIADgFQAFgMgFgNIBgCBIAwBEQgDAGAAAFQgFAPgRAKIgIAFQg6g/gcgugALHC1QgRgKgGgRQgCgDAAgFIAwhCIBgiDQgFAMAFANIAAACIAKAQQAHAZgqA/QghAug1A8gACUC4QgUgDgPgMIgHgIIAKhRIAPiIQAFAMAKAIIAFACQAIAIAKACQAbAagMBOQgFAugSA8gAoqBQQgtg/AIgZIAKgQQAAAAAAgBQAAAAAAgBQABAAAAAAQABAAAAAAQAFgNgFgMIBgCDIAuBCIAAAIQgIARgPAKIgKAFQg1g8gfgugAD8C1QgUgHgKgNQgFgFAAgFIAUhHIAoiLQAAANAKAKIADACIAPANQAUAagXBLQgMAugcA6gAmuBQQgjg/APgcIANgPIACgDQAFgMgCgNIBJCGIAhA/IgDALQgKAPgSAKIgKAFQgog9gXgrgAJRCuQgSgKgFgQIgDgHIArg9IBliIQgFAMAFAKIAAAFIANAPQAFAagwBEQgeArgzA4gAHLCrQgSgKgFgSIAAgHIAmg4IBniNQgFAMAFAKIADAFQACAIAIAHQAIAdg1BGIhMBegAFmCpQgSgKgKgQQgCgFAAgFIAbg1IBMiQQgCANAHAMIADADQAFAKAHAFQAPAcgoBJIg6BeQgIgDgCgCgAuaBQQhThYAKgmIAMgPIAAgGQAGgMgGgKICGCpIAIANIAAAKQgIAPgPANIgHAFQgfgegUgagANXB0QgSgNgCgSIAAgFQgBAAAAgBQgBgBAAAAQAAgBABAAQAAgBABgBICaisQgFANAFAMIAAAFQACAIAIAIQAHArhiBYIgtApgAO8A/IA3g/IArgiIAKgHIAAgDQAFgHAAgKIArgwQgFAMADANIACAFIAIAPIACAFQAAAXgZAhIgRAUIg9AAIg/A1gAvMBGIhAg1Ig1AAIgHgMQgXgfAAgUIAAgNIAKgPIASgKIAcAmIAAACIACAAIAAADQADAAACACIBWBtgAzGhXIgRAAIAHgCIACgIIAAgFIgCgHIgKgDIgXAAQgKgPAAgKIAHgSIADgCQAAgNgFgNIBbBUIgIAPQgFAPAIANIAAACIAKAKgASnhUQgDgNgKgMIBdhUQgHANACANIADACQAAAKAHAIIAAAAIAAABQAAAOgRAZIgZAAQgGAAgCADQgFACAAAFIAAAFQAAADAFAFIAIACIgSAAIgZAXQAFgNgFgMg");
	this.shape_143.setTransform(216.2,255.5);

	this.shape_144 = new cjs.Shape();
	this.shape_144.graphics.lf(["#754600","#FF9900","#FFFF66","#FFFFFF"],[0.004,0.361,0.749,1],24.1,42,-27,-46.4).s().p("AL5B7I1YAAQiVADhQgIQi2gShRgoQgogUgegeIgDgDIgFgFIgXgcIgMgSIgNgZIAAg4IAKAaIANAWQAMAcAXAXQBMBoDFAWQB+AQDjgIIE2gDIFxADQC4AACVAFIERgDIGggbIBCgKIgPAFQhRAbiLANIh5AIg");
	this.shape_144.setTransform(208.4,232);

	this.shape_145 = new cjs.Shape();
	this.shape_145.graphics.lf(["#553624","#B97E5B","#E3CCBD"],[0.071,0.404,0.961],-3.3,13.7,-4,-29.3).s().p("AN5B2QAmgpAtgnIg3A/IAAAHIgDAAIAAAKgAvpB2IgIgKIhWhtIAFADIABABIAAAAQA6A6AyA5gANqhNQCLgNBRgbIACADQgBAAAAAAQgBAAAAAAQAAAAAAABQAAAAAAABQhhAch4AKIh8AFg");
	this.shape_145.setTransform(220.2,251.7);

	this.shape_146 = new cjs.Shape();
	this.shape_146.graphics.lf(["#754600","#FF9900","#FFFF66","#FFFFFF"],[0.004,0.361,0.749,1],127.4,-0.1,-127.3,-0.1).s().p("AAgByIlxgCIk2ACQjjAIh+gPQjFgXhMhoQBOBRCuAVIE0APIE3AAIF4AAIFzAAIE2AAIE0gPQECgfA1ipIAAA3QghBAgzAoIgrAZIgbANIhCAKImgAcIkRACQiVgFi4AAg");
	this.shape_146.setTransform(219,231.4);

	this.shape_147 = new cjs.Shape();
	this.shape_147.graphics.lf(["#4D3428","#956240"],[0,1],-5.1,19.5,-5.1,3.1).s().p("AM4DAIBLhTQAFgEAFgDIAAgFQAGgNgGgPQgFgPgPgKIgFAAQgPgIgNACQgNAAgHALIgFAKIhgCFIgjAAIBRhXIAJgLIAAgDQAFgMgCgPQgIgSgOgIQgDgCgDAAQgMgHgQACQgMACgHAIIgGANIhlCKIgtAAQAmgvAqgtIAAAAIABgBIAKgKQAAgBAAAAQAAgBABAAQAAAAAAAAQABAAAAAAQAGgNgGgPQgFgQgRgJIgDgDQgPgIgPADQgKADgIAHIgIANIhnCPIgeAAQAegwAkgvIAHgKIACgDQADgMgIgNQgJgSgSgKIgDAAIgHgFIAXAAIBBgCQBRAAEGgLIBYgKIgHAKIiaCvQgBAAAAABQAAAAAAABQAAABAAAAQAAABABAAgAFCDAQATg1Aag0IAFgNIAAgCQAAgNgLgNQgHgHgKgFIBGgDIAfgDQgFADgCAFIgGAKIhLCSgADLDAQAMg3ASg6IACgNIAAgCQgCgMgMgOQgGgEgHgDIBCADIAMAAQgFAAAAAEIgDAOIgoCMgAA+DAQACg6AKg8IADgMIAAgDIgDgFIgPgQQgOgGgRgBIByAAQgHACgDAIIAAANIgPCKgAhaDAIgSiIIAAgKQgCgKgLgFIAPAAIBwAAIAKAAQgQACgKALQgKAHAAAIIAAANIAAB4gAjtDAIgmiFIgDgNQgEgHgLgFIgJgDIBIAAIA9AAQgPACgNALQgMANgDAMIAAADIAGAMIAbBsgAlmDAIhJiIIgCgKQgIgKgKAAIgfACIgEADQgSALgIARQgHAMACANIADADQACAHAFAFIA6BTIghAAIhfiFIgIgKQgHgLgLAAQgPgCgPAIIgDAAQgRAKgFAPQgGAPAIANIAAAFIAKAHIBJBTIgoAAIhgiCIgFgKQgKgJgKgCQgPgCgNAHIgFAAQgRAKgGASQgFANAIAPIAAADIAKAKQAmAlAhAoIgfAAIhbh7IgFgNQgHgHgNgDQgPgDgPAJIgCACQgQAIgHARQgFAPAHANIAAACIAKAKIBEBJIgTAAIiGirIgGgMIAOACIBcAKIAwADIBcAHICQAAICAAAIB+AAIAjAAIgPADIgEAAQgTAHgMAPQgNANADAMIAAADIAFAKIArBigAxCAaIgPgRIgFgLQgKgIgKAAQgPAAgNAIIgFACIgPALIhbhRIgKgKQgKgGgKAAQgNAAgIAGQgWgegahSIBCAAIANAaIAMARIgCAAIAZAfIAFAFIADACIAcAfQAtAvBYAUIA/AOQgHADgIAFIgDACQgPAKgHARQgFAOAFAMgAQuAmQgEgRgPgMIgGgDQgMgGgMAAQA0gNA9gYQAAgBAAgBQAAAAABAAQAAgBAAAAQABAAAAAAIgCgDIAPgFIAcgMIArgaQAygoAhhCIBBAAQgEANgDANQgUA1gQAbQgJgHgNAAQgMgCgIAHIgKAIIheBRQgDgBgCAAIgFgCQgMgLgQAAQgMABgIAFIgHAIIgsAwg");
	this.shape_147.setTransform(216.1,244.3);

	this.shape_148 = new cjs.Shape();
	this.shape_148.graphics.f("#A06C4D").s().p("AUXAbIAAg1IBHAHIgGAugA1YAbIgFguIBHgHIAAA1g");
	this.shape_148.setTransform(216.1,222.2);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_148},{t:this.shape_147},{t:this.shape_146},{t:this.shape_145},{t:this.shape_144},{t:this.shape_143},{t:this.shape_142},{t:this.shape_141},{t:this.shape_140},{t:this.shape_139},{t:this.shape_138},{t:this.shape_137},{t:this.shape_136},{t:this.shape_135},{t:this.shape_134},{t:this.shape_133},{t:this.shape_132},{t:this.shape_131},{t:this.shape_130},{t:this.shape_129},{t:this.shape_128},{t:this.shape_127},{t:this.shape_126},{t:this.shape_125},{t:this.shape_124},{t:this.shape_123},{t:this.shape_122},{t:this.shape_121}]}).wait(1));

	// Layer 8
	this.shape_149 = new cjs.Shape();
	this.shape_149.graphics.f("#CCCCCC").s().p("AvoABIAAgBIfRAAIAAABg");
	this.shape_149.setTransform(215.2,262.2);

	this.shape_150 = new cjs.Shape();
	this.shape_150.graphics.f("#7D5840").s().p("AvOBQIAAifIFLAAICDAAIA/AAIWQAAIAACfgAvJAwIBlAAIAXhKIBLAAIAAA4IgyAAIAAgNIAmAAIAAgPIgwAAIAAArIBGAAIAAhWIhaAAIgXBJIhgAAgALSgdIAXBKIBqAAIAXhKIBMAAIAAA4IgzAAIAAgPIAmAAIAAgNIgwAAIAAApIBHAAIAAhUIhbAAIgXBHIhgAAIgWhHIhbAAIAABXIBHAAIAAgsIgrAAIAAAQIAjAAIAAAMIg1AAIAAg4gAF5gaIAUBHIBsAAIAXhHIBMAAIAAA1IgyAAIAAgMIAlAAIgDgPIgtAAIAAArIBHAAIAAhXIhbAAIgWBKIhgAAIgXhHIhbAAIAABUIBGAAIAAgrIgrAAIAAAPIAhAAIAAAPIgyAAIAAg4gAAcgaIAXBHIBtAAIAWhHIBJAAIAAA1IgyAAIAAgMIAmAAIAAgPIguAAIAAArIBHAAIAAhXIhaAAIgYBKIhiAAIgUhHIhZAAIAABUIBDAAIAAgrIgnAAIAAAPIAfAAIAAAPIgxAAIAAg4gAkzgaIAWBHIBsAAIAYhHIBJAAIAAA1IgzAAIAAgMIAmAAIAAgPIguAAIAAArIBEAAIAAhXIhYAAIgWBKIhjAAIgUhHIhbAAIAABUIBEAAIAAgrIgoAAIAAAPIAgAAIAAAPIgyAAIAAg4gAqOgaIAYBHIBqAAIAYhHIBKAAIAAA1IgyAAIAAgMIAlAAIAAgPIgtAAIAAArIBEAAIAAhXIhbAAIgXBKIhgAAIgWhHIhbAAIAABUIBHAAIAAgrIgrAAIAAAPIAjAAIAAAPIg1AAIAAg4g");
	this.shape_150.setTransform(215.1,272.1);

	this.shape_151 = new cjs.Shape();
	this.shape_151.graphics.f("#503829").s().p("AvoBgIAAi/IfRAAIAAC/gAvPBSIedAAIAAifI2QAAIg/AAIiDAAIlLAAgAvKAyIAAgQIBgAAIAXhJIBaAAIAABWIhGAAIAAgqIAwAAIAAAOIgmAAIAAANIAyAAIAAg4IhLAAIgXBKgALoAvIgXhJIhMAAIAAA3IA1AAIAAgMIgjAAIAAgRIArAAIAAAtIhHAAIAAhXIBbAAIAXBHIBgAAIAXhHIBaAAIAABVIhGAAIAAgrIAwAAIAAAPIgmAAIAAAOIAyAAIAAg3IhLAAIgXBJgAGMAvIgVhHIhLAAIAAA4IAyAAIAAgPIghAAIAAgPIArAAIAAArIhGAAIAAhUIBbAAIAWBHIBgAAIAXhKIBbAAIAABXIhHAAIAAgrIAtAAIADAPIgmAAIAAAMIAzAAIAAg1IhMAAIgXBHgAAyAvIgXhHIhHAAIAAA4IAxAAIAAgPIgfAAIAAgPIAmAAIAAArIhCAAIAAhUIBZAAIAUBHIBiAAIAXhKIBbAAIAABXIhHAAIAAgrIAuAAIAAAPIgmAAIAAAMIAyAAIAAg1IhJAAIgXBHgAkeAvIgXhHIhJAAIAAA4IAyAAIAAgPIggAAIAAgPIAoAAIAAArIhEAAIAAhUIBbAAIAUBHIBiAAIAXhKIBYAAIAABXIhEAAIAAgrIAuAAIAAAPIgmAAIAAAMIAyAAIAAg1IhJAAIgXBHgAp4AvIgXhHIhLAAIAAA4IA1AAIAAgPIgkAAIAAgPIArAAIAAArIhGAAIAAhUIBaAAIAXBHIBgAAIAXhKIBbAAIAABXIhFAAIAAgrIAuAAIAAAPIgmAAIAAAMIAzAAIAAg1IhKAAIgZBHg");
	this.shape_151.setTransform(215.2,271.9);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_151},{t:this.shape_150},{t:this.shape_149}]}).wait(1));

	// Layer 7
	this.shape_152 = new cjs.Shape();
	this.shape_152.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.1,-1.9,-0.1,1.6).s().p("AgKAQIgPAAQAXgBAFgeQAMAHAKADQgIAVgTAAIgIAAg");
	this.shape_152.setTransform(169,231.9);

	this.shape_153 = new cjs.Shape();
	this.shape_153.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-2.5,0,2.6).s().p("AgGAZQgUgXAUgaIAFADIAEAAIAOAAQgRAZAPAVg");
	this.shape_153.setTransform(166,235.8);

	this.shape_154 = new cjs.Shape();
	this.shape_154.graphics.lf(["#281B15","#C09383"],[0.012,1],-3.5,-0.1,3.6,-0.1).s().p("AACAIIgkgCQAKgFgCgGIAugCQACAIANAHg");
	this.shape_154.setTransform(193,220);

	this.shape_155 = new cjs.Shape();
	this.shape_155.graphics.lf(["#281B15","#C09383"],[0.012,1],-1.9,0.1,2,0.1).s().p("AgTAFIAAgFQATgIARAIIADAFQgUgFgTAFg");
	this.shape_155.setTransform(192.8,215);

	this.shape_156 = new cjs.Shape();
	this.shape_156.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-3,0,2.9,0).s().p("AgbAKQgDgIAFgFIAIgLIAAgCQASgFAUAFIAAACQAKAIgCANQAAAIgFACIguADg");
	this.shape_156.setTransform(192.6,217.4);

	this.shape_157 = new cjs.Shape();
	this.shape_157.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-2.3,0,2,0).s().p("AgSARIAAgFQgFgNAMgFQAAgHADgDIASAAIAAAKQANADgDAPIgCAFQgSgHgSAHg");
	this.shape_157.setTransform(192.7,213.1);

	this.shape_158 = new cjs.Shape();
	this.shape_158.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-2.5,0,2.6).s().p("AgNAZQANgUgQgaIARAAIABAAIAFgDQAUAagUAXg");
	this.shape_158.setTransform(222.8,235.6);

	this.shape_159 = new cjs.Shape();
	this.shape_159.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.1,-1.7,-0.1,1.8).s().p("AgKAQIgPAAQAWgBAGgeIAXAMQgJATgTAAIgIAAg");
	this.shape_159.setTransform(210.6,231.7);

	this.shape_160 = new cjs.Shape();
	this.shape_160.graphics.lf(["#281B15","#C09383"],[0.012,1],0.1,-2.5,0.1,2.6).s().p("AgIAZQgRgXARgaIAIADIAEAAIAOAAQgSAaAPAUg");
	this.shape_160.setTransform(207.6,235.6);

	this.shape_161 = new cjs.Shape();
	this.shape_161.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.6,-1.9,0.4,2).s().p("AgbgDIAXgMQAFAeAbABIgTAAIgJAAQgTAAgIgTg");
	this.shape_161.setTransform(220.1,231.7);

	this.shape_162 = new cjs.Shape();
	this.shape_162.graphics.lf(["#281B15","#C09383"],[0.012,1],3.6,0.1,-3.5,0.1).s().p("AgiAKQANgHAAgIQAagIAWAIQgCAFAKAIIgkACg");
	this.shape_162.setTransform(237.9,219.8);

	this.shape_163 = new cjs.Shape();
	this.shape_163.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],2.9,0.2,-3,0.2).s().p("AgZASQgDgEAAgIQgCgLAKgKQAUgFASAFIAAACIAIALQAFAGgDAHIgFAHQgWgHgaAHg");
	this.shape_163.setTransform(238.3,217.3);

	this.shape_164 = new cjs.Shape();
	this.shape_164.graphics.lf(["#281B15","#C09383"],[0.012,1],2,-0.1,-1.9,-0.1).s().p("AgTAGIADgIQAQgFAUAFQgDAFADADQgUgFgTAFg");
	this.shape_164.setTransform(238.2,215);

	this.shape_165 = new cjs.Shape();
	this.shape_165.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],2.2,0.1,-2.1,0.1).s().p("AgQAQIgDgDQgFgPAQgFIAAgIIAUAAIAAAIQANAHgFALIAAAFQgUgFgQAFg");
	this.shape_165.setTransform(238.2,213);

	this.shape_166 = new cjs.Shape();
	this.shape_166.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-2.5,0,2.6).s().p("AgQAZQAQgXgQgXIASgDIAFAAQAUAagUAXg");
	this.shape_166.setTransform(264.9,235.8);

	this.shape_167 = new cjs.Shape();
	this.shape_167.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.2,-1.7,0,1.8).s().p("AgcgEQAKgCANgKQAGAfAcAAIgVACIgFAAQgVAAgKgVg");
	this.shape_167.setTransform(262.3,231.8);

	this.shape_168 = new cjs.Shape();
	this.shape_168.graphics.lf(["#281B15","#C09383"],[0.012,1],3.6,0.1,-3.5,0.1).s().p("AgVgFQAZgIAVAIQAAAFAKAIIgkAAIghACQANgHAAgIg");
	this.shape_168.setTransform(277.8,219.8);

	this.shape_169 = new cjs.Shape();
	this.shape_169.graphics.lf(["#281B15","#C09383"],[0.012,1],2,-0.1,-1.9,-0.1).s().p("AgTAGIADgIQAQgFAUAFIgDAIQgRgFgTAFg");
	this.shape_169.setTransform(278.1,215);

	this.shape_170 = new cjs.Shape();
	this.shape_170.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],2.9,0.2,-2.9,0.2).s().p("AgYASIgDgMQgCgLAKgKQATgFAQAFIADACIAIALQACAGgCAHQgDAHgCAAQgVgHgZAHg");
	this.shape_170.setTransform(278.1,217.3);

	this.shape_171 = new cjs.Shape();
	this.shape_171.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],2.2,0.1,-2.1,0.1).s().p("AgQAQIgDgDQgFgPAPgFQgCgFACgDIASAAIADAIQANAHgFALIAAAFQgUgFgQAFg");
	this.shape_171.setTransform(278.1,213);

	this.shape_172 = new cjs.Shape();
	this.shape_172.graphics.lf(["#281B15","#C09383"],[0.012,1],0.1,-2.5,0.1,2.6).s().p("AgQAZQAQgXgQgXIAQgDIAHAAQASAagSAXg");
	this.shape_172.setTransform(304.8,235.8);

	this.shape_173 = new cjs.Shape();
	this.shape_173.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.2,-1.7,0.1,1.8).s().p("AgcgEQANgCAKgKQAGAfAcAAIgSACIgFAAQgWAAgMgVg");
	this.shape_173.setTransform(301.9,231.8);

	this.shape_174 = new cjs.Shape();
	this.shape_174.graphics.lf(["#281B15","#C09383"],[0.012,1],-3.5,0.9,3,-0.7).s().p("AACADIgCAAIgiADQAKgFgCgGIAtgCQADAIANAHg");
	this.shape_174.setTransform(114.6,220);

	this.shape_175 = new cjs.Shape();
	this.shape_175.graphics.lf(["#281B15","#C09383"],[0.012,1],-2,0.1,1.9,0.1).s().p("AgSAFIAAgFQATgIARAIQgCADACACQgRgFgTAFg");
	this.shape_175.setTransform(114.2,215);

	this.shape_176 = new cjs.Shape();
	this.shape_176.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-3,0,2.9,0).s().p("AgbAKQgDgIAFgFIAIgLIAAgCQASgFASAFIACACQAIAIAAANQAAAIgFACIguADg");
	this.shape_176.setTransform(114.1,217.4);

	this.shape_177 = new cjs.Shape();
	this.shape_177.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-2.2,0,2.1,0).s().p("AgSARIgCgFQgDgNANgFIAAgKIAVAAIAAAKQAMADgCAPIgDAFQgRgHgTAHg");
	this.shape_177.setTransform(114.2,213.1);

	this.shape_178 = new cjs.Shape();
	this.shape_178.graphics.f("#B07966").s().p("ALjAHIAAgNIExAAIAAANgAFUAHIAAgNIFXAAIgHANgAhyAHIgIgNIGWAAIgHANgAoEAHIgFgNIFYAAIAAANgAuEAHIgHgNIFKAAIAAANgAwTAHIAAgNIBQAAIAAANg");
	this.shape_178.setTransform(207.4,225.7);

	this.shape_179 = new cjs.Shape();
	this.shape_179.graphics.lf(["#281B15","#C09383"],[0.012,1],0.2,-1.9,-0.1,1.6).s().p("AgJAQIgRAAQAWgBAIgeQALAHAMADQgLAVgSAAIgHAAg");
	this.shape_179.setTransform(129.2,231.9);

	this.shape_180 = new cjs.Shape();
	this.shape_180.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-2.5,0,2.6).s().p("AgGAZQgUgXAUgaIAFADIABAAIARAAQgSAZAPAVg");
	this.shape_180.setTransform(126.4,235.8);

	this.shape_181 = new cjs.Shape();
	this.shape_181.graphics.f("#C09383").s().p("Ahyg2IGHAAIAACVImHADgAgGA8IAXAAIB6AAIAWAAQAUgXgUgbIgFACIgBAAQgbAAgHgfIgXANIgHAAIgDAAQgUACgKgCIgXgNQgIAfgWAAIgFAAIgGgCQgRAbARAXgAoEg2IFTAAIAACVIlTADgAmkA/IAXAAICDAAQAPgXgPgUIgDgDQggAIgIgaIgCgPIgZAMIgVgCQgKgDgNgHQgHAegWABIgGAAIgFgDQgUAcAUAXgAuEg2IFDAAIAACVIlDADgAswA/IAWAAICBAAQAQgXgQgUIAAgDQghAIgJgaIgDgPIgXAMIgUgCQgMgDgLgHQgKAegWABIgDAAIgFgDQgUAcAUAXgALjBfIAAiVIExAAIAACVgAM7A/ICBAAIAZAAQASgXgSgcIgHAAQgcAAgIgfQgKAKgNADQgKACgJgCQgLgDgNgKIgCAPQgKAaghgFQgPAXAPAXgAFUBfIAAiVIFQAAIAACVgAGoAbIgDAKQAAANAKANIB+AAIAZAAQAVgXgVgcIgEAAQgcAAgIgfQgNAKgKADQgKACgKgCQgMgDgLgKIgEAPQgLAagegFIgDAAgAwTBfIAAiVIBQAAIAACVgALjhFIAAgcIExAAIAAAcgAFUhFIAAgcIFXAAIAAAcgAh6hFIAAgZIGWgDIAAAcgAoJhFIAAgZIFYAAIAAAZgAuLhFIAAgZIFKAAIAAAZgAwThFIAAgZIBQAAIAAAZg");
	this.shape_181.setTransform(207.4,232);

	this.shape_182 = new cjs.Shape();
	this.shape_182.graphics.f("#794D3E").s().p("AsshwIAhAFIAAACIgQAOIAAAYIAIAPIAACYIgZANgAgchrIAfAAIADAAIAAACIgQAOIAAAYIAIAPIAACYIgaAKgAMUBiIAAiWIAIgPIAAgbIgSgNIACAAIAhgCIAADZgAmqhwIAhAFIACAAIAAACIgSAOIAAAYIAFAPIAACYIgWAIgAGFBiIAAiWIAIgPIAAgbIgSgNIADAAIAhAAIAADVg");
	this.shape_182.setTransform(196.2,231.8);

	this.shape_183 = new cjs.Shape();
	this.shape_183.graphics.lf(["#281B15","#C09383"],[0.012,1],-3.5,0,3.6,0).s().p("AACAFIgkACQALgFAAgFQATgKAaAHQAAAIANAIg");
	this.shape_183.setTransform(153.2,219.9);

	this.shape_184 = new cjs.Shape();
	this.shape_184.graphics.f("#DBC1B9").s().p("AhogEIFzgCIARALImWACgAoJAHIASgLIFGAAIAAALgAuLAHIAPgLIE7AAIAAALgAwTAHIAAgLIBQAAIAAALgALjAFIAAgLIExAAIAAALgAFUAFIAAgLIFFAAIASALg");
	this.shape_184.setTransform(207.4,221.7);

	this.shape_185 = new cjs.Shape();
	this.shape_185.graphics.f("#97604D").s().p("AtSBxIAAgPIAAiWIAAgPIAAgYIAAgOIAAgEIAjgDIADAAIAADhgAhBBuIAAgMIAAiWIAAgPIAAgYIAAgOIAAgEIAlACIAADZgAMtBsIAAjZIAmAAIAAACIAAANIAAAbIAAAPIAACWIAAAKgAnQBsIAAgKIAAiWIAAgPIAAgYIAAgOIAAgEIAmgDIAADcgAGfBqIAAjVIAlgCIAAACIAAANIAAAbIAAAPIAACWIAAAIg");
	this.shape_185.setTransform(196.2,231.8);

	this.shape_186 = new cjs.Shape();
	this.shape_186.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-2.8,0,3.1,0).s().p("AgbAKQgDgIAFgFIAIgLIADgCQAPgFAUAFIAAACQALAIgDANIgDAKQgagHgTAKg");
	this.shape_186.setTransform(152.7,217.4);

	this.shape_187 = new cjs.Shape();
	this.shape_187.graphics.lf(["#281B15","#C09383"],[0.012,1],-1.9,0.1,2,0.1).s().p("AgQAFIgCgFQASgIATAIQgCADACACQgTgFgQAFg");
	this.shape_187.setTransform(152.9,215);

	this.shape_188 = new cjs.Shape();
	this.shape_188.graphics.lf(["#C09383","#E2CEC7"],[0.012,1],-2.1,0,2.2,0).s().p("AgSARIAAgFQgGgNANgFQAAgHADgDIASAAIAAAKQAPADgFAPIAAAFQgUgHgSAHg");
	this.shape_188.setTransform(152.9,213.1);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_188},{t:this.shape_187},{t:this.shape_186},{t:this.shape_185},{t:this.shape_184},{t:this.shape_183},{t:this.shape_182},{t:this.shape_181},{t:this.shape_180},{t:this.shape_179},{t:this.shape_178},{t:this.shape_177},{t:this.shape_176},{t:this.shape_175},{t:this.shape_174},{t:this.shape_173},{t:this.shape_172},{t:this.shape_171},{t:this.shape_170},{t:this.shape_169},{t:this.shape_168},{t:this.shape_167},{t:this.shape_166},{t:this.shape_165},{t:this.shape_164},{t:this.shape_163},{t:this.shape_162},{t:this.shape_161},{t:this.shape_160},{t:this.shape_159},{t:this.shape_158},{t:this.shape_157},{t:this.shape_156},{t:this.shape_155},{t:this.shape_154},{t:this.shape_153},{t:this.shape_152}]}).wait(1));

	// Layer 6
	this.shape_189 = new cjs.Shape();
	this.shape_189.graphics.f("#E4D7A7").s().p("AgugaIBMgFQAXgHgKgNIAeAHQAwASAAAWQAAAYgwASQgrAPg4AAQhGghAygugAh3AEQAFgMAXAAIAFAHQAAADgIAKQgKALgKgBQgKABAFgTg");
	this.shape_189.setTransform(292.6,274.7);

	this.shape_190 = new cjs.Shape();
	this.shape_190.graphics.f("#D6BE73").s().p("ABJAzQhCAAgrgPQgwgRAAgYQAAgWAwgSIARgFQgFANAXAFIBCAFQgyAuBGAggAgGADQgFASAKAAQAIAAAKgKQAIgKAAgDIgFgIQgVAAgFANg");
	this.shape_190.setTransform(281.3,274.8);

	this.shape_191 = new cjs.Shape();
	this.shape_191.graphics.f("#D08A98").s().p("AgMnoIAVgoIAAQNQAKANgVAIg");
	this.shape_191.setTransform(296,218.4);

	this.shape_192 = new cjs.Shape();
	this.shape_192.graphics.lf(["#1C090C","#33131A","#692936"],[0,0.463,0.796],-8.2,-0.1,8.2,-0.1).s().p("Ag+JhQgXgFAFgNIAAvKIAAi3IAAgfQAcgNAbgHQASAKAVAIQACBlgXBnIBFAAIAKgRIAKP5IhOAFg");
	this.shape_192.setTransform(287.5,210.5);

	this.shape_193 = new cjs.Shape();
	this.shape_193.graphics.f("#3E2713").s().p("AgJAKQgegNgUgXIAeAAQAUAUAdANQAFAAACADIAhAMIgeAFQgXgHgQgKg");
	this.shape_193.setTransform(285.9,148);

	this.shape_194 = new cjs.Shape();
	this.shape_194.graphics.f("#392410").s().p("ABAA3Qh1gmg6hUIAoAAQAkA9CIAuIALACIAAAag");
	this.shape_194.setTransform(264.3,152);

	this.shape_195 = new cjs.Shape();
	this.shape_195.graphics.f("#5A3818").s().p("AgdhJIAdgFIAHghQgVgFgSgNIBVAAIgIAzIgSA/QgbBMg0BEQAZhngChjg");
	this.shape_195.setTransform(292,158.2);

	this.shape_196 = new cjs.Shape();
	this.shape_196.graphics.f("#5A391B").s().p("AhEAMQgDgCgEAAQgfgNgUgVIA6AAQARANAXAFIgHAfgABFgLQARgFANgIIAcAAQgXASglAIg");
	this.shape_196.setTransform(295.6,147.7);

	this.shape_197 = new cjs.Shape();
	this.shape_197.graphics.f("#A56931").s().p("AiOACIAMgDQh8grg6hbIAcAAQA7BWB2AmIAwALIAAgYIgLgCIAwgUIAAC2QhGg1gyhRgAAyB7QA1hEAbhMIASg/IAIgzIA3AAIgCANIgCAPQAlgKAXgSIAuAAQgfAphOAMQgPBRgkBCIgXApIgKARg");
	this.shape_197.setTransform(281.6,158.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_197},{t:this.shape_196},{t:this.shape_195},{t:this.shape_194},{t:this.shape_193},{t:this.shape_192},{t:this.shape_191},{t:this.shape_190},{t:this.shape_189}]}).wait(1));

	// Layer 5
	this.shape_198 = new cjs.Shape();
	this.shape_198.graphics.f("#E4D7A7").s().p("AhIAmQgwgSAAgYQAAgWAwgSIAegHQgKANAXAHIBMAFQAyAuhEAhQg6AAgrgPgABfAMQgIgKAAgDIAFgHQAXAAAFAMQAFATgKgBQgKABgKgLg");
	this.shape_198.setTransform(138.1,274.7);

	this.shape_199 = new cjs.Shape();
	this.shape_199.graphics.f("#D6BE73").s().p("AhTAzQBEgggyguIBBgFQAYgFgGgNIASAFQAwASAAAWQAAAYgwARQgsAPhBAAgAgZgCQAAADAHAKQAKAKAIAAQALAAgFgSQgGgNgUAAg");
	this.shape_199.setTransform(149.5,274.8);

	this.shape_200 = new cjs.Shape();
	this.shape_200.graphics.f("#D08A98").s().p("AgIH9IAAwNIAVAoIgKP6QgVgIAKgNg");
	this.shape_200.setTransform(134.7,218.4);

	this.shape_201 = new cjs.Shape();
	this.shape_201.graphics.lf(["#1C090C","#33131A","#692936"],[0,0.463,0.796],8.3,-0.1,-8.1,-0.1).s().p("AhRJhIAKv5IAKARIBFAAQgXhnAChlIAngSQAbAHAcANIAAAfIAAC3IAAPKQAFANgXAFIhCAFg");
	this.shape_201.setTransform(143.2,210.5);

	this.shape_202 = new cjs.Shape();
	this.shape_202.graphics.f("#5A3818").s().p("AgagPIgSg/IgHgzIBTAAQgRANgVAFIAGAhIAeAFQgDBjAaBnQg1hEgahMg");
	this.shape_202.setTransform(138.8,158.2);

	this.shape_203 = new cjs.Shape();
	this.shape_203.graphics.f("#3E2713").s().p("Ag7AWIAhgMQACgDAFAAQAdgNAUgUIAeAAQgUAXgeANIgnARg");
	this.shape_203.setTransform(144.8,148);

	this.shape_204 = new cjs.Shape();
	this.shape_204.graphics.f("#5A391B").s().p("AAcgGQAYgFARgNIA6AAQgUAVgeANQgFAAgDACIghANgAh+gYIAcAAQAMAIASAFIADANQgmgIgXgSg");
	this.shape_204.setTransform(135.1,147.7);

	this.shape_205 = new cjs.Shape();
	this.shape_205.graphics.f("#392410").s().p("AhvAqIALgCQCIguAkg9IAoAAQg6BUh1AmIgwANg");
	this.shape_205.setTransform(166.4,152);

	this.shape_206 = new cjs.Shape();
	this.shape_206.graphics.f("#A56931").s().p("AAWguIAxAUIgLACIAAAYIAwgLQB3gmA6hWIAbAAQg5Bbh8ArIANADQgzBRhHA1gAh4B7IgKgRIgWgpQgjhCgQhRQhOgMgegpIAtAAQAXASAmAKIgDgPIgDgNIA4AAIAHAzIASA/QAcBMA1BEg");
	this.shape_206.setTransform(149.1,158.8);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_206},{t:this.shape_205},{t:this.shape_204},{t:this.shape_203},{t:this.shape_202},{t:this.shape_201},{t:this.shape_200},{t:this.shape_199},{t:this.shape_198}]}).wait(1));

	// Layer 4
	this.shape_207 = new cjs.Shape();
	this.shape_207.graphics.lf(["#CB8989","#964734","#24110D"],[0,0.153,0.835],0.2,10.2,0.2,-10.1).s().p("AqzBlIAAjJIVnAAIAADJgAqTA0IAQAAIDtAAIAZAAIFJAAIAWAAIE3AAIAZAAIFDAAIAVAAIAAgtI0dAAg");
	this.shape_207.setTransform(215.4,163.4);

	this.shape_208 = new cjs.Shape();
	this.shape_208.graphics.f("#24110D").s().p("AJ6AWIAAgXIlDAAIAAAXIgZAAIAAgXIk3AAIAAAXIgWAAIAAgXIlJAAIAAAXIgZAAIAAgXIjtAAIAAAXIgQAAIAAgrIUdAAIAAArg");
	this.shape_208.setTransform(214.9,166.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_208},{t:this.shape_207}]}).wait(1));

	// Layer 3
	this.shape_209 = new cjs.Shape();
	this.shape_209.graphics.lf(["#723627","#24110D"],[0,0.835],119.1,-1.1,19.9,-1.1).s().p("AnuAYIAAguIPdAAIAAAug");
	this.shape_209.setTransform(215.1,172.1);

	this.shape_210 = new cjs.Shape();
	this.shape_210.graphics.lf(["#CB8989","#964734","#24110D"],[0,0.153,0.835],154.2,-2.4,-154,-2.4).s().p("AnuAfIAAg9IPdAAIAAA9gAF2gDIAAADIAAAGIAAAFIAFACIBYAAIADgCIADgFIAAgGIgDgDIgDgCIhYAAQgDAAgCACgAD1gDIgCADIAAAGIACAFIADACIBYAAIADgCIADgFIAAgGIgDgDIgDgCIhYAAQgBAAAAAAQgBAAAAABQAAAAAAAAQgBABAAAAgABwgDIAAAGIAAAIIAFACIBYAAIACgCIADgIIAAgDIgDgDIgCgCIhYAAgAgPgDIAAAGIAAAIIAFACIBXAAIACgCIACgIIAAgDQABgCgDgBIgCgCIhXAAgAiPgDQgDABAAACIAAADQAAAFADADIACACIBYAAIADgCIADgIIAAgDQAAgCgDgBIgDgCIhYAAQgBAAAAAAQgBAAAAABQAAAAAAAAQAAABAAAAgAkbgDQgCABAAACIAAADQAAAFACADIADACIBZAAIAEgCIAAgIIAAgGQgCgCgCAAIhZAAQgBAAAAAAQgBAAAAABQAAAAAAAAQgBABAAAAgAmlgFIAAAFIAAADIAAAFIACADIBbAAIADgDIACgFIAAgDIgCgFIgDgCIhbAAg");
	this.shape_210.setTransform(215.1,177.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_210},{t:this.shape_209}]}).wait(1));

	// Layer 2
	this.shape_211 = new cjs.Shape();
	this.shape_211.graphics.f("#D6BE73").s().p("AAzAkQgtAAgfgNQghgKAAgPQAAgSAhgKIANgFQgDAKAPADIAuAFQgjAcAwAZgAgCADQgDANAFgDQAGAAAFgFQAHgHAAgDIgFgFQgNAAgCAKg");
	this.shape_211.setTransform(261.3,262.7);

	this.shape_212 = new cjs.Shape();
	this.shape_212.graphics.f("#E4D7A7").s().p("AgggQIA1gFQAPgFgHgLIAWAIQAhAKAAASQAAAQghAJQgeANgpAAQgwgZAkgcgAhTAFQADgLAPAAIAFAFQAAADgIAHQgFAGgHAAIgCAAQgDAAACgKg");
	this.shape_212.setTransform(269.3,262.6);

	this.shape_213 = new cjs.Shape();
	this.shape_213.graphics.f("#D08A98").s().p("AgHlWIANgbIAALUQAHALgNAEg");
	this.shape_213.setTransform(271.6,223.2);

	this.shape_214 = new cjs.Shape();
	this.shape_214.graphics.f("#392410").s().p("AAsAlQhPgZgrg6IAfAAQAZArBdAfIAIACIAAARg");
	this.shape_214.setTransform(249.4,176.8);

	this.shape_215 = new cjs.Shape();
	this.shape_215.graphics.lf(["#1C090C","#33131A","#692936"],[0,0.463,0.796],-5.7,-0.1,5.8,-0.1).s().p("AgsGpQgPgDADgKIAAqlIAAiBIAAgWQAUgKAUgDIAXAKQAFBJgPBHIAuAAIAHgNIAILJIg4AFg");
	this.shape_215.setTransform(265.7,217.8);

	this.shape_216 = new cjs.Shape();
	this.shape_216.graphics.f("#3E2713").s().p("AATATIgXgKQgYgLgMgPIAUAAQAQAPARAIIAGAAIAWAKIgUADg");
	this.shape_216.setTransform(264.6,173.9);

	this.shape_217 = new cjs.Shape();
	this.shape_217.graphics.f("#5A3818").s().p("AgWgzIACAAIAUgDIAGgXQgQgFgMgHIA6AAIgFAjIgNArQgSA1gjAxQARhHgEhHg");
	this.shape_217.setTransform(268.9,181.1);

	this.shape_218 = new cjs.Shape();
	this.shape_218.graphics.f("#5A391B").s().p("AgwAHIgFAAQgUgIgPgPIAoAAQANAHASAFIgIAVgAAxgJIAUgHIAUAAQgPAMgcAGg");
	this.shape_218.setTransform(271.4,173.7);

	this.shape_219 = new cjs.Shape();
	this.shape_219.graphics.f("#A56931").s().p("AhiAAIAHgBQhVgfgpg+IASAAQArA8BRAZIAjAJIAAgQIgIgCIAhgNIAAB/Qgygmghg6gAAkBWQAjgxAVg1IAMgrIAFgjIAmAAIAAAHIgCANQAbgIAQgMIAeAAQgUAcg4AKQgKA3gZAuIgPAbIgIAOg");
	this.shape_219.setTransform(261.5,181.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_219},{t:this.shape_218},{t:this.shape_217},{t:this.shape_216},{t:this.shape_215},{t:this.shape_214},{t:this.shape_213},{t:this.shape_212},{t:this.shape_211}]}).wait(1));

	// Layer 1
	this.shape_220 = new cjs.Shape();
	this.shape_220.graphics.f("#D6BE73").s().p("Ag6AkQAwgZgjgcIAugFQAPgDgDgKIANAFQAhAKAAASQAAAPghAKQgfANgtAAgAgRgCQAAADAHAHQAFAFAGAAQAFADgDgNQgCgKgNAAg");
	this.shape_220.setTransform(169.2,262.7);

	this.shape_221 = new cjs.Shape();
	this.shape_221.graphics.f("#E4D7A7").s().p("AgyAYQghgJAAgQQAAgSAhgKQAKgFAMgDQgHALAPAFIA1AFQAkAcgwAZQgpAAgegNgABRAPQgHAAgFgGQgIgHAAgDIAFgFQAPAAADALQACAKgDAAIgCAAg");
	this.shape_221.setTransform(161.1,262.6);

	this.shape_222 = new cjs.Shape();
	this.shape_222.graphics.f("#D08A98").s().p("AgFFjIAArUQAFAMAIAPIgHLIQgNgEAHgLg");
	this.shape_222.setTransform(158.8,223.2);

	this.shape_223 = new cjs.Shape();
	this.shape_223.graphics.lf(["#1C090C","#33131A","#692936"],[0,0.463,0.796],5.8,-0.1,-5.7,-0.1).s().p("Ag5GpIAIrJIAHANIAuAAQgPhHAFhJIAXgKQAUADAUAKIAAAWIAACBIAAKlQADAKgPADIguAFg");
	this.shape_223.setTransform(164.8,217.8);

	this.shape_224 = new cjs.Shape();
	this.shape_224.graphics.f("#5A3818").s().p("AgSgLIgMgrIgFgjIA6AAQgMAHgQAFIAGAXIAUADIACAAQgEBHARBHQgjgxgTg1g");
	this.shape_224.setTransform(161.6,181.1);

	this.shape_225 = new cjs.Shape();
	this.shape_225.graphics.f("#3E2713").s().p("AgUATIgUgDIAWgKIAGAAQARgIAQgPIAUAAQgMAPgYALIgXAKg");
	this.shape_225.setTransform(165.9,173.9);

	this.shape_226 = new cjs.Shape();
	this.shape_226.graphics.f("#5A391B").s().p("AASgEQASgFANgHIAoAAQgPAPgUAIIgFAAIgXAKgAhYgQIAUAAIAUAHIADALQgcgGgPgMg");
	this.shape_226.setTransform(159.1,173.7);

	this.shape_227 = new cjs.Shape();
	this.shape_227.graphics.f("#392410").s().p("AhOAeIAIgCQBdgfAZgrIAfAAQgrA6hPAZIgjAKg");
	this.shape_227.setTransform(181.1,176.8);

	this.shape_228 = new cjs.Shape();
	this.shape_228.graphics.f("#A56931").s().p("AAQgfIAhANIgIACIAAAQIAjgJQBRgZArg8IASAAQgpA+hVAfIAHABQghA6gyAmgAhTBWIgIgOQgKgOgFgNQgZgugKg3Qg4gKgUgcIAeAAQAQAMAbAIIgCgNIAAgHIAmAAIAFAjIAMArQAVA1AjAxg");
	this.shape_228.setTransform(168.9,181.6);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_228},{t:this.shape_227},{t:this.shape_226},{t:this.shape_225},{t:this.shape_224},{t:this.shape_223},{t:this.shape_222},{t:this.shape_221},{t:this.shape_220}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0.8,23.4,449.7,350);


(lib.shape2 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.f("rgba(255,204,0,0.263)").s().p("AZpFxQgMgEgFgKIAAgIIACAAQAjAAANAOQgIAAAAAEQgCAIgPAGQAAgGgIgEgA6EkDQAUAAANAGQAWAAAKAIIgCAGQhXAAAYgUgA4jleIAAgKIADAAIAAAKgA5yl6IAKAAIAAAGg");
	this.shape.setTransform(-313.2,17.6);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.f("rgba(255,204,0,0.141)").s().p("AgBgBIADAAIAAADg");
	this.shape_1.setTransform(97.9,60.8);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.f("rgba(255,255,255,0.412)").s().p("AqYC6QgEgOgVgEQgRgIgEgKIhmAEIgZgEQgggIAJAMIglAAQgFgEgFAAQgCAAAAgEIsVglQgFgKgLAAQgVgIgaAAQADgGgDAAQADgIgFgGIACgIIAAgEIAXAEIAIAEIAHAAQBSAEBcgIIAvgEIA0gGIBIgIIADAAIEagSQAAgGADAAIAEAAIADgEIAxAAIAAgEIAJAAQAMAIAKgIQAKgFAMAAQCwAABfgSQCIAGA+gYQAEgEAGAAIAAAAIAAgEIJbgSQArgOA2gKQAHAAADgEQAKgEAFgGIBXgJQAAgBAAgBQAAAAABgBQAAAAAAgBQABAAABAAQACgGAFAAID4gIIACgEIAUgKIJ3gcIAFgEQAJgEALAAIBWAAQAAgBABgBQAAgBAAAAQAAgBABAAQAAAAABAAIAvAAIAjAEQAAgBAAgBQAAgBABAAQAAgBAAAAQABAAABAAQAEgGADAAIADAAIFOgOIAAAKIAAAOIAAAIQgiAGgXAIQglASgeAYIgDAAIggAEIgCAEIAAAFQgjAGgWAIIgoAOQiQgIiBAMQgMAGgPAAQgeAAgRAIIgDAAIi4AcQAFAEgFAAQgTAEgNAGQgRAEAcACIDQAPIACAEQASAAgBAKQAIAAAAAEQgIAAgCAEQgKAAgKAGQgCAAgFAEQgBAAAAAAQgBAAAAABQAAAAAAABQgBABAAABIgiAAIgDAAQgRAGgSAAIgEAEIgGAAIgCAEIhUAEQgHAAgIAGIkOASQgBAEgFAAIgUAEQgCAAAAAGIhLAAIgDAAIgEAEQgCAAAAAFQidAAhoAYIjbAMQiJAGhhAWQiQAMiSAAQh4AAh5gIg");
	this.shape_2.setTransform(148.5,64.3);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.f("rgba(255,255,255,0.373)").s().p("A0TJ/QDfgIhjAOIgFAAQg6AJgeAAQgmAAAHgPgA/CJxIgFAAQAigEAcAEIAAAGgAIYHjQBtgEgwAIIgCAFQgkAFgRAAQgcAAAWgOgAKUHfIAAgGIBhgMQAHAAAAAEQAPAOg5gGIgFAGIgXAEgAMaHHQBrgEA0gWIACAAIAAAAIAvAAIAcAIQAAAEgGAAQgWAKglgKQACAGgCAAQgDAEgJAAIifAKgA/UF6QABAAAAAAQABABAAAAQAAABABAAQAAABAAABIgDAFgEhIsADjIAAgKIAAgcIAyAGIAAAEIAAAcQgNAGgMAAQgNAAgMgGgEhJgAC9QgPgOgggFQAUgEAMAEQAMAFAIAGQAIAAACAIgEhMwACGIBlAEIgFAGQgoAJgYAAQgiAAACgTgEhNHACCQgMgKAAgIQARgGACAGQAQAIAAAOQgSAAgFgEgEBOQABwQAtgOgSgcQA5gFgtgSIgRAAQgqgSAUgSQAegKAsAAQAAgBAAAAQABAAAAAAQAAgBABAAQAAAAABAAQAOgGALAAIAACJQgYACgTAAQg9AAACgUgEhO2ABeIBAAAIgFAEQgXAGgOAAQgSAAgEgKgEhP1AA3QALgEAAAEQARAMgDAAQgCAAgXgMgAiHA3IAngFIgTAFgAhNAvIgTADQAKgCAJgBgAgmApQgKAAgFAGIAAgGIAjgIIASAEIgEAEIgEAAIgKAGgAA2AlIggAAIgDAAIADgEQACgEAFAAIAyAAIgCAEIgUAEgAn+AhIgWgEQAtAAAYAIgAB3AdIACgGIAUgEIgDAKQgEgGgPAGgAC1ATIAxgEQAAAEgPAAIgnAEgAEhALIAIAAQAGAAgDAEQgEgEgHAAgAFEALQAPgGAQAAIgGAGgAMBgZIgPAAICrgIIAFAEIAAAAIgKAAIgCAAQg7AKgoAAQgfAAgTgGgA4LhCIADgEIAEAEgEAzEgBQIACgKQALgEAMAAQgDAKgPAEgEAwCgBiIAUAAIgbAEQAFgEACAAgEAwggBmQAKgKAUgEQgPAEgKAKIgHACgEA0qgBwIAMgEIAAAEgEA1PgB4QAMgGAPAAIgEAGgEAxjgCGIATgGIgTAOgEA6ZgC1IgFAAIgFAAQAcgKAWgEIAMAAQgFAEgKAAIgWAKgEAsDgC1IASgEIgGAEgEAsXgC5IgCAAIACAAgEA8VgDHIA7gGIgHACIgvAEgEA+8gDZIAMgCIgFACgEBBQgD+QADAAAEgDIgEADgEBDqgEsIAUAAIgUAEQgCAEgKAAgEBFXgE+QAIgGAKAAIAFAAQgKAAgKAGgEgyogKNIAAAEQAPAEgUAAg");
	this.shape_3.setTransform(5.1,64.1);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.f("rgba(255,255,255,0.302)").s().p("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");
	this.shape_4.setTransform(128.9,76.3);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.f("rgba(255,255,255,0.2)").s().p("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");
	this.shape_5.setTransform(-3.3,-11.7);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape}]}).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-508.4,-129.6,1024.5,259.2);


(lib.shape1 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.shape = new cjs.Shape();
	this.shape.graphics.lf(["#68D6FF","#FFFFFF"],[0,0.878],-7.4,-179.8,3.2,139.5).s().p("EhOSAcYMAAAg4vMCclAAAMAAAA4vg");
	this.shape.setTransform(501.2,181.6);

	this.timeline.addTween(cjs.Tween.get(this.shape).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0,0,1002.4,363.3);


(lib.üì_y = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.üì_NI = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.üì_ = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.üì_嬟 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.text19复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_嬟复制("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(1.9,5.4,64.4,65);


(lib.text17复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_NI复制("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(1.6,6.2,66.1,62);


(lib.text15复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_y复制("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(3,10.2,64.7,62);


(lib.text11复制 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_复制("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(7.1,1.9,53.6,69);


(lib.sprite22 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.shape21("synched",0);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0,0,84.4,11.4);


(lib.text19 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_嬟("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.text17 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_NI("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.text15 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_y("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.text11 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.üì_("synched",0);
	this.instance.setTransform(0,60,0.068,0.068);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(1));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = null;


(lib.sprite49 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 4
	this.instance = new lib.shape41("synched",0);

	this.instance_1 = new lib.shape42("synched",0);

	this.instance_2 = new lib.shape43("synched",0);

	this.instance_3 = new lib.shape44("synched",0);

	this.instance_4 = new lib.shape45("synched",0);

	this.instance_5 = new lib.shape46("synched",0);

	this.instance_6 = new lib.shape47("synched",0);

	this.instance_7 = new lib.shape48("synched",0);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.instance}]}).to({state:[{t:this.instance_1}]},41).to({state:[{t:this.instance_2}]},3).to({state:[{t:this.instance_3}]},3).to({state:[{t:this.instance_2}]},16).to({state:[{t:this.instance_4}]},3).to({state:[{t:this.instance}]},4).to({state:[{t:this.instance_5}]},3).to({state:[{t:this.instance}]},3).to({state:[{t:this.instance_6}]},56).to({state:[{t:this.instance_2}]},3).to({state:[{t:this.instance_3}]},3).to({state:[{t:this.instance_2}]},24).to({state:[{t:this.instance_7}]},3).to({state:[{t:this.instance}]},4).to({state:[{t:this.instance_5}]},3).wait(3));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-163.2,-170.9,231.2,304.2);


(lib.sprite33 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 1
	this.instance = new lib.shape29复制("synched",0);
	this.instance.setTransform(-39.1,200.4);

	this.instance_1 = new lib.shape30复制2("synched",0);
	this.instance_1.setTransform(-54.9,208);

	this.instance_2 = new lib.shape31("synched",0);
	this.instance_2.setTransform(-54.3,217.2);

	this.instance_3 = new lib.shape32("synched",0);
	this.instance_3.setTransform(-74.6,231.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.instance}]}).to({state:[{t:this.instance_1}]},2).to({state:[{t:this.instance_2}]},2).to({state:[{t:this.instance_3}]},2).to({state:[]},2).wait(2));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-113.5,115,149.1,171.3);


(lib.sprite8 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 36
	this.instance = new lib.shape4("synched",0);
	this.instance.setTransform(632.9,403.8,0.575,0.575);
	this.instance._off = true;

	this.instance_1 = new lib.shape5("synched",0);
	this.instance_1.setTransform(616.3,395,0.567,0.567);
	this.instance_1._off = true;

	this.instance_2 = new lib.shape6("synched",0);
	this.instance_2.setTransform(583.6,377.3,0.552,0.552);
	this.instance_2._off = true;

	this.instance_3 = new lib.shape7("synched",0);
	this.instance_3.setTransform(551.5,360.2,0.537,0.537);
	this.instance_3._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(4).to({_off:false},0).to({_off:true},1).wait(6).to({_off:false,scaleX:0.52,scaleY:0.52,x:520.1,y:343.2},0).to({scaleX:0.52,scaleY:0.52,x:504.5,y:335},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.47,scaleY:0.47,x:400.8,y:279.2},0).to({scaleX:0.46,scaleY:0.46,x:386.5,y:271.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.42,scaleY:0.42,x:291.8,y:220.9},0).to({scaleX:0.41,scaleY:0.41,x:279,y:214},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.37,scaleY:0.37,x:193.2,y:168.1},0).to({scaleX:0.36,scaleY:0.36,x:181.7,y:161.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.33,scaleY:0.33,x:105.1,y:120.7},0).to({scaleX:0.32,scaleY:0.32,x:94.7,y:115.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:27.2,y:78.9},0).to({scaleX:0.29,scaleY:0.29,x:18.3,y:74.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:-40.2,y:42.8},0).to({scaleX:0.26,scaleY:0.26,x:-47.9,y:38.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:-97.4,y:12.1},0).to({scaleX:0.23,scaleY:0.23,x:-103.7,y:8.7},1).to({_off:true},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(5).to({_off:false},0).to({scaleX:0.56,scaleY:0.56,x:599.9,y:386.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.51,scaleY:0.51,x:489.3,y:326.7},0).to({scaleX:0.5,scaleY:0.5,x:474.1,y:318.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.45,scaleY:0.45,x:372.5,y:264.2},0).to({scaleX:0.45,scaleY:0.45,x:358.7,y:256.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.4,scaleY:0.4,x:266.3,y:207.1},0).to({scaleX:0.4,scaleY:0.4,x:253.6,y:200.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:170.2,y:155.7},0).to({scaleX:0.35,scaleY:0.35,x:159,y:149.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:84.6,y:109.7},0).to({scaleX:0.31,scaleY:0.31,x:74.7,y:104.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:9.3,y:69.3},0).to({scaleX:0.28,scaleY:0.28,x:0.7,y:64.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:-55.5,y:34.6},0).to({scaleX:0.25,scaleY:0.25,x:-62.8,y:30.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:-110,y:5.4},0).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_2).wait(7).to({_off:false},0).to({scaleX:0.54,scaleY:0.54,x:567.5,y:368.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.49,scaleY:0.49,x:459.1,y:310.6},0).to({scaleX:0.49,scaleY:0.49,x:444.3,y:302.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.44,scaleY:0.44,x:345,y:249.4},0).to({scaleX:0.43,scaleY:0.43,x:331.5,y:242.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:241.2,y:193.7},0).to({scaleX:0.39,scaleY:0.39,x:229,y:187.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:147.9,y:143.6},0).to({scaleX:0.34,scaleY:0.34,x:136.9,y:137.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.31,scaleY:0.31,x:64.8,y:99.1},0).to({scaleX:0.3,scaleY:0.3,x:55.2,y:94},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:-7.8,y:60.2},0).to({scaleX:0.27,scaleY:0.27,x:-16.2,y:55.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:-70.1,y:26.8},0).to({scaleX:0.24,scaleY:0.24,x:-77.1,y:22.9},1).to({_off:true},1).wait(5));
	this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(9).to({_off:false},0).to({scaleX:0.53,scaleY:0.53,x:535.7,y:351.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.48,scaleY:0.48,x:429.6,y:294.8},0).to({scaleX:0.47,scaleY:0.47,x:415.1,y:287},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.43,scaleY:0.43,x:318.1,y:235},0).to({scaleX:0.42,scaleY:0.42,x:304.8,y:227.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.38,scaleY:0.38,x:216.9,y:180.7},0).to({scaleX:0.37,scaleY:0.37,x:204.9,y:174.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.34,scaleY:0.34,x:126.1,y:131.9},0).to({scaleX:0.33,scaleY:0.33,x:115.6,y:126.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:45.7,y:88.9},0).to({scaleX:0.3,scaleY:0.3,x:36.3,y:83.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:-24.3,y:51.3},0).to({scaleX:0.26,scaleY:0.26,x:-32.5,y:47},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:-84.1,y:19.4},0).to({scaleX:0.24,scaleY:0.24,x:-90.8,y:15.7},1).to({_off:true},1).wait(3));

	// Layer 35
	this.instance_4 = new lib.shape4("synched",0);
	this.instance_4.setTransform(821.7,367.7,0.54,0.54);
	this.instance_4._off = true;

	this.instance_5 = new lib.shape5("synched",0);
	this.instance_5.setTransform(798.3,357.1,0.53,0.53);
	this.instance_5._off = true;

	this.instance_6 = new lib.shape6("synched",0);
	this.instance_6.setTransform(752.6,336.3,0.511,0.511);
	this.instance_6._off = true;

	this.instance_7 = new lib.shape7("synched",0);
	this.instance_7.setTransform(707.6,316,0.493,0.493);
	this.instance_7._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_4).wait(2).to({_off:false},0).to({_off:true},1).wait(6).to({_off:false,scaleX:0.48,scaleY:0.48,x:663.8,y:296.1},0).to({scaleX:0.47,scaleY:0.47,x:642.2,y:286.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:498.6,y:221.1},0).to({scaleX:0.4,scaleY:0.4,x:479,y:212.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:349.6,y:153.7},0).to({scaleX:0.34,scaleY:0.34,x:332.3,y:145.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:217.1,y:93.5},0).to({scaleX:0.28,scaleY:0.28,x:201.7,y:86.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:101,y:40.8},0).to({scaleX:0.24,scaleY:0.24,x:87.6,y:34.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:1.1,y:-4.4},0).to({scaleX:0.2,scaleY:0.2,x:-10.2,y:-9.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:-82.4,y:-42.4},0).to({scaleX:0.16,scaleY:0.16,x:-91.8,y:-46.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.14,scaleY:0.14,x:-149.7,y:-72.8},0).to({scaleX:0.14,scaleY:0.14,x:-157,y:-76.1},1).to({_off:true},1).wait(3));
	this.timeline.addTween(cjs.Tween.get(this.instance_5).wait(3).to({_off:false},0).to({scaleX:0.52,scaleY:0.52,x:775.3,y:346.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.46,scaleY:0.46,x:620.9,y:276.6},0).to({scaleX:0.45,scaleY:0.45,x:599.9,y:267.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:459.9,y:203.6},0).to({scaleX:0.38,scaleY:0.38,x:440.9,y:194.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.33,scaleY:0.33,x:315.1,y:137.9},0).to({scaleX:0.32,scaleY:0.32,x:298.1,y:130.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:186.5,y:79.7},0).to({scaleX:0.27,scaleY:0.27,x:171.7,y:72.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:74.5,y:28.8},0).to({scaleX:0.23,scaleY:0.23,x:61.6,y:23},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.19,scaleY:0.19,x:-21.4,y:-14.6},0).to({scaleX:0.19,scaleY:0.19,x:-32.2,y:-19.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.16,scaleY:0.16,x:-100.8,y:-50.6},0).to({scaleX:0.16,scaleY:0.16,x:-109.5,y:-54.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:-164,y:-79.3},0).to({scaleX:0.13,scaleY:0.13,x:-170.6,y:-82.3},1).to({_off:true},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_6).wait(5).to({_off:false},0).to({scaleX:0.5,scaleY:0.5,x:730,y:326},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.44,scaleY:0.44,x:579.1,y:257.6},0).to({scaleX:0.43,scaleY:0.43,x:558.5,y:248.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.38,scaleY:0.38,x:422.1,y:186.5},0).to({scaleX:0.37,scaleY:0.37,x:403.6,y:178.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:281.3,y:122.7},0).to({scaleX:0.31,scaleY:0.31,x:265,y:115.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:157,y:66.3},0).to({scaleX:0.26,scaleY:0.26,x:142.6,y:59.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.22,scaleY:0.22,x:49,y:17.3},0).to({scaleX:0.22,scaleY:0.22,x:36.5,y:11.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:-42.7,y:-24.4},0).to({scaleX:0.18,scaleY:0.18,x:-53,y:-29},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.15,scaleY:0.15,x:-118.1,y:-58.5},0).to({scaleX:0.15,scaleY:0.15,x:-126.4,y:-62.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:-177.2,y:-85.3},0).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_7).wait(7).to({_off:false},0).to({scaleX:0.48,scaleY:0.48,x:685.6,y:305.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.42,scaleY:0.42,x:538.4,y:239.2},0).to({scaleX:0.42,scaleY:0.42,x:518.3,y:230.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:385.4,y:169.8},0).to({scaleX:0.35,scaleY:0.35,x:367.5,y:161.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:248.9,y:107.8},0).to({scaleX:0.3,scaleY:0.3,x:232.8,y:100.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:128.4,y:53.3},0).to({scaleX:0.25,scaleY:0.25,x:114.5,y:47},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.21,scaleY:0.21,x:24.5,y:6.2},0).to({scaleX:0.21,scaleY:0.21,x:12.6,y:0.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:-63.1,y:-33.5},0).to({scaleX:0.17,scaleY:0.17,x:-73,y:-38},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.15,scaleY:0.15,x:-134.4,y:-65.8},0).to({scaleX:0.14,scaleY:0.14,x:-142.1,y:-69.3},1).to({_off:true},1).wait(5));

	// Layer 34
	this.instance_8 = new lib.shape5("synched",0);
	this.instance_8.setTransform(746.4,332.6,0.533,0.533);
	this.instance_8._off = true;

	this.instance_9 = new lib.shape6("synched",0);
	this.instance_9.setTransform(712,317.2,0.52,0.52);
	this.instance_9._off = true;

	this.instance_10 = new lib.shape7("synched",0);
	this.instance_10.setTransform(678.1,302,0.508,0.508);
	this.instance_10._off = true;

	this.instance_11 = new lib.shape4("synched",0);
	this.instance_11.setTransform(645,287.1,0.496,0.496);
	this.instance_11._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_8).wait(1).to({_off:false},0).to({scaleX:0.53,scaleY:0.53,x:729.2,y:324.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.48,scaleY:0.48,x:612.4,y:272.4},0).to({scaleX:0.48,scaleY:0.48,x:596.2,y:265.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.44,scaleY:0.44,x:488.1,y:216.5},0).to({scaleX:0.43,scaleY:0.43,x:473.1,y:209.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.4,scaleY:0.4,x:373.3,y:165},0).to({scaleX:0.39,scaleY:0.39,x:359.7,y:158.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:268.3,y:117.8},0).to({scaleX:0.35,scaleY:0.35,x:255.8,y:112.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:172.9,y:75},0).to({scaleX:0.32,scaleY:0.32,x:161.7,y:70},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:87.2,y:36.6},0).to({scaleX:0.28,scaleY:0.28,x:77.2,y:32.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:11.2,y:2.4},0).to({scaleX:0.26,scaleY:0.26,x:2.4,y:-1.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:-55.2,y:-27.4},0).to({scaleX:0.23,scaleY:0.23,x:-62.8,y:-30.8},1).to({_off:true},1).wait(3));
	this.timeline.addTween(cjs.Tween.get(this.instance_9).wait(3).to({_off:false},0).to({scaleX:0.51,scaleY:0.51,x:695.1,y:309.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.47,scaleY:0.47,x:580.4,y:258.1},0).to({scaleX:0.47,scaleY:0.47,x:564.6,y:250.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.43,scaleY:0.43,x:458.4,y:203.2},0).to({scaleX:0.42,scaleY:0.42,x:443.9,y:196.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:346.2,y:152.8},0).to({scaleX:0.38,scaleY:0.38,x:332.8,y:146.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:243.5,y:106.6},0).to({scaleX:0.34,scaleY:0.34,x:231.3,y:101.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.31,scaleY:0.31,x:150.7,y:65},0).to({scaleX:0.31,scaleY:0.31,x:139.6,y:60.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:67.2,y:27.6},0).to({scaleX:0.28,scaleY:0.28,x:57.6,y:23.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:-6.4,y:-5.5},0).to({scaleX:0.25,scaleY:0.25,x:-14.8,y:-9.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:-70.3,y:-34.2},0).to({scaleX:0.23,scaleY:0.23,x:-77.6,y:-37.5},1).to({_off:true},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_10).wait(5).to({_off:false},0).to({scaleX:0.5,scaleY:0.5,x:661.5,y:294.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.46,scaleY:0.46,x:549,y:243.9},0).to({scaleX:0.45,scaleY:0.45,x:533.6,y:237},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.42,scaleY:0.42,x:429.5,y:190.2},0).to({scaleX:0.41,scaleY:0.41,x:415.2,y:183.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.38,scaleY:0.38,x:319.6,y:140.9},0).to({scaleX:0.37,scaleY:0.37,x:306.6,y:135},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.34,scaleY:0.34,x:219.4,y:95.9},0).to({scaleX:0.33,scaleY:0.33,x:207.5,y:90.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:128.8,y:55.2},0).to({scaleX:0.3,scaleY:0.3,x:118.2,y:50.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:48,y:18.9},0).to({scaleX:0.27,scaleY:0.27,x:38.5,y:14.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:-23.2,y:-13},0).to({scaleX:0.25,scaleY:0.25,x:-31.4,y:-16.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:-84.8,y:-40.6},0).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_11).wait(7).to({_off:false},0).to({scaleX:0.49,scaleY:0.49,x:628.6,y:279.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.45,scaleY:0.45,x:518.2,y:230.1},0).to({scaleX:0.44,scaleY:0.44,x:503.1,y:223.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:401.1,y:177.5},0).to({scaleX:0.4,scaleY:0.4,x:387.1,y:171.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.37,scaleY:0.37,x:293.6,y:129.3},0).to({scaleX:0.36,scaleY:0.36,x:280.9,y:123.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.33,scaleY:0.33,x:195.9,y:85.3},0).to({scaleX:0.33,scaleY:0.33,x:184.4,y:80.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:107.8,y:45.7},0).to({scaleX:0.29,scaleY:0.29,x:97.4,y:41.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:29.3,y:10.5},0).to({scaleX:0.26,scaleY:0.26,x:20.1,y:6.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:-39.5,y:-20.3},0).to({scaleX:0.24,scaleY:0.24,x:-47.4,y:-23.9},1).to({_off:true},1).wait(5));

	// Layer 33
	this.instance_12 = new lib.shape5("synched",0);
	this.instance_12.setTransform(794.4,376.4,0.529,0.529);
	this.instance_12._off = true;

	this.instance_13 = new lib.shape6("synched",0);
	this.instance_13.setTransform(751.3,355.5,0.507,0.507);
	this.instance_13._off = true;

	this.instance_14 = new lib.shape7("synched",0);
	this.instance_14.setTransform(709.3,335,0.486,0.486);
	this.instance_14._off = true;

	this.instance_15 = new lib.shape4("synched",0);
	this.instance_15.setTransform(668.2,315,0.466,0.466);
	this.instance_15._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_12).wait(2).to({_off:false},0).to({scaleX:0.52,scaleY:0.52,x:772.7,y:365.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.45,scaleY:0.45,x:628.1,y:295.4},0).to({scaleX:0.44,scaleY:0.44,x:608.5,y:285.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.37,scaleY:0.37,x:477.2,y:221.9},0).to({scaleX:0.36,scaleY:0.36,x:459.4,y:213.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:341.6,y:155.8},0).to({scaleX:0.29,scaleY:0.29,x:325.8,y:148.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:221.1,y:97.2},0).to({scaleX:0.24,scaleY:0.24,x:207.3,y:90.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.19,scaleY:0.19,x:116.2,y:46},0).to({scaleX:0.18,scaleY:0.18,x:104,y:40.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.14,scaleY:0.14,x:26.3,y:2.4},0).to({scaleX:0.14,scaleY:0.14,x:16.2,y:-2.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.11,scaleY:0.11,x:-48.2,y:-34},0).to({scaleX:0.1,scaleY:0.1,x:-56.4,y:-38},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.08,scaleY:0.08,x:-107.4,y:-62.9},0).to({scaleX:0.07,scaleY:0.07,x:-113.8,y:-66},1).to({_off:true},1).wait(2));
	this.timeline.addTween(cjs.Tween.get(this.instance_13).wait(4).to({_off:false},0).to({scaleX:0.5,scaleY:0.5,x:730.3,y:345.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.43,scaleY:0.43,x:589,y:276.3},0).to({scaleX:0.42,scaleY:0.42,x:569.8,y:267},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:441.9,y:204.7},0).to({scaleX:0.34,scaleY:0.34,x:424.6,y:196.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:310,y:140.5},0).to({scaleX:0.28,scaleY:0.28,x:294.6,y:133},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:193.6,y:83.7},0).to({scaleX:0.22,scaleY:0.22,x:180,y:77.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:92.2,y:34.4},0).to({scaleX:0.17,scaleY:0.17,x:80.6,y:28.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:6.2,y:-7.5},0).to({scaleX:0.13,scaleY:0.13,x:-3.5,y:-12.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.1,scaleY:0.1,x:-64.4,y:-41.9},0).to({scaleX:0.1,scaleY:0.1,x:-72.2,y:-45.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.07,scaleY:0.07,x:-119.8,y:-69},0).to({scaleX:0.07,scaleY:0.07,x:-125.7,y:-71.7},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_14).wait(6).to({_off:false},0).to({scaleX:0.48,scaleY:0.48,x:688.7,y:324.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:550.8,y:257.7},0).to({scaleX:0.4,scaleY:0.4,x:532,y:248.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.34,scaleY:0.34,x:407.5,y:188},0).to({scaleX:0.33,scaleY:0.33,x:390.6,y:179.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:279.5,y:125.7},0).to({scaleX:0.26,scaleY:0.26,x:264.5,y:118.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.22,scaleY:0.22,x:166.7,y:70.8},0).to({scaleX:0.21,scaleY:0.21,x:153.7,y:64.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:69.4,y:23.2},0).to({scaleX:0.16,scaleY:0.16,x:58.2,y:17.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:-12.8,y:-16.8},0).to({scaleX:0.12,scaleY:0.12,x:-22.1,y:-21.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.09,scaleY:0.09,x:-79.8,y:-49.4},0).to({scaleX:0.09,scaleY:0.09,x:-87,y:-52.9},1).to({_off:true},1).wait(6));
	this.timeline.addTween(cjs.Tween.get(this.instance_15).wait(8).to({_off:false},0).to({scaleX:0.46,scaleY:0.46,x:648,y:305.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:513.5,y:239.6},0).to({scaleX:0.38,scaleY:0.38,x:495.3,y:230.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:374.1,y:171.7},0).to({scaleX:0.31,scaleY:0.31,x:357.6,y:163.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:249.9,y:111.2},0).to({scaleX:0.25,scaleY:0.25,x:235.4,y:104.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:140.9,y:58.2},0).to({scaleX:0.2,scaleY:0.2,x:128.4,y:52.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.16,scaleY:0.16,x:47.3,y:12.5},0).to({scaleX:0.15,scaleY:0.15,x:36.7,y:7.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.12,scaleY:0.12,x:-31,y:-25.6},0).to({scaleX:0.11,scaleY:0.11,x:-39.7,y:-29.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.08,scaleY:0.08,x:-94.1,y:-56.3},0).to({scaleX:0.08,scaleY:0.08,x:-100.9,y:-59.6},1).to({_off:true},1).wait(4));

	// Layer 32
	this.instance_16 = new lib.shape4("synched",0);
	this.instance_16.setTransform(851.6,370.8,0.54,0.54);
	this.instance_16._off = true;

	this.instance_17 = new lib.shape5("synched",0);
	this.instance_17.setTransform(831.7,361.7,0.531,0.531);
	this.instance_17._off = true;

	this.instance_18 = new lib.shape6("synched",0);
	this.instance_18.setTransform(792.2,343.8,0.516,0.516);
	this.instance_18._off = true;

	this.instance_19 = new lib.shape7("synched",0);
	this.instance_19.setTransform(753.5,326,0.5,0.5);
	this.instance_19._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_16).wait(3).to({_off:false},0).to({_off:true},1).wait(6).to({_off:false,scaleX:0.49,scaleY:0.49,x:715.5,y:308.8},0).to({scaleX:0.48,scaleY:0.48,x:696.7,y:300.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.43,scaleY:0.43,x:570.6,y:242.8},0).to({scaleX:0.42,scaleY:0.42,x:553.3,y:234.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.37,scaleY:0.37,x:437.2,y:182},0).to({scaleX:0.37,scaleY:0.37,x:421.4,y:174.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:315.3,y:126.4},0).to({scaleX:0.32,scaleY:0.32,x:300.8,y:119.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:204.7,y:76.1},0).to({scaleX:0.27,scaleY:0.27,x:191.8,y:70.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:105.7,y:31},0).to({scaleX:0.24,scaleY:0.24,x:94.1,y:25.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:18.1,y:-8.9},0).to({scaleX:0.2,scaleY:0.2,x:8,y:-13.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:-58,y:-43.6},0).to({scaleX:0.17,scaleY:0.17,x:-66.7,y:-47.6},1).to({_off:true},1).wait(2));
	this.timeline.addTween(cjs.Tween.get(this.instance_17).wait(4).to({_off:false},0).to({scaleX:0.52,scaleY:0.52,x:811.8,y:352.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.47,scaleY:0.47,x:678.1,y:291.7},0).to({scaleX:0.46,scaleY:0.46,x:659.8,y:283.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:536.2,y:227},0).to({scaleX:0.41,scaleY:0.41,x:519.3,y:219.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:405.6,y:167.6},0).to({scaleX:0.35,scaleY:0.35,x:390.2,y:160.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.31,scaleY:0.31,x:286.5,y:113.3},0).to({scaleX:0.31,scaleY:0.31,x:272.5,y:107},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:179,y:64.2},0).to({scaleX:0.26,scaleY:0.26,x:166.3,y:58.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:82.7,y:20.5},0).to({scaleX:0.23,scaleY:0.23,x:71.6,y:15.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:-2,y:-18.1},0).to({scaleX:0.19,scaleY:0.19,x:-11.8,y:-22.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:-75.2,y:-51.6},0).to({scaleX:0.16,scaleY:0.16,x:-83.6,y:-55.3},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_18).wait(6).to({_off:false},0).to({scaleX:0.51,scaleY:0.51,x:772.8,y:334.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.46,scaleY:0.46,x:641.6,y:275.2},0).to({scaleX:0.45,scaleY:0.45,x:623.6,y:267},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.4,scaleY:0.4,x:502.5,y:211.7},0).to({scaleX:0.39,scaleY:0.39,x:485.9,y:204.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:374.8,y:153.6},0).to({scaleX:0.34,scaleY:0.34,x:359.6,y:146.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:258.6,y:100.6},0).to({scaleX:0.3,scaleY:0.3,x:244.8,y:94.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:153.8,y:52.8},0).to({scaleX:0.25,scaleY:0.25,x:141.5,y:47.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.22,scaleY:0.22,x:60.6,y:10.3},0).to({scaleX:0.22,scaleY:0.22,x:49.6,y:5.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.19,scaleY:0.19,x:-21.3,y:-27},0).to({scaleX:0.19,scaleY:0.19,x:-30.9,y:-31.2},1).to({_off:true},1).wait(6));
	this.timeline.addTween(cjs.Tween.get(this.instance_19).wait(8).to({_off:false},0).to({scaleX:0.49,scaleY:0.49,x:734.3,y:317.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.44,scaleY:0.44,x:605.8,y:258.8},0).to({scaleX:0.43,scaleY:0.43,x:588.1,y:250.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:469.5,y:196.7},0).to({scaleX:0.38,scaleY:0.38,x:453.2,y:189.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.34,scaleY:0.34,x:344.7,y:139.8},0).to({scaleX:0.33,scaleY:0.33,x:329.9,y:133.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:231.3,y:88.2},0).to({scaleX:0.28,scaleY:0.28,x:217.9,y:82.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:129.4,y:41.7},0).to({scaleX:0.24,scaleY:0.24,x:117.5,y:36.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.21,scaleY:0.21,x:38.9,y:0.6},0).to({scaleX:0.21,scaleY:0.21,x:28.4,y:-4.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:-40,y:-35.5},0).to({scaleX:0.18,scaleY:0.18,x:-49.1,y:-39.6},1).to({_off:true},1).wait(4));

	// Layer 31
	this.instance_20 = new lib.shape4("synched",0);
	this.instance_20.setTransform(918.5,465.9,0.54,0.54);
	this.instance_20._off = true;

	this.instance_21 = new lib.shape5("synched",0);
	this.instance_21.setTransform(878.7,446,0.522,0.522);
	this.instance_21._off = true;

	this.instance_22 = new lib.shape6("synched",0);
	this.instance_22.setTransform(839.4,426.4,0.505,0.505);
	this.instance_22._off = true;

	this.instance_23 = new lib.shape7("synched",0);
	this.instance_23.setTransform(801,407.3,0.488,0.488);
	this.instance_23._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_20).wait(2).to({_off:false},0).to({scaleX:0.53,scaleY:0.53,x:898.5,y:455.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.47,scaleY:0.47,x:763.2,y:388.5},0).to({scaleX:0.46,scaleY:0.46,x:744.6,y:379.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:619.5,y:316.7},0).to({scaleX:0.4,scaleY:0.4,x:602.4,y:308.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:487.4,y:250.9},0).to({scaleX:0.34,scaleY:0.34,x:471.5,y:243.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:366.8,y:190.8},0).to({scaleX:0.29,scaleY:0.29,x:352.5,y:183.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:257.7,y:136.4},0).to({scaleX:0.24,scaleY:0.24,x:244.8,y:130},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.21,scaleY:0.21,x:160.2,y:87.8},0).to({scaleX:0.2,scaleY:0.2,x:148.8,y:82.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:74.3,y:45},0).to({scaleX:0.16,scaleY:0.16,x:64.4,y:40.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.14,scaleY:0.14,x:0,y:7.9},0).to({scaleX:0.13,scaleY:0.13,x:-8.5,y:3.7},1).to({_off:true},1).wait(2));
	this.timeline.addTween(cjs.Tween.get(this.instance_21).wait(4).to({_off:false},0).to({scaleX:0.51,scaleY:0.51,x:858.9,y:436.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.46,scaleY:0.46,x:726.2,y:370},0).to({scaleX:0.45,scaleY:0.45,x:708,y:360.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:585.3,y:299.8},0).to({scaleX:0.39,scaleY:0.39,x:568.7,y:291.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.34,scaleY:0.34,x:456.1,y:235.3},0).to({scaleX:0.33,scaleY:0.33,x:440.7,y:227.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:338.4,y:176.7},0).to({scaleX:0.28,scaleY:0.28,x:324.4,y:169.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:232.2,y:123.7},0).to({scaleX:0.23,scaleY:0.23,x:219.8,y:117.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:137.6,y:76.6},0).to({scaleX:0.19,scaleY:0.19,x:126.6,y:71},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.16,scaleY:0.16,x:54.6,y:35.1},0).to({scaleX:0.16,scaleY:0.16,x:45,y:30.4},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:-16.9,y:-0.5},0).to({scaleX:0.13,scaleY:0.13,x:-24.9,y:-4.5},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_22).wait(6).to({_off:false},0).to({scaleX:0.5,scaleY:0.5,x:820.1,y:416.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.44,scaleY:0.44,x:689.9,y:352},0).to({scaleX:0.43,scaleY:0.43,x:672.1,y:343},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.38,scaleY:0.38,x:552,y:283.2},0).to({scaleX:0.37,scaleY:0.37,x:535.5,y:274.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:425.6,y:220.1},0).to({scaleX:0.32,scaleY:0.32,x:410.6,y:212.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.27,scaleY:0.27,x:310.7,y:162.9},0).to({scaleX:0.27,scaleY:0.27,x:297.1,y:156.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:207.4,y:111.4},0).to({scaleX:0.22,scaleY:0.22,x:195.4,y:105.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.19,scaleY:0.19,x:115.8,y:65.7},0).to({scaleX:0.18,scaleY:0.18,x:105.1,y:60.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.15,scaleY:0.15,x:35.6,y:25.7},0).to({scaleX:0.15,scaleY:0.15,x:26.5,y:21.1},1).to({_off:true},1).wait(6));
	this.timeline.addTween(cjs.Tween.get(this.instance_23).wait(8).to({_off:false},0).to({scaleX:0.48,scaleY:0.48,x:782.1,y:397.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.42,scaleY:0.42,x:654.3,y:334.1},0).to({scaleX:0.42,scaleY:0.42,x:636.8,y:325.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:519.3,y:266.8},0).to({scaleX:0.36,scaleY:0.36,x:503.3,y:258.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.31,scaleY:0.31,x:395.8,y:205.3},0).to({scaleX:0.3,scaleY:0.3,x:381.1,y:197.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:283.9,y:149.5},0).to({scaleX:0.26,scaleY:0.26,x:270.7,y:142.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.22,scaleY:0.22,x:183.4,y:99.5},0).to({scaleX:0.21,scaleY:0.21,x:171.7,y:93.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:94.6,y:55.1},0).to({scaleX:0.17,scaleY:0.17,x:84.3,y:50},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.14,scaleY:0.14,x:17.5,y:16.6},0).to({scaleX:0.14,scaleY:0.14,x:8.5,y:12.2},1).to({_off:true},1).wait(4));

	// Layer 30
	this.instance_24 = new lib.shape4("synched",0);
	this.instance_24.setTransform(763.9,340.4,0.54,0.54);

	this.instance_25 = new lib.shape5("synched",0);
	this.instance_25.setTransform(752.4,360.9,0.52,0.52);
	this.instance_25._off = true;

	this.instance_26 = new lib.shape6("synched",0);
	this.instance_26.setTransform(711.7,341.2,0.501,0.501);
	this.instance_26._off = true;

	this.instance_27 = new lib.shape7("synched",0);
	this.instance_27.setTransform(671.9,322,0.482,0.482);
	this.instance_27._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_24).to({x:816.2,y:387},1).to({_off:true},1).wait(1).to({_off:false,x:793.9,y:381},0).to({scaleX:0.53,scaleY:0.53,x:773.1,y:370.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.46,scaleY:0.46,x:632.9,y:303},0).to({scaleX:0.46,scaleY:0.46,x:613.7,y:293.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.39,scaleY:0.39,x:485.9,y:232.1},0).to({scaleX:0.39,scaleY:0.39,x:468.4,y:223.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.33,scaleY:0.33,x:352.6,y:167.5},0).to({scaleX:0.32,scaleY:0.32,x:337,y:160},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.28,scaleY:0.28,x:233.3,y:109.9},0).to({scaleX:0.27,scaleY:0.27,x:219.3,y:103.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.23,scaleY:0.23,x:127.8,y:58.9},0).to({scaleX:0.22,scaleY:0.22,x:115.7,y:53},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.18,scaleY:0.18,x:36.3,y:14.7},0).to({scaleX:0.18,scaleY:0.18,x:25.8,y:9.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.15,scaleY:0.15,x:-41.4,y:-22.8},0).to({scaleX:0.14,scaleY:0.14,x:-50.1,y:-27.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.12,scaleY:0.12,x:-105.2,y:-53.7},0).to({scaleX:0.11,scaleY:0.11,x:-112.1,y:-57.1},1).to({_off:true},1).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_25).wait(5).to({_off:false},0).to({scaleX:0.51,scaleY:0.51,x:732,y:351},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.45,scaleY:0.45,x:594.8,y:284.7},0).to({scaleX:0.44,scaleY:0.44,x:576.2,y:275.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.38,scaleY:0.38,x:451.3,y:215.2},0).to({scaleX:0.37,scaleY:0.37,x:434.3,y:207.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.32,scaleY:0.32,x:321.5,y:152.5},0).to({scaleX:0.31,scaleY:0.31,x:306.1,y:145.1},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.26,scaleY:0.26,x:205.6,y:96.5},0).to({scaleX:0.26,scaleY:0.26,x:192.1,y:89.9},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.21,scaleY:0.21,x:103.6,y:47.3},0).to({scaleX:0.21,scaleY:0.21,x:91.9,y:41.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.17,scaleY:0.17,x:15.6,y:4.7},0).to({scaleX:0.17,scaleY:0.17,x:5.5,y:-0.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.14,scaleY:0.14,x:-58.7,y:-31.2},0).to({scaleX:0.13,scaleY:0.13,x:-66.9,y:-35.2},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.11,scaleY:0.11,x:-119,y:-60.3},0).wait(1));
	this.timeline.addTween(cjs.Tween.get(this.instance_26).wait(7).to({_off:false},0).to({scaleX:0.49,scaleY:0.49,x:691.7,y:331.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.43,scaleY:0.43,x:557.7,y:266.7},0).to({scaleX:0.42,scaleY:0.42,x:539.4,y:258},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.36,scaleY:0.36,x:417.4,y:198.9},0).to({scaleX:0.35,scaleY:0.35,x:401,y:191},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.3,scaleY:0.3,x:291.2,y:137.9},0).to({scaleX:0.3,scaleY:0.3,x:276.4,y:130.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.25,scaleY:0.25,x:178.8,y:83.7},0).to({scaleX:0.24,scaleY:0.24,x:165.8,y:77.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.2,scaleY:0.2,x:80.2,y:36},0).to({scaleX:0.2,scaleY:0.2,x:69,y:30.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.16,scaleY:0.16,x:-4.3,y:-4.9},0).to({scaleX:0.16,scaleY:0.16,x:-14,y:-9.6},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.13,scaleY:0.13,x:-75,y:-39.1},0).to({scaleX:0.13,scaleY:0.13,x:-82.9,y:-42.8},1).to({_off:true},1).wait(5));
	this.timeline.addTween(cjs.Tween.get(this.instance_27).wait(9).to({_off:false},0).to({scaleX:0.47,scaleY:0.47,x:652.3,y:312.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.41,scaleY:0.41,x:521.4,y:249.1},0).to({scaleX:0.4,scaleY:0.4,x:503.5,y:240.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.35,scaleY:0.35,x:384.6,y:183.1},0).to({scaleX:0.34,scaleY:0.34,x:368.5,y:175.3},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.29,scaleY:0.29,x:261.8,y:123.7},0).to({scaleX:0.28,scaleY:0.28,x:247.5,y:116.7},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.24,scaleY:0.24,x:152.9,y:71.1},0).to({scaleX:0.23,scaleY:0.23,x:140.2,y:65},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.19,scaleY:0.19,x:57.9,y:25.1},0).to({scaleX:0.19,scaleY:0.19,x:46.9,y:19.8},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.15,scaleY:0.15,x:-23.3,y:-14.1},0).to({scaleX:0.15,scaleY:0.15,x:-32.3,y:-18.5},1).to({_off:true},1).wait(6).to({_off:false,scaleX:0.12,scaleY:0.12,x:-90.5,y:-46.6},0).to({scaleX:0.12,scaleY:0.12,x:-98,y:-50.2},1).to({_off:true},1).wait(3));

	// Layer 29
	this.instance_28 = new lib.shape3("synched",0);

	this.timeline.addTween(cjs.Tween.get(this.instance_28).wait(70));

	// Layer 2
	this.instance_29 = new lib.shape2("synched",0);
	this.instance_29.setTransform(509.2,132.9);

	this.timeline.addTween(cjs.Tween.get(this.instance_29).to({x:485.3},69).wait(1));

	// Layer 1
	this.instance_30 = new lib.shape1("synched",0);

	this.timeline.addTween(cjs.Tween.get(this.instance_30).wait(70));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0,0,1025.3,373.4);


(lib.sprite50 = function(mode,startPosition,loop) {
	this.initialize(mode,startPosition,loop,{});

	// Layer 17
	this.instance = new lib.shape34("synched",0);
	this.instance.setTransform(-212.9,128.3,0.475,0.475,0,0,180);
	this.instance._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(20).to({_off:false},0).to({_off:true},1).wait(18).to({_off:false,x:-502.4,y:134.8},0).to({_off:true},1).wait(52));

	// Layer 15
	this.instance_1 = new lib.shape24复制2("synched",0);
	this.instance_1.setTransform(87.8,84,0.475,0.475,0,0,180);
	this.instance_1._off = true;

	this.instance_2 = new lib.shape25复制2("synched",0);
	this.instance_2.setTransform(14.1,49.6,0.475,0.475,0,0,180);
	this.instance_2._off = true;

	this.instance_3 = new lib.shape26复制2("synched",0);
	this.instance_3.setTransform(-59.6,15.2,0.475,0.475,0,0,180);
	this.instance_3._off = true;

	this.instance_4 = new lib.shape28复制2("synched",0);
	this.instance_4.setTransform(-184.3,100.6,0.475,0.475,0,0,180);
	this.instance_4._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(3).to({_off:false},0).to({x:38.7,y:61.1},2).to({_off:true},1).wait(18).to({_off:false,x:-280.1,y:104.1},0).to({x:-317.6,y:76.1},2).to({_off:true},1).wait(16).to({_off:false,x:-570.3,y:116.5},0).to({x:-608.2,y:95.7},2).to({_off:true},1).wait(46));
	this.timeline.addTween(cjs.Tween.get(this.instance_2).wait(6).to({_off:false},0).to({x:-35,y:26.6},2).to({_off:true},1).wait(1).to({_off:false,x:-84.1,y:3.7},0).to({_off:true},1).wait(16).to({_off:false,x:-336.3,y:62.1},0).to({x:-373.7,y:34.2},2).to({_off:true},1).wait(16).to({_off:false,x:-627.1,y:85.3},0).to({x:-664.9,y:64.6},2).to({_off:true},1).wait(43));
	this.timeline.addTween(cjs.Tween.get(this.instance_3).wait(9).to({_off:false},0).to({_off:true},1).wait(1).to({_off:false,x:-98.4,y:17.5},0).to({x:-127,y:45.2},2).to({_off:true},1).wait(16).to({_off:false,x:-388,y:45.4},0).to({x:-416.6,y:67.7},2).to({_off:true},1).wait(16).to({_off:false,x:-679.2,y:72.9},0).to({x:-707.8,y:89.6},2).to({_off:true},1).wait(40));
	this.timeline.addTween(cjs.Tween.get(this.instance_4).wait(17).to({_off:false},0).to({x:-212.9,y:128.3},2).to({_off:true},1).wait(16).to({_off:false,x:-473.8,y:112.4},0).to({x:-502.4,y:134.8},2).to({_off:true},1).wait(16).to({_off:false,x:-765,y:122.9},0).to({x:-793.6,y:139.6},2).to({_off:true},1).wait(34));

	// Layer 14
	this.instance_5 = new lib.shape23复制2("synched",0);
	this.instance_5.setTransform(161.5,118.5,0.475,0.475,0,0,180);

	this.instance_6 = new lib.shape27复制2("synched",0);
	this.instance_6.setTransform(-141.3,59.1,0.475,0.475,0,0,180);
	this.instance_6._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_5).to({x:112.4,y:95.5},2).to({_off:true},1).wait(18).to({_off:false,x:-224,y:146},0).to({x:-261.4,y:118},2).to({_off:true},1).wait(16).to({_off:false,x:-513.6,y:147.6},0).to({x:-551.4,y:126.8},2).to({_off:true},1).wait(49));
	this.timeline.addTween(cjs.Tween.get(this.instance_6).wait(14).to({_off:false},0).to({x:-170,y:86.7},2).to({_off:true},1).wait(16).to({_off:false,x:-430.9,y:78.9},0).to({x:-459.5,y:101.2},2).to({_off:true},1).wait(16).to({_off:false,x:-722.1,y:97.9},0).to({x:-750.7,y:114.6},2).to({_off:true},1).wait(37));

	// Layer 9
	this.instance_7 = new lib.shape35("synched",0);
	this.instance_7.setTransform(-719.2,102.5,0.472,0.472);
	this.instance_7._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_7).wait(58).to({_off:false},0).to({_off:true},2).wait(32));

	// Layer 7
	this.instance_8 = new lib.shape37("synched",0);
	this.instance_8.setTransform(-688.1,132.4,0.726,0.726);

	this.instance_9 = new lib.shape40("synched",0);
	this.instance_9.setTransform(-688.1,132.4,0.726,0.726);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_8}]},63).to({state:[]},3).to({state:[{t:this.instance_9}]},19).to({state:[]},3).wait(4));

	// Layer 6
	this.instance_10 = new lib.shape36("synched",0);
	this.instance_10.setTransform(-688.1,132.4,0.726,0.726);
	this.instance_10._off = true;

	this.timeline.addTween(cjs.Tween.get(this.instance_10).wait(60).to({_off:false},0).to({_off:true},3).wait(25).to({_off:false},0).to({_off:true},2).wait(2));

	// Layer 4
	this.instance_11 = new lib.shape38("synched",0);
	this.instance_11.setTransform(-688.1,132.4,0.726,0.726);

	this.instance_12 = new lib.shape39("synched",0);
	this.instance_12.setTransform(-688.1,132.4,0.726,0.726);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_11}]},66).to({state:[{t:this.instance_12}]},3).to({state:[]},16).wait(7));

	// Layer 3
	this.instance_13 = new lib.sprite33();
	this.instance_13.setTransform(-185.4,81.6,0.232,0.232,30.6);
	this.instance_13.filters = [new cjs.BlurFilter(3, 3, 1)];
	this.instance_13.cache(-115,113,153,175);

	this.instance_14 = new lib.sprite49();
	this.instance_14.setTransform(-757.1,77.5,0.475,0.475,-0.4);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.instance_13,p:{x:-185.4,y:81.6}}]},20).to({state:[]},1).to({state:[{t:this.instance_13,p:{x:-478.3,y:91.3}}]},18).to({state:[]},1).to({state:[{t:this.instance_13,p:{x:-788.8,y:93}}]},18).to({state:[]},2).to({state:[{t:this.instance_14}]},30).wait(2));

	// Layer 1
	this.instance_15 = new lib.sprite22();
	this.instance_15.setTransform(169.3,96.1,0.853,1.151);
	this.instance_15.filters = [new cjs.BlurFilter(8, 8, 1)];
	this.instance_15.cache(-2,-2,88,15);

	this.timeline.addTween(cjs.Tween.get(this.instance_15).to({x:148.8,y:97.4},1).to({x:-220.7,y:121.2},18).to({x:-209.9},1).to({x:-206.3},1).to({x:-511.4,y:128.5},17).to({x:-500.6},1).to({x:-506},1).to({x:-812.4,y:133.9},17).wait(1).to({x:-810.6},0).wait(2).to({x:-808.8},0).wait(30).to({scaleX:1.03,scaleY:0.89,x:-816.2,y:135.4},0).wait(2));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(145.2,-72.8,142.2,197.9);


// stage content:



(lib.KXZYAnswer_Canvas = function(mode,startPosition,loop) {
if (loop == null) { loop = false; }	this.initialize(mode,startPosition,loop,{});

	// Layer 70
	this.instance = new lib.shape47_1("synched",0);

	this.timeline.addTween(cjs.Tween.get(this.instance).wait(92));

	// Layer 66
	this.instance_1 = new lib.sprite50("synched",0,false);
	this.instance_1.setTransform(866.2,460.9);

	this.timeline.addTween(cjs.Tween.get(this.instance_1).wait(92));

	// Layer 65
	this.instance_2 = new lib.text19复制();
	this.instance_2.setTransform(854.1,344.8,0.859,0.859);
	this.instance_2.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];
	this.instance_2.cache(0,3,68,69);

	this.instance_3 = new lib.text17复制();
	this.instance_3.setTransform(695.3,344.8,0.859,0.859);
	this.instance_3.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];
	this.instance_3.cache(0,4,70,66);

	this.instance_4 = new lib.text15复制();
	this.instance_4.setTransform(485.2,344.8,0.859,0.859);
	this.instance_4.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];
	this.instance_4.cache(1,8,69,66);

	this.instance_5 = new lib.text11复制();
	this.instance_5.setTransform(117.6,344.8,0.859,0.859);
	this.instance_5.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];
	this.instance_5.cache(5,0,58,73);

	this.shape = new cjs.Shape();
	this.shape.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("EBLtgCrIAsACICSADIAFAAIAChaIgFAAIgCAuEBNdAByIgCgHIgKgRIgFgRIhfj0EBOpAB5IhMgHIjdgMIjNgMIi5gFIjTgFIjFAAIjKAAIjIACIjKAFIivAFIjDAKIjbAMIjsATIgTAAIgCAAIimAPEgl8AC7IKABkIGFhwIFhhJIKuhHQCrgOCvAMIKzAsIK9BaIFKA/IFuAKIDPgZIWvhBIT9AdIAAgFIARACIADgzEBOwgCmIgHEfEAnQgDSIAHAAIAsAAIBmACIAnAAICXADIAgAAIBvAAIAqACIByADIAiAAIAPACIBwAAIApADICIACIAqAAICZAAIApACIB/ADIAKACIAHAAIBCEDIAAAHEAnQgDSIAHACIAAgCAWCjhIAfAAIEfADIAIAAIAdAAIEEAFIAIAAIAuACIC5AAIAFAAIBGAAICtAFAXBB0IAHgJIDxk4IAMgPIADgCEAjdgDXIgDADIkyGPAfxjZIgDACIlQFmIgMAPIjRgqIj/grIjxggIgDAAIgdgCIjIgZIBTj4IAqAAIAMACIEVAFID2ACIAFAAEAkEACWIhFAFIjWAWAfkCxIg8AKIjqgUIgsgJEAu2gDNIgiEwIAAADEAxXABcIAAgCIgFkiEAsngDNIhrE9IgDACEAm6ACFICvlVEAi/ACbIEYlrEA8ggC6IAsAAIB8AAIAMADIAFAAIAsAAICbACIAqAAICKADIAsACIChACIAsAAICgADEA/ZgC3IBLECIADAFEA5ngDBIAxEKIAAAHEA2kgDBIAqERIACACEAzzgDGIATEYIAAAFEBFUgCyIBaEHIAFAFEBIhgCuIBcENIADAHEBCggC1IBYEFIACAFApPj3IAgAAIF8AFIAfAAIFTADIAfAAIEpACIAlAAIEQADApIgUIAAgDIgHjgAizjyIgIDBIAAAEImNAZIkwAiIgVADIgNAAIgukIIAiAAIFXAAAnQliIgjAAA1GlmIiSAAIoEgFQs7gDleAPI8aAWIgiAHIAAAgA68j5IAkAAIDKAAIAkAAIChAAIAiAAIEfACAuaARIjUAkIgCAAIgFADIgkAEIhwk1AyZA8Ii7AqIgUACIgPAFIjFAzA5WCnIgRAIIiEApIhagTIkCgnIkHgiIjAgRIjUgRIj6gKIjbAAIkMACIjaANIkWAYIkRAfIlGA4IkaA7IErnlIAggCIAHAAIDsgFIDtgFIAOgCICsgDIDjgCA3Oj5IBXFmATCBJIAHgKIC2kdIADgDEBOtgEAMg8QgBMInAgJADEglIgFjKAIHjtIAiDgIADAKIlogbIl/gPALpAOIi9gREg/5gDoIAJgCIAFAAIjZGAEg5tgCOIgDADIgJAzIAAAEIgSBRIgEAWIAAADIgCAJIgMBAEg5tgCQIAAgIIAOhJIADgOIACAAIAZAAICegDIC+gFIDrAAIEHAAIDWgCIADAgIAAACIACAuIAAARIAKEDEhH5gDcIAgAAIAHgCEhMCgDSIAYgCIAIAAEhMJgDSIAHAAIAYAAIAIgCIDpgIEhOgAEmIgPAFIAABCIMNiSIK9hJIFtgKILoAzEg3DABSIAglEEhDmgDjIkkGxEg+zAB3IB0lkEggugD5IC0AAIC+AAIBVGoEgkigD5ID0AAIgZGXEgodgD5ID7AAA96j5IA1G+EglOAB8IAsl1EgrzgD3IARFREgv6gD3IAeFHEgzlgD3IAuFHEhOvgDNIAFAAEhMJgDSIiJEHIgHARIgHAKIgDAKIADDMEhOjABaIgHknIChgFEhMkAEJIh8Ad");
	this.shape.setTransform(493.9,289.3);

	this.shape_1 = new cjs.Shape();
	this.shape_1.graphics.lf(["#84576B","#470F21"],[0,0.914],-8.6,14.8,0.5,-16.7).s().p("AhPiRICfgEIiHEHIgEADIgDAOIgHAJIgDAKg");
	this.shape_1.setTransform(-1.6,283.2);

	this.shape_2 = new cjs.Shape();
	this.shape_2.graphics.lf(["#84576B","#470F21"],[0,0.914],0.1,18.9,0.1,-18.8).s().p("AhtCqIgKkDIAKB9IAHAdIADAbQgFATAOAPIANAAIAAgDIAJgMIADgOIgPhHIgFgKIAAgQIgKh4IADggQACgWgYgCIgCACIgFAUIgDggID5AAIgsF1g");
	this.shape_2.setTransform(247.4,283);

	this.shape_3 = new cjs.Shape();
	this.shape_3.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.2,18,-0.2,-17.8).s().p("AhgChIgRlQIDUgCIADAfIAAADIAAAdIACARIAAARIAKECgAhliIIAKAbQAKArACAxIAAB1IAPAbIAHAAQAMgHgCgPIgUjxIgEgHQAAgKgIgDIgRgHQgRAKAMARg");
	this.shape_3.setTransform(224.9,282.1);

	this.shape_4 = new cjs.Shape();
	this.shape_4.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.3,16.1,0.6,-16.6).s().p("AhVCkIgulHIDqAAIAdFHgAhtiPIgFAFIAYDDIAAAMQAAAWAdACIADgCIAFgDIACgEIgCgKIgFgFIgWi3IgCgHIAAgFIgKgPIgPgHg");
	this.shape_4.setTransform(176.9,280.9);

	this.shape_5 = new cjs.Shape();
	this.shape_5.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.4,16.7,0.7,-17.3).s().p("AhtCfIgelHIEFAAIARFRgAh5h+IAbDZIAAARIAOAdIAKAAIACgCIAAgDQAKgRgIgTIgHglIgRimIAAgMIgKgKIgOgEg");
	this.shape_5.setTransform(201.2,281.4);

	this.shape_6 = new cjs.Shape();
	this.shape_6.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.1,22.5,1.2,-22.4).s().p("Ah/C4IAYmWICyAAIA1G9gAhZjQIgHAWIACD0IgGA6IAJAbIAKACIAAADIAEgIQAIgVAAgWIAFkZQADgOgKgHIgMgFg");
	this.shape_6.setTransform(294.8,286.6);

	this.shape_7 = new cjs.Shape();
	this.shape_7.graphics.lf(["#84576B","#470F21"],[0,0.914],0.1,23.4,0.1,-23.3).s().p("AhTDVIg1m9IC8AAIBVGoIiEApgAhtjTIgDBPIAdECIAAAHQAAAZAbACIADAAIACgDIACgEIgilWQADgTgMgHIgIgDIgEAAg");
	this.shape_7.setTransform(316.1,287.6);

	this.shape_8 = new cjs.Shape();
	this.shape_8.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.8,20.1,0.4,-20.9).s().p("AiOCqIArl1IDyAAIgYGXgAhZjEIgEAbIgDCXIgOBwIgGAIIAAACIgEACQgKAPAOAKIANAAIAEgFIAPgdIAIgiIAKjSIAEgYIgEgWQgIgIgKAAg");
	this.shape_8.setTransform(270,284.7);

	this.shape_9 = new cjs.Shape();
	this.shape_9.graphics.lf(["#84576B","#470F21"],[0,0.914],2.2,24.2,-2,-25.1).s().p("AjUA1IADgKIAHgJIAHAHIAQAAIAJgFIAIgMIAEgNIA0hcIAJgNIA9h1IgHgJIgKgDIgZAdIhyDQIgDADICJkIIAHAAIAZAAIAHgCIDngIIkpHlIh8Aeg");
	this.shape_9.setTransform(12.4,293);

	this.shape_10 = new cjs.Shape();
	this.shape_10.graphics.lf(["#84576B","#470F21"],[0,0.914],0.7,23.7,-1.1,-23.9).s().p("AAMjuIAfAAIAIgCIDsgGIkiGyIkbA7gAgBjAIAAAFIgRApIiIDoIgFAFIglAzQgTAHAPAMIAAADIAEAEIAIgCIAHgHIAsgzICrkxIACgQIgFgFIgMgDg");
	this.shape_10.setTransform(32.5,291.1);

	this.shape_11 = new cjs.Shape();
	this.shape_11.graphics.lf(["#84576B","#470F21"],[0,0.914],2.2,16.5,-2.3,-17.1).s().p("AhwBpIAUAMIAFgCQAOgbAAgiIADgbIAmjAICdgDIggFEIjZANgAhpBAIAAAFIgEAYgAhOhEIADgKIAAAIIAAACgAg6ilIACAAIgFAOg");
	this.shape_11.setTransform(132.1,281.9);

	this.shape_12 = new cjs.Shape();
	this.shape_12.graphics.lf(["#84576B","#470F21"],[0,0.914],0.2,16.6,0.2,-16.1).s().p("AhlifIC8gFIAuFGIkJADgAhWiRQgRAXACAfIgMC0QgFAZAbACIAAgCIAKgTIAJjNIADgNIAAgQQgFgIgMgCg");
	this.shape_12.setTransform(154.9,281);

	this.shape_13 = new cjs.Shape();
	this.shape_13.graphics.lf(["#84576B","#470F21"],[0,0.914],0.2,19.6,-0.1,-19.1).s().p("AAVi/ICtgCIh0FkIkPAfgAAVi1IgKAiIhmDSIgiApIACATIADADIAFAAIAHgDIAOgLIA2hPIAshhIAHgMIAjhWIAAgQQgDgIgPgCg");
	this.shape_13.setTransform(84.1,284.9);

	this.shape_14 = new cjs.Shape();
	this.shape_14.graphics.lf(["#84576B","#470F21"],[0,0.914],1,21.1,-1.1,-21).s().p("AAUjVIDsgEIAPgDIjZGBIlEA4gAAbjEIi3ENIgEAEIggAnIgCAFQgKAPARAFIAKAAIAHgNIApguICpj4IAHgFIACgIIADgCQAFgMgIgFIgHgCIgFAAg");
	this.shape_14.setTransform(59.1,287.8);

	this.shape_15 = new cjs.Shape();
	this.shape_15.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.2,11.7,-0.2,-11.8).s().p("AiyBbIAAgIIAhADIgHjKIEnACIAiDeIADAKg");
	this.shape_15.setTransform(531.5,277);

	this.shape_16 = new cjs.Shape();
	this.shape_16.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.2,12.7,-1.4,-12.6).s().p("AiFBrIgDgKIAkAEIghjiIEOACIhTD5g");
	this.shape_16.setTransform(563,278.1);

	this.shape_17 = new cjs.Shape();
	this.shape_17.graphics.lf(["#84576B","#470F21"],[0,0.914],0,15.2,0.3,-15.2).s().p("AjMB2IgCAAIgRgBIDJkLID0ADIADADIi3EcIgHALg");
	this.shape_17.setTransform(612.2,281.5);

	this.shape_18 = new cjs.Shape();
	this.shape_18.graphics.lf(["#84576B","#470F21"],[0,0.914],0,13.4,0,-13.7).s().p("AgGCJIjIgYIBTj5IAqAAIAMACIETAFIjJEKg");
	this.shape_18.setTransform(589.2,279.5);

	this.shape_19 = new cjs.Shape();
	this.shape_19.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.9,18.4,-1.9,-17.6).s().p("AiHivICfAAIBwE1Ii5Aqg");
	this.shape_19.setTransform(362.4,281.9);

	this.shape_20 = new cjs.Shape();
	this.shape_20.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.6,15.4,-1.9,-15.9).s().p("AiliYIEdACIAuEIIjRAkIgDAAIgFADg");
	this.shape_20.setTransform(384.9,279.6);

	this.shape_21 = new cjs.Shape();
	this.shape_21.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.8,21.6,-2.3,-21.1).s().p("AiPjNIDHAAIBYFmIjDAzIgKACg");
	this.shape_21.setTransform(339.4,284.9);

	this.shape_22 = new cjs.Shape();
	this.shape_22.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.1,10.9,-0.3,-10.7).s().p("Ai/BbIAAgFIAlgCIACi8IFTACIAFDIIAAAHg");
	this.shape_22.setTransform(494.3,275.5);

	this.shape_23 = new cjs.Shape();
	this.shape_23.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.9,12.9,-1.9,-13.8).s().p("AiuiCIFVAAIAIDeIAAADIkvAkg");
	this.shape_23.setTransform(417.9,277.6);

	this.shape_24 = new cjs.Shape();
	this.shape_24.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.7,11.4,-0.7,-11.1).s().p("AjIBuIAigCIgLjcIF7AFIgIC/IAAAEImKAZg");
	this.shape_24.setTransform(455.6,275.8);

	this.shape_25 = new cjs.Shape();
	this.shape_25.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.6,13.9,-2.4,-14.3).s().p("AhkCIIAsgDIgskOICYAAIAxEKIAAAHIjGACg");
	this.shape_25.setTransform(857.4,283.7);

	this.shape_26 = new cjs.Shape();
	this.shape_26.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.5,13.8,-2.7,-14.1).s().p("AhgCIIAAgHIAqAAIgxkIIB8ADIAKACIAHAAIBCEDIAAAHg");
	this.shape_26.setTransform(877.2,283.7);

	this.shape_27 = new cjs.Shape();
	this.shape_27.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.1,14,-1.1,-14.6).s().p("AhjCJIAsAAIgXkWICGACIAqERIACADIjHAFg");
	this.shape_27.setTransform(837.5,283.8);

	this.shape_28 = new cjs.Shape();
	this.shape_28.graphics.lf(["#84576B","#470F21"],[0,0.914],-2.8,13.4,-1.5,-13.3).s().p("AhDCDIgCgFIAsAAIhakFICIADIBaEHIAFAFg");
	this.shape_28.setTransform(935.4,284.7);

	this.shape_29 = new cjs.Shape();
	this.shape_29.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.9,14.1,-2.1,-14.2).s().p("AhLB/IgFgFIAuADIhckHICeACIBdEMIACAHg");
	this.shape_29.setTransform(954.7,285.5);

	this.shape_30 = new cjs.Shape();
	this.shape_30.graphics.lf(["#84576B","#470F21"],[0,0.914],-2,13.5,-2.1,-13.7).s().p("AhXCBIgDgEIAvACIhOkEICZACIBYEEIACAGg");
	this.shape_30.setTransform(916.3,284.3);

	this.shape_31 = new cjs.Shape();
	this.shape_31.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.9,13.6,-2,-13.6).s().p("AhWCFIAAgHIApAAIg/kCIB6AAIANACIAEAAIBLEDIADAEg");
	this.shape_31.setTransform(896.5,283.9);

	this.shape_32 = new cjs.Shape();
	this.shape_32.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.1,14.7,0.2,-14.7).s().p("AhWCPIApAAIgEkfIAHAAIBtAAIAUEXIAAAGIitAEg");
	this.shape_32.setTransform(818.6,284);

	this.shape_33 = new cjs.Shape();
	this.shape_33.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.7,19.2,0.5,-19.3).s().p("AkVCVIAHgKIAZAGIEClOIEEAEIAFADIlOFmIgMAPg");
	this.shape_33.setTransform(669.1,286);

	this.shape_34 = new cjs.Shape();
	this.shape_34.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.2,21.3,-0.2,-20.3).s().p("Aj3C2IgsgKIANgOIApAJIFVlvIC5AAIACACIkwGPg");
	this.shape_34.setTransform(691.4,287.8);

	this.shape_35 = new cjs.Shape();
	this.shape_35.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.2,17.4,-0.2,-17).s().p("AkACAIAHgLIAYAGIADAAIC9klIEdADIAFACIgFAFIgHAJIjxE4IgFAKg");
	this.shape_35.setTransform(641.5,283.8);

	this.shape_36 = new cjs.Shape();
	this.shape_36.graphics.lf(["#84576B","#470F21"],[0,0.914],-1,17.5,-0.8,-17.1).s().p("AiVCnICrlRICWACIhrE+IgCABIjqAUg");
	this.shape_36.setTransform(762.1,285.5);

	this.shape_37 = new cjs.Shape();
	this.shape_37.graphics.lf(["#84576B","#470F21"],[0,0.914],-0.4,16.2,0.5,-16.1).s().p("Ah7CeIAhgDIBpk6IBvAAIgiExIAAACIjZAMg");
	this.shape_37.setTransform(781.1,284.7);

	this.shape_38 = new cjs.Shape();
	this.shape_38.graphics.lf(["#84576B","#470F21"],[0,0.914],0.1,16,1.4,-15).s().p("AhgCWIAqgCIAiksIBwADIAFEiIAAACIjBAKg");
	this.shape_38.setTransform(800.1,284.2);

	this.shape_39 = new cjs.Shape();
	this.shape_39.graphics.lf(["#84576B","#470F21"],[0,0.914],-3.4,18.8,-3.4,-18).s().p("ABLi0IBmADIivFVIgCAAIikAPIgMACg");
	this.shape_39.setTransform(742.7,286.2);

	this.shape_40 = new cjs.Shape();
	this.shape_40.graphics.lf(["#84576B","#470F21"],[0,0.914],-3.8,20.7,-3.2,-19.7).s().p("ABCjDICtAEIAHADIkWFqIjWAXg");
	this.shape_40.setTransform(721.2,287.4);

	this.shape_41 = new cjs.Shape();
	this.shape_41.graphics.lf(["#84576B","#470F21"],[0,0.914],-251,47,-251.6,-5.3).s().p("AIlAGIAIAAIAAACgAEtABIAFAAIgCADgAA/AAIAHAAIgCABgAjogEIAHAAIgCACgAosgHIAFAAIgCADg");
	this.shape_41.setTransform(690.1,267.5);

	this.shape_42 = new cjs.Shape();
	this.shape_42.graphics.f("#A57287").s().p("EhOrACLIAPgEIB8geIEag6IFGg2IERggIEVgYIDbgMIELgDIDcAAID6AKIDTARIDBARIEHAiIEBAlIBaAUICEgqIARgHIARgFIAKgCIDFgxIAOgFIAUgDIC7gpIAkgFIAGgCIACAAIDUglIAMAAIAVgCIExglIGMgYIF/AOIFoAbIC9AUIDIAYIANAAIARACIgegCIAeACIACAAIDxAgID/AsIDRApIAsAIIDqATIA9gJIAEAAIDWgUIBFgFIACAAIAMgCICmgPIACAAIATAAIDsgUIDcgMIDCgJICwgFIDJgFIDIgDIDKAAIDFAAIDTAFIC6AFIDMAMIDdAMIBMAIIgDAxIgRgDIAAAFIz9gbI2vBAIjPAYIltgKIlLg/Iq8hYIq0gsQivgMirAPIqtBGIlhBHImGBwIqAhkIgTAAIrpgxIlsAKIq9BHIsNCSg");
	this.shape_42.setTransform(493.6,305.2);

	this.shape_43 = new cjs.Shape();
	this.shape_43.graphics.f("#3F1826").s().p("EBMcABdIgrgDIihgCIgsAAIiggCIgsgDIiLgCIgpAAIicgCIgsAAIgEAAIgNgDIh8AAIgsAAIgHAAIgKgDIh+gCIgpgCIiaAAIgpAAIiIgCIgqgDIhwAAIgOgCIgiAAIhzgDIgpgDIhwAAIggAAIiWgCIgnAAIhngCIgrAAIgIAAIisgFIhHAAIgFAAIi5AAIgugDIgHAAIkFgEIgdAAIgHAAIkfgDIggAAIgFAAIj1gDIkWgEIgMgDIgpAAIkRgCIgkAAIkpgCIggAAIlSgDIggAAIl8gFIgfAAIlXAAIgiAAIkfgCIgiAAIihAAIglAAIjJAAIglAAIi+AAIi0AAIjzAAIj7AAIjWACIkHAAIjsAAIi9AFIifADIgYAAIgCAAIjjACIisACIgFAAIgKADIjsAEIjsAGIgHAAIggACIjpAIIgIAAIgYACIgHAAIihAEIgFAAIgChSIA4gDQZnguacgSICegCICSAAINTAFIAiAAIAIAAIAOACISYAKIHAAKIkCAWUAjZAAWAc2ABMIAAAsgEAx/ABAIgHgCIAOACg");
	this.shape_43.setTransform(493.5,262.9);

	this.shape_44 = new cjs.Shape();
	this.shape_44.graphics.lf(["#84576B","#470F21"],[0,0.914],2.9,15.1,-2.4,-16).s().p("AgGCKIgCgHIAHACIAjAAIhskWICQADIAFAAIgHEfg");
	this.shape_44.setTransform(990.4,286.9);

	this.shape_45 = new cjs.Shape();
	this.shape_45.graphics.f("#BA92A3").s().p("AOegVIECgWMA8PABJIgCAuUgc3gBMgjYgAVgEhOvgAhIAjgIIcZgWQFegOM8ACIIEAFIifACQ6bAR5oAvIg4ACg");
	this.shape_45.setTransform(493.7,260.4);

	this.shape_46 = new cjs.Shape();
	this.shape_46.graphics.f("#7F5265").s().p("EAjkgC1IACgCIBHAAIk6GIIgFAAIg9AJgEhLFADWIAAgDQgPgMAUgHIAkgzIAFgFICJjoIARgpIAAgFIAVgdIANACIAEAFIgCARIitExIgrAzIgIAHIgHACgA6zjZIAlAAIBSGbIgRAFIgRAHgEAnhgCwIAAgDIArAAIj9FpIgCAAIhEAFgAaoCvIFQlmIACgDIAuADIlXFvgA8gCvQgbgDAAgYIAAgHIgdkDIAChOIAFgHIAFAAIAHADQAMAHgCATIAiFVIgDAFIgCADgEAnDAClICwlVIAnAAIitFQIgWAFgEhGtACeQgRgFAKgPIACgFIAggnIAEgEIC5kNIAKgEIAFAAIAHACQAIAFgFAMIgDACIgCAIIgHAFIirD4IgpAuIgHANgEggPACWIgKgCIgJgbIAHg6IgDj0IAIgWIAFgCIAMAFQAKAHgDAOIgFEZQAAAWgHAVIgFAIgEg81gDNIDjgDIgDAPIgOBJIgDAKIADAAIgDACIgJAzIAAAFIgSBOIAAgFIgEAeIAAACIgDAKIADgKIAAgCIAEgZIAShOIAAgFIAJgzIADgCIAAgDIAAgHIAOhJIAFgPIAYAAIgmDDIgDAZQAAAigOAaIgFADIgUgMIgMA/IkVAYgEAswgCuIAgAAIhrE7IggACgAXSCKIDxk4IAHgJIAFgFIACgDIAdAAIkEFOgAbDiuIAMgOgEBNrACNIgHgDIgKgRIgEgRIhfj0IArADIBuEWgA3FjZIAlAAIBVFeIgTADIgPAFgEAvAgCuIApADIgiErIgpADgEBKHAB+IhdkMIAsAAIBdEOgEAxhAB5IgFkiIAiAAIAHADIAFEfgEBG4AB0IhakHIAsADIBcEHgEgkpAB0QgPgJAKgPIAFgCIAAgDIAFgHIAOhxIADiWIAFgbIAEgFQAKAAAIAHIAEAWIgEAYIgKDSIgIAiIgOAdIgFAFgEA0QAByIgUkYIAqACIAWEWgEhBxAByIgCgDIgDgTIAigpIBpjSIAKgiIAHgHQAPACACAHIAAARIgiBVIgHANIgvBhIg1BOIgPAMIgHADgEBEBABvIhXkEIApAAIBaEEgEA2ugCiIApAAIAsEPIgsACgEBAuABrIhMkDIAsAAIBOEFgEhOIABtIgHgHIAHgRIADgDIByjPIAZgdIAJACIAIAKIg9B1IgKAMIgzBdIgFAMIgHAMIgKAFgEA9rABoIhCkCIAsAAIBAECgEA6hABoIgwkKIApADIAxEHgEhOMABXIAEgCIgHARgATrBjIgZgFIC3kdIACgCIAgAAIi+EkgEgnxABeQgOgOAFgUIgDgaIgHgeIgKh8IAAgRIgCguIACAuIgCgRIAAgdIAAgDIAFgTIACgDQAYADgCAWIgDAfIAKB6IAAARIAFAKIAPBEIgDAPIgJAMIAAACgAz/jZIAiAAIByEwIglAFgEgu1ABaIgPgeIAAgRIgbjZIAIgHIAOAFIAKAKIAAAMIARCmIAHAkQAIAUgKARIAAACIgCADgEgq4ABXIgPgbIAAh1QgCgxgKgrIgKgbQgMgRARgKIARAHQAIADAAAKIAEAHIAUDxQACAPgMAHgEhOIABVgEg2mAAmIANi0QgDggARgWIAAgEQAMACAFAHIAAARIgCAMIgKDNIgKAUIAAACQgagCAEgZgAuQAwIgukHIAiAAIAuEFIgWACgEgyyAARIAAgNIgZjDIAFgFIADgEIAOAHIAKAOIAAAFIADAIIAVC3IAFAEIADAKIgDAFIgFACIgCADQgdgDAAgVgAIzARIgijeIAkAAIAiDigApFjXIAfAAIAKDbIgiADgADNgGIgFjKIAgAAIAHDNgAiqjSIAgAAIgDC+IgkACgEg5jgBug");
	this.shape_46.setTransform(492.9,286.1);

	this.shape_47 = new cjs.Shape();
	this.shape_47.graphics.lf(["#84576B","#470F21"],[0,0.914],-1.5,15,-2.3,-14.7).s().p("AhUCEIgCgHIAsACIhdkOICfACIBfD0IAEARIAKARIADAHg");
	this.shape_47.setTransform(976,286.2);

	this.shape_48 = new cjs.Shape();
	this.shape_48.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("EBLtgCrIC+AFIAFAAIAChaIgFAAMhDQgBVEBOrgDSIACguEBNdAByIgCgHIgKgRIgFgRIhfj0EBOpAB5IgDAzIgRgCIAAAFIz9gdI2vBBIjPAZIlugKIlKg/Iq9haIqzgsQivgMirAOIquBHIlhBJImFBwIqAhkEBOpAB5IhMgHEBOwgCmIgHEfEAnQgDSIAHAAICSACIC+ADICPAAICcAFIAiAAIAPACIBwAAICxAFIDDAAICoAFIAKACIAHAAIBCEDIAAAHIjKAAIjIACIjKAFIivAFIjDAKIjbAMIjsATIgTAAIgCAAIimAPEAnQgDSIAHACIAAgCAXBB0IEElQIADgCIEpAFIDsACIgDADIkyGPIA8gKEAkEACWIhFAFIjWAWAeoC7IjqgUIgsgJIjRgqEAu2gDNIgiEwIAAADEAsngDNIhrE9IgDACEAjdgDXIDzAFEAxXABcIgFkkAfxjZIlfF3EAi/ACbIEYlrEAm6ACFICvlVEA8ggC6ICoAAIAMADIAFAAIDHACIC0ADIDNAEIBfEUIjNgMIi5gFIjTgFIjFAAEA/ZgC3IBLECIADAFEA5ngDBIAxEKIAAAHEAzzgDGIATEYIAAAFEA2kgDBIAqERIACACEBFUgCyIBaEHIAFAFEBCggC1IBYEFIACAFEBIhgCuIDMADEBKAABmIDdAMApIgUIgHjjIGcAFIFyADIFIACIAiDgIADAKIlogbIl/gPIAIjFAnQliIgjAAApIgUIGNgZA0Jj5IFBACIAuEIIANAAIFFglAuaARIjUAkIgCAAIgFADIgkAEIhwk1A3Oj5IDFAAAyZA8Ii7AqIgUACIgPAFIjFAzA5WCnIgRAIIiEApIhagTIkCgnIkHgiIjAgRIjUgRIj6gKIjbAAIkMACIjaANIkWAYIkRAfIlGA4IkaA7IErnlIAggCIAHAAIDsgFIDtgFIAJgCIAFAAICsgDIDjgCIC5gDIC+gFIDrAAIEHAAIDWgCID7AAID0AAIC0AAIC+AAIDuAAIBXFmAvIj3IF5AAAM8jqIAqAAIAMACIIQAHIgDADIi9EnIjxggIgDAAIjlgbgADEglIgFjKAIHjtIE1ADATCBJID/ArALpAOIi9gREg/5gDoIAOgCIjZGAEg5tgCOIgDADIgJAzIAAAEIgWBnIAAADIgOBJEg5tgCQIAAgIIARhXEhH5gDcIAgAAIAHgCEhMCgDSIAYAAIAIgCEhMJgDSIAHAAIAYgCIAIAAIDpgIEgmQAC7IrogzIltAKIq9BJIsNCSIAAhCIAPgFIgDjMIgHknIChgFIiJEHIgHARIgHAKIgDAKEg+zAB3IB0lkEhDmgDjIkkGxEg3DABSIAglEA1GlmIqWgFQs7gDleAPI8aAWIgiAHIAAAgEgzlgD3IAuFHEgv6gD3IAeFHEgrzgD3IARFREggugD5IgZGXEglOAB8IAsl1A96j5IA1G+EgoOABrIgPlkA68j5IBVGoAWCjhIFGADEhOvgDNIAFAAEhMkAEJIh8Ad");
	this.shape_48.setTransform(493.9,289.3);

	this.shape_49 = new cjs.Shape();
	this.shape_49.graphics.f("#000000").s().p("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");
	this.shape_49.setTransform(493.8,289.3);

	this.shape_50 = new cjs.Shape();
	this.shape_50.graphics.f("#30262D").s().p("AgCABIAAgBIAFABg");
	this.shape_50.setTransform(-4.9,449.3);

	this.shape_51 = new cjs.Shape();
	this.shape_51.graphics.f("#CCA89B").s().p("AiIABQgIgPAdAAID+AYIifAFg");
	this.shape_51.setTransform(-3.1,410.6);

	this.shape_52 = new cjs.Shape();
	this.shape_52.graphics.f("#603E31").s().p("AjRHLQgUgVAAgbIAAswQAAgdAUgRQAZgUAfABQAfgBAUAUIAAACIgUgCQgcAAgXATQgYAWAAAbIAAMvIAUAsgAC8GuIAAswQAAgcgYgUQgYgUgnADIgbAAIAHgCQAYgXAlAAQAkAAAYAXQAbARAAAdIAAMvQAAAbgbAWIgiARQAUgUAAgYg");
	this.shape_52.setTransform(5.1,494.4);

	this.shape_53 = new cjs.Shape();
	this.shape_53.graphics.lf(["#B17A67","#C09383"],[0.514,1],27.7,0.1,-27.9,0.1).s().p("AkVKFIAAxMIDnAqIFEgWIAARiIlEAngAjdk1QgUARAAAdIAAMwQAAAbAUAWIAfARIAZACQAfAAAUgTQAYgWAAgbIAAswQAAgYgRgTIgHgDQgUgTgfAAQggAAgYATgABFk3IgIACQgTAUAAAYIAAMvQAAAbAbAUQAYAWAlAAIAagDIAigRQAbgWAAgbIAAsvQAAgdgbgRQgYgWgkAAQglAAgYAWgAjYnVIAAimQhMgsBHguQDFAbDqgYQBGArhGAsIAAC3Ij+AOgAjnq1IB1AOICegFIj9gaQgdAAAHARg");
	this.shape_53.setTransform(6.3,480.2);

	this.shape_54 = new cjs.Shape();
	this.shape_54.graphics.f("#925E4B").s().p("Ai+KmIgTgsIAAsvQAAgbAYgWQAWgTAdAAIAUACIAHAAQARAUAAAYIAAMvQAAAbgYAWQgUATgfAAgABFKQQgbgTAAgbIAAswQAAgYATgTIAbAAQAngDAZAUQAYATAAAdIAAMwQAAAYgUAUIgaACQglAAgYgWgAkVmLIA9gPICsAgID+gPIBEARIlEAWgAjdqZQD4geC3AgQhwAMhmAAQhyAAhngOg");
	this.shape_54.setTransform(6.3,474.2);

	this.shape_55 = new cjs.Shape();
	this.shape_55.graphics.lf(["#281B15","#C09383"],[0.012,1],0.1,-7.5,0.1,7.4).s().p("AgYBKQg/hCA/hRIAUADIA9AEQg5BMAyBAg");
	this.shape_55.setTransform(466.9,525.7);

	this.shape_56 = new cjs.Shape();
	this.shape_56.graphics.lf(["#281B15","#C09383"],[0.012,1],0.5,-5.1,-0.4,5.2).s().p("AgeAwIg/gEQBXAAAZhdQAnAaAkAKQgdA/hGAAQgMAAgNgCg");
	this.shape_56.setTransform(475.8,514.1);

	this.shape_57 = new cjs.Shape();
	this.shape_57.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-7.4,0,7.5).s().p("AgZBLQhAhEBAhRIATACIBAAGQg7BLAzBCg");
	this.shape_57.setTransform(39.3,523.1);

	this.shape_58 = new cjs.Shape();
	this.shape_58.graphics.lf(["#281B15","#C09383"],[0.012,1],0.4,-5,-0.5,5.3).s().p("AgdAyIhCgFQBaACAchiIBJAnQgfBAhDAAQgNAAgOgCg");
	this.shape_58.setTransform(48.2,511.3);

	this.shape_59 = new cjs.Shape();
	this.shape_59.graphics.f("#B07966").s().p("AbHAfIgYgqIL8AFIAAAqgAEnAYIAAgqITRAFIAAApgAzCALIgZgpIVJAKIgdAqgEgmqAAHIAAgqIQWAFIAAApg");
	this.shape_59.setTransform(259.6,494.7);

	this.shape_60 = new cjs.Shape();
	this.shape_60.graphics.lf(["#281B15","#C09383"],[0.012,1],0.2,-7.5,0.2,7.4).s().p("AgZBKQhAhFBAhOIAVAAIA+AHQg6BMA1BAg");
	this.shape_60.setTransform(178.5,523.7);

	this.shape_61 = new cjs.Shape();
	this.shape_61.graphics.lf(["#281B15","#C09383"],[0.012,1],2.3,-5.5,-1.2,5.8).s().p("AgeAyIhAgHQBZAAAYhdIBLAkQgdBBhHAAQgLAAgNgBg");
	this.shape_61.setTransform(187.5,512);

	this.shape_62 = new cjs.Shape();
	this.shape_62.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-5.3,0,5.1).s().p("AhegPQAlgJAlgbQAbBfBYAAIg/AHIgUABQhJAAghhDg");
	this.shape_62.setTransform(220.1,512.1);

	this.shape_63 = new cjs.Shape();
	this.shape_63.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-7.4,0,7.5).s().p("Ag0BJQA2g9g7hMIA9gHIAWgDQA/BTg/BCg");
	this.shape_63.setTransform(229.1,523.8);

	this.shape_64 = new cjs.Shape();
	this.shape_64.graphics.f("#97604D").s().p("AWHFyIAAgbIAAnGIAAgrIAAhOIAAgkIAAhTIB8AAIAABJIADAAIAAKIgAA1FsIAArVIB+ADIAABGIADAAIAAAOIAAAlIAABOIAAAsIAAHCIgDAggA4FFXIAAgUIAAnDIAAgrIAAhOIAAgnIAAhQIB/AAIAALJg");
	this.shape_64.setTransform(270.9,508.8);

	this.shape_65 = new cjs.Shape();
	this.shape_65.graphics.f("#DBC1B9").s().p("AavAbIA2giIACAAILEAFIAAAjgAEnAXIAAgjIHgACIAAgCILxAFIAAAigAzbAKIAAgCIA4ggIAAgFITXAOIA6AjgEgmqAADIAAgiIQWAEIAAAlg");
	this.shape_65.setTransform(259.6,482.6);

	this.shape_66 = new cjs.Shape();
	this.shape_66.graphics.f("#794D3E").s().p("AWPkWIgDAAIAAhJIBwAAIAABNIADAAIAAAGIgDAAIg1AkIAABQIAYAsIAAHDIhQAbgAiVFNIAAnCIAdgsIAAhPIg6gkIAAgCIAHgDIAAhQIBrAAIAALVgA3+lwIBzAAIAABNIAAAGIg4AiIAAACIAABOIAYArIAAHAIhTAZg");
	this.shape_66.setTransform(282.8,508.8);

	this.shape_67 = new cjs.Shape();
	this.shape_67.graphics.f("#B17A67").s().p("AYqBMIAAiJINZAIIAACIgAZxAlIK4AFIAAhCIq4gFgAVXBJIAAiGIAMAAIAACJgAFoBHIAAiIINfAEIAACGgAG8AeILEAFIAAhDIrEgCgAqVBAIAAiIINXAEIAACJgApLAZIK2ACIAAhBIq2gGgA6OA4IAAiGINWAGIAACFgA5HASIK4AFIAAhFIq4AAgEgmCAA0IAAgqIH3AGIAAhAIn3gFIAAgdIJRAEIAACGg");
	this.shape_67.setTransform(255.6,460.5);

	this.shape_68 = new cjs.Shape();
	this.shape_68.graphics.f("#C09383").s().p("AbHHWIAAnEILkAFIAADWQgugPgRgxIgNgrIhKAkIhFAAQgkgKgngaQgbBfhYAAIgTgDQg/BTA/BCIBLAAIFhACIAABkgAEnHPIAAnFITRAFIAAHHgAJLFuIF0AFQA0hCgvhEIgHAAQhrAOgghQIgKgsIghARIglgTIgHApQggBThrgRIgIAAQgtBCAwBEgAzCHAIAAnAIUTAIIAAHFgAtFFfIBQAAIGQAAIBQACQA/hBg/hVIgWACQhYAAgdhhQglAbgkAJIhtAFIgbgFIhMgkQgaBfhYAAIgWAAQg/BQA/BEgEgmqAG8IAAnBIQWAFIAAHDgEgi0AFaIBNAAIGtADQAdgngFgnIgHgZIgPgdIgFgCQhrAOgghSIgJgsQgnAbgnAJIhCAAIhJgnQgdBkhagCIgTgDQhABTBABEgAavgYIAAhQIL8AFIAABQgAEngfIAAhOITRAFIAABOgAzbgsIAAhNIVJAJIAABOgEgmqgAwIAAhRIQWAIIAABNgEgmqgELIAAgiIJRAEIAAiIIpRgFIAAgiMBNVAAdIAADNgAYCkVINZAHIAAiKItZgIgAUvkYIAMADIAAiLIgMAAgAFAkaINfACIAAiIItfgFgAq9khINXAFIAAiLItXgFgA62kpINWAFIAAiIItWgFgAZJk8IAAhEIK4AFIAABEgAGUlDIAAhCILEACIAABFgApzlIIAAhHIK2AFIAABEgA5vlPIAAhCIK4AAIAABGgEgmqgFXIAAhBIH3AEIAABCg");
	this.shape_68.setTransform(259.6,496);

	this.shape_69 = new cjs.Shape();
	this.shape_69.graphics.f("#30262D").s().p("AgCABIAFgBIAAABg");
	this.shape_69.setTransform(992,449.3);

	this.shape_70 = new cjs.Shape();
	this.shape_70.graphics.f("#CCA89B").s().p("AiKAKID+gYQAdAAgHAPIh1AOg");
	this.shape_70.setTransform(990.2,410.6);

	this.shape_71 = new cjs.Shape();
	this.shape_71.graphics.f("#603E31").s().p("ADGGwIAAsvQAAgbgZgWQgVgTgeAAIgTACIAAgCQATgUAgABQAggBAYAUQATARAAAdIAAMwQAAAbgTAVIggARgAjKHJQgagWAAgbIAAsvQAAgdAagRQAZgXAkAAQAlAAAYAXIAHACIgbAAQgmgDgZAUQgYAUAAAcIAAMwQAAAYATAUg");
	this.shape_71.setTransform(982,494.4);

	this.shape_72 = new cjs.Shape();
	this.shape_72.graphics.f("#925E4B").s().p("ABzKVQgYgWAAgbIAAsvQAAgYARgUIAHAAIATgCQAeAAAWATQAYAWAAAbIAAMvIgUAsIgYACQgfAAgUgTgAibKkQgUgUAAgYIAAswQAAgdAYgTQAZgUAmADIAbAAQAUATAAAYIAAMwQAAAbgbATQgYAWgkAAgAkVl4IBEgRID9APICtggIA9APIjnApgAjRqXQC3ggD4AeQhnAOhzAAQhlAAhwgMg");
	this.shape_72.setTransform(980.8,474.2);

	this.shape_73 = new cjs.Shape();
	this.shape_73.graphics.lf(["#B17A67","#C09383"],[0.514,1],-27.6,0.1,28,0.1).s().p("AkVKvIAAxiIFEAWIDngqIAARMIjnBRgABzk1IgHADQgRATAAAYIAAMwQAAAbAYAWQAUATAfAAIAYgCIAggRQAUgWgBgbIAAswQABgdgUgRQgYgTggAAQgfAAgUATgAi+k3QgaARAAAdIAAMvQAAAbAaAWIAjARIAbADQAkAAAYgWQAbgUAAgbIAAsvQAAgYgUgUIgHgCQgYgWgkAAQglAAgZAWgAjRnEIAAi3QhHgsBHgrQDqAYDFgbQBHAuhMAsIAACmIitAfgAgsqsICfAFIB1gOQAHgRgdAAg");
	this.shape_73.setTransform(980.8,480.2);

	this.shape_74 = new cjs.Shape();
	this.shape_74.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.1,-7.5,-0.1,7.4).s().p("AgwBKQAxhAg5hMIA+gEIATgDQA/BRg/BCg");
	this.shape_74.setTransform(520.2,525.7);

	this.shape_75 = new cjs.Shape();
	this.shape_75.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.4,-5.1,0.5,5.2).s().p("AhdgNQAkgKAngaQAZBdBYAAIhAAEQgNACgMAAQhGAAgdg/g");
	this.shape_75.setTransform(511.3,514.1);

	this.shape_76 = new cjs.Shape();
	this.shape_76.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-5.3,0,5.1).s().p("AgeAzIg/gHQBXAAAchfQAkAbAlAJQghBDhIAAIgUgBg");
	this.shape_76.setTransform(767,512.1);

	this.shape_77 = new cjs.Shape();
	this.shape_77.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-7.4,0,7.5).s().p("AgZhKIAVADIA+AHQg7BMA2A9IhOACQhAhCBAhTg");
	this.shape_77.setTransform(758,523.8);

	this.shape_78 = new cjs.Shape();
	this.shape_78.graphics.lf(["#281B15","#C09383"],[0.012,1],-2.2,-5.5,1.3,5.8).s().p("AhdgOIBLgkQAZBdBXAAIg/AHQgMABgMAAQhHAAgdhBg");
	this.shape_78.setTransform(799.6,512);

	this.shape_79 = new cjs.Shape();
	this.shape_79.graphics.f("#B07966").s().p("EgmqgAGIL8gFIgYAqIrkAFgA34gNITSgFIAAAqIzSAEgAhtgUIVJgKIgYApI0UALgAWVgeIQWgFIAAAqIwWAEg");
	this.shape_79.setTransform(727.5,494.7);

	this.shape_80 = new cjs.Shape();
	this.shape_80.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.2,-5,0.6,5.3).s().p("AhegMQAkgJAlgeQAaBiBbgCIhCAFQgOACgNAAQhDAAgehAg");
	this.shape_80.setTransform(938.9,511.3);

	this.shape_81 = new cjs.Shape();
	this.shape_81.graphics.lf(["#281B15","#C09383"],[0.012,1],0,-7.4,0,7.5).s().p("AgxBLQAzhCg7hLIBAgGIATgCQBABRhABEg");
	this.shape_81.setTransform(947.8,523.1);

	this.shape_82 = new cjs.Shape();
	this.shape_82.graphics.lf(["#281B15","#C09383"],[0.012,1],-0.2,-7.5,-0.2,7.4).s().p("Ag0BKQA0hAg5hMIA9gHIAWAAQA/BOg/BFg");
	this.shape_82.setTransform(808.6,523.7);

	this.shape_83 = new cjs.Shape();
	this.shape_83.graphics.f("#97604D").s().p("A4FFyIAAqIIACAAIAAhJIB9AAIAABTIAAAkIAABOIAAArIAAHGIAAAbgAi1FPIAAnCIAAgsIAAhOIAAglIAAgOIACAAIAAhGIB/gDIAALVIh/ADgAWIlwIB+AAIAABQIAAAnIAABOIAAArIAAHDIAAAUIh+ACg");
	this.shape_83.setTransform(716.2,508.8);

	this.shape_84 = new cjs.Shape();
	this.shape_84.graphics.f("#DBC1B9").s().p("EgmqgACILEgFIACAAIA2AiIr8AGgA34gHILzgFIAAACIHfgCIAAAjIzSAEgAgzgPITXgOIAAAFIA4AgIAAACI1JAKgAWVgbIQWgEIAAAiIwWAHg");
	this.shape_84.setTransform(727.5,482.6);

	this.shape_85 = new cjs.Shape();
	this.shape_85.graphics.f("#794D3E").s().p("A3eFXIAAnDIAYgsIAAhQIg1gkIgDAAIAAgGIADAAIAAhNIBwAAIAABJIgDAAIAAKIgABBlpIBrAAIAABQIAIADIAAACIg7AkIAABPIAdAsIAAHCIhVAfgAWsFAIAAnAIAYgrIAAhOIAAgCIg3giIAAgGIAAhNIByAAIAALJg");
	this.shape_85.setTransform(704.2,508.8);

	this.shape_86 = new cjs.Shape();
	this.shape_86.graphics.f("#C09383").s().p("EgmqAF1IFhgCIBLAAQA/hCg/hTIgTADQhYAAgbhfQgnAagkAKIhEAAQglgHgngdIgMArQgRAxguAPIAAjWILkgFIAAHEIrkADgA34APITSgFIAAHFIzSAHgAu+FzIF0gFQAxhEgvhCIgHAAQhrARgghTIgHgpIgkATIgigRIgKAsQggBQhrgOIgHAAQguBEAzBCgAhQAIIUUgIIAAHAI0UANgAEWFhIBQgCIGPAAIBRAAQA/hEg/hQIgWAAQhYAAgbhfIhLAkIgbAFIhtgFQglgJgkgbQgdBhhYAAIgWgCQg/BVA/BBgAWVAAIQWgFIAAHBIwWAHgAa5DZIgPAdIgHAZQgFAnAdAnIGtgDIBNAAQBAhEhAhTIgTADQhaACgdhkQglAegkAJIhCAAQgngJgngbIgKAsQgfBShrgOgEgmqgBjIL8gFIAABQIr8AFgA34hoITSgFIAABOIzSAFgAhthwIVJgJIAABNI1JAKgAWVh5IQWgIIAABRIwWAEgEgmqgG7MBNVgAdIAAAiIpSAFIAACIIJSgEIAAAiMhNVAAdgEglagGYIAACKINZgHIAAiLgA06kVIAMgDIAAiIIgMAAgAyemgIAACIINggCIAAiLgAiZmnIAACLINWgFIAAiLgANgmsIAACIINWgFIAAiIgEgkAgF7IK4gFIAABEIq4AFgAxYmDILEgCIAABCIrEAFgAhBmKIK1gFIAABHIq1ACgAO4mRIK4AAIAABCIq4AEgAezmUIH4gEIAABBIn4AFg");
	this.shape_86.setTransform(727.5,496);

	this.shape_87 = new cjs.Shape();
	this.shape_87.graphics.f("#B17A67").s().p("EgmCgA1INYgIIAACJItYAHgEgkogAYIAABCIK4gFIAAhCgA1ig9IAMAAIAACGIgMADgAzGg9INggEIAACIItgACgAyAggIAABDILEgFIAAhAgAjChEINXgEIAACIItXAFgAhpgmIAABBIK1gCIAAhFgAM4hIINWgGIAACGItWAFgAOQAXIK3gFIAAhAIq3AAgAcxhOIJSgEIAAAdIn4AFIAABAIH4gGIAAAqIpSAEg");
	this.shape_87.setTransform(731.5,460.5);

	this.shape_88 = new cjs.Shape();
	this.shape_88.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,-100.6,4.4,-100.6).s().p("AgsgGIBZAAQgbANgVAAQgWAAgTgNg");
	this.shape_88.setTransform(801.9,457.6);

	this.shape_89 = new cjs.Shape();
	this.shape_89.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,23.5,4.4,23.5).s().p("AgsMFIAA3tQAtg4AsA4IAAXtg");
	this.shape_89.setTransform(801.9,379.6);

	this.shape_90 = new cjs.Shape();
	this.shape_90.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-7,24.5,6.6,24.5).s().p("AhDMcIAA4XQBDg/BDA/IAAYXg");
	this.shape_90.setTransform(393.6,374.6);

	this.shape_91 = new cjs.Shape();
	this.shape_91.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,-100.7,4.4,-100.7).s().p("AgsgGIBZAAQgbANgVAAQgWAAgTgNg");
	this.shape_91.setTransform(67.9,456.9);

	this.shape_92 = new cjs.Shape();
	this.shape_92.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,23.5,4.4,23.5).s().p("AgsMFIAA3tQAtg5AsA5IAAXtg");
	this.shape_92.setTransform(67.9,378.8);

	this.shape_93 = new cjs.Shape();
	this.shape_93.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,24.2,4.4,24.2).s().p("AgsMbIAA4YQAtg5ArA5IAAYYg");
	this.shape_93.setTransform(225.6,377.8);

	this.shape_94 = new cjs.Shape();
	this.shape_94.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,-100.7,4.4,-100.7).s().p("AgsgGIBYAAQgaANgVAAQgWAAgTgNg");
	this.shape_94.setTransform(639.9,459.4);

	this.shape_95 = new cjs.Shape();
	this.shape_95.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,23.5,4.4,23.5).s().p("AgsMFIAA3tQAsg5AsA5IAAXtg");
	this.shape_95.setTransform(639.9,381.3);

	this.shape_96 = new cjs.Shape();
	this.shape_96.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,-100.7,4.4,-100.7).s().p("AgsgGIBYAAQgaANgVAAQgWAAgTgNg");
	this.shape_96.setTransform(961.1,455.2);

	this.shape_97 = new cjs.Shape();
	this.shape_97.graphics.lf(["#55280B","#FB890B","#F9BE7D","#FB890B"],[0.075,0.69,0.831,0.949],-4.6,23.5,4.4,23.5).s().p("AgsMFIAA3tQAtg4ArA4IAAXtg");
	this.shape_97.setTransform(961.1,377.1);

	this.instance_6 = new lib.shape20("synched",0);

	this.instance_7 = new lib.text19();
	this.instance_7.setTransform(854.1,344.8,0.859,0.859);
	this.instance_7.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];

	this.instance_8 = new lib.shape18("synched",0);

	this.instance_9 = new lib.text17();
	this.instance_9.setTransform(695.3,344.8,0.859,0.859);
	this.instance_9.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];

	this.instance_10 = new lib.shape16("synched",0);

	this.instance_11 = new lib.text15();
	this.instance_11.setTransform(485.2,344.8,0.859,0.859);
	this.instance_11.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];

	this.instance_12 = new lib.shape14("synched",0);

	this.shape_98 = new cjs.Shape();
	this.shape_98.graphics.f("#BCBCBC").s().p("AgtE1IAAgBIAAAAIAAABgAhEEvIAAAAIABAAIAAAAgAhZEnIAAgBIABAAIAAABgAhcElIAAgBIABAAIAAABgAhpEeIAAgBIABAAIAAABgAh3EVIAAgBIABAAIAAABgACsEHIAAgBIAAAAIAAABgAieD0IAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgADLDqIAAgBIABAAIAAABgAA2DpIAAAAIAAAAIAAAAgAA7DpIAAgBIABAAIAAABgABADoIAAgBIABAAIAAABgAiqDoIAAgBIABAAIAAABgABGDnIAAgBIABAAIAAABgADQDlIAAgBIAAAAIAAABgABoDgIAAgBIABAAIAAABgAhdDcIAAgBIABAAIAAABgABhDXIAAgBIABAAIAAABgABdDSIAAgBIABAAIAAABgAjnDQIAAgBIABAAIAAABgAjjDMIAAAAIABAAIAAAAgAkHDKIAAgBIABAAIAAABgAjfDJIAAAAIABAAIAAAAgAjGDJIAAgBIABAAIAAABgAh6DHIAAgBIABAAIAAABgAjbDGIAAgBIABAAIAAABgAjKDEIAAgBIABAAIAAABgAjXDDIAAgBIABAAIAAABgAh/DCIAAgBIAAAAIAAABgABLDBIAAgBIABAAIAAABgAjTDAIAAgBIABAAIAAABgAkEC/IAAgBIAAAAIAAABgAA8C2IAAgBIABAAIAAABgAj+CxIAAgBIABAAIAAABgAAoCrIAAAAIABAAIAAAAgAj4CoIAAgBIABAAIAAABgACPCjIAAgBIAAAAIAAABgAjxCfIAAgBIABAAIAAABgAgECdIAAAAIABAAIAAAAgAgMCcIAAgBIAAAAIAAABgAgSCbIAAgBIABAAIAAABgABvCZIAAAAIAAAAIAAAAgAgeCZIAAAAIAAAAIAAAAgABnCWIAAgBIABAAIAAABgABgCTIAAgBIABAAIAAABgABeCSIAAAAIABAAIAAAAgABYCPIAAgBIABAAIAAABgAjgCOIAAgBIABAAIAAABgAigCMIAAgBIABAAIAAABgABPCKIAAgBIAAAAIAAABgAjZCIIAAgBIAAAAIAAABgAicCHIAAgBIABAAIAAABgAiXCBIAAgBIABAAIAAABgAjOB/IAAgBIABAAIAAABgAjKB8IAAAAIAAAAIAAAAgAjHB6IAAgBIAAAAIAAABgAA8B4IAAAAIABAAIAAAAgABGBrIAAgBIABAAIAAABgAivBqIAAgBIABAAIAAABgADhBoIAAgBIABAAIAAABgADoBmIAAAAIABAAIAAAAgAipBmIAAgBIABAAIAAABgADtBlIAAgBIABAAIAAABgADJBkIAAgBIABAAIAAABgADyBjIAAgBIABAAIAAABgADHBjIAAgBIABAAIAAABgADFBiIAAAAIAAAAIAAAAgAD2BiIAAgBIABAAIAAABgADCBiIAAgBIABAAIAAABgADABhIAAgBIABAAIAAABgAC+BgIAAgBIAAAAIAAABgAC7BfIAAAAIABAAIAAAAgAC5BfIAAgBIABAAIAAABgAC3BeIAAgBIAAAAIAAABgAC0BdIAAgBIABAAIAAABgACyBcIAAgBIABAAIAAABgACwBbIAAAAIAAAAIAAAAgABmBZIAAgBIABAAIAAABgAEDBYIAAgBIABAAIAAABgAA0BWIAAgBIABAAIAAABgAB2BTIAAgBIAAAAIAAABgABABSIAAgBIABAAIAAABgAgUBSIAAgBIAAAAIAAABgABDBRIAAgBIABAAIAAABgAgcBRIAAgBIABAAIAAABgAEHBPIAAgBIABAAIAAABgACBBPIAAgBIAAAAIAAABgACDBOIAAgBIABAAIAAABgABPBNIAAgBIABAAIAAABgABSBMIAAAAIAAAAIAAAAgABhBIIAAgBIAAAAIAAABgAAHBEIAAgBIAAAAIAAABgABvBDIAAgBIABAAIAAABgAB+A+IAAAAIABAAIAAAAgACBA+IAAgBIAAAAIAAABgACNA6IAAgBIABAAIAAABgACPA5IAAgBIABAAIAAABgAECA2IAAAAIAAAAIAAAAgACcA1IAAgBIABAAIAAABgAAWA1IAAgBIABAAIAAABgAD+A0IAAgBIABAAIAAABgACeA0IAAgBIABAAIAAABgACtAvIAAAAIABAAIAAAAgAC8ArIAAgBIABAAIAAABgAAaAqIAAgBIABAAIAAABgADLAmIAAgBIABAAIAAABgAiYAjIAAgBIAAAAIAAABgADaAhIAAAAIABAAIAAAAgADlAhIAAgBIAAAAIAAABgADcAhIAAgBIABAAIAAABgAAdAfIAAgBIAAAAIAAABgAihAZIAAAAIABAAIAAAAgAipADIAAAAIABAAIAAAAgAAlgRIAAgBIABAAIAAABgAAtgXIAAgBIABAAIAAABgAikg0IAAgBIABAAIAAABgABSg4IAAgBIAAAAIAAABgAilg4IAAgBIABAAIAAABgAggg9IAAgBIABAAIAAABgAiohEIAAgBIABAAIAAABgAiqhKIAAgBIABAAIAAABgABjhTIAAgBIABAAIAAABgABkhXIAAgBIABAAIAAABgAiwhdIAAAAIABAAIAAAAgABohhIAAgBIAAAAIAAABgAAChhIAAgBIABAAIAAABgAiyhjIAAgBIABAAIAAABgAAHhmIAAgBIABAAIAAABgACEiCIAAAAIABAAIAAAAgAg0iIIAAgBIAAAAIAAABgAgsiNIAAAAIABAAIAAAAgAghiQIAAgBIABAAIAAABgAgbiSIAAgBIAAAAIAAABgAgYiTIAAgBIABAAIAAABgAjJiWIAAgBIABAAIAAABgAhLiaIAAgBIABAAIAAABgAhGibIAAgBIABAAIAAABgAClidIAAgBIABAAIAAABgAhAidIAAgBIABAAIAAABgAhYidIAAgBIABAAIAAABgAheifIAAgBIABAAIAAABgAhkiiIAAgBIABAAIAAABgAjOijIAAgBIAAAAIAAABgACpilIAAgBIABAAIAAABgAjMilIAAgBIABAAIAAABgAjKimIAAgBIABAAIAAABgAjHioIAAgBIAAAAIAAABgAjFiqIAAAAIABAAIAAAAgAjDirIAAgBIABAAIAAABgAiBitIAAgBIABAAIAAABgAjAitIAAgBIAAAAIAAABgAi+iuIAAgBIABAAIAAABgAiHivIAAgBIABAAIAAABgAi8iwIAAgBIABAAIAAABgAiNixIAAgBIAAAAIAAABgAi5ixIAAgBIABAAIAAABgAgQiyIAAgBIAAAAIAAABgAi3izIAAgBIABAAIAAABgAi1i1IAAAAIABAAIAAAAgAgFi6IAAgBIAAAAIAAABgAAAjAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABIgBAAIAAABgACIjEIAAgBIAAAAIAAABgAB0jJIAAgBIABAAIAAABgABjjMIAAgBIABAAIAAABgABZjOIAAAAIABAAIAAAAgAAOjTIAAgBIAAAAIAAABgAAPjWIAAgBIABAAIAAABgAAQjYIAAgBIABAAIAAABgAAVjsIAAAAIABAAIAAAAgABFj9IAAgBIABAAIAAABgABHkEIAAgBIABAAIAAABgABIkLIAAgBIABAAIAAABgABKkSIAAgBIABAAIAAABgAAVkVIAAgBIABAAIAAABgAAVkYIAAgBIAAAAIAAABgABLkZIAAgBIABAAIAAABgAAUkbIAAgBIABAAIAAABgAATkfIAAAAIABAAIAAAAgABNkgIAAgBIABAAIAAABgAASkiIAAgBIABAAIAAABgAASklIAAgBIAAAAIAAABgABAkzIAAgBIAAAAIAAABg");
	this.shape_98.setTransform(308.4,375.8);

	this.shape_99 = new cjs.Shape();
	this.shape_99.graphics.f("#828282").s().p("AAeE7IAAgBIAAAAIAAABgAAvE6IAAgBIABAAIAAABgAg5E6IAAgBIABAAIAAABgAA1E5IAAgBIABAAIAAABgAg8E5IAAgBIABAAIAAABgAg/E4IAAgBIABAAIAAABgAA/E3IAAgBIABAAIAAABgAhEE2IAAAAIABAAIAAAAgAhLE0IAAgBIABAAIAAABgABUEzIAAgBIAAAAIAAABgABaEyIAAgBIABAAIAAABgAhUEwIAAgBIABAAIAAABgABiEvIAAAAIABAAIAAAAgACEEkIAAgBIABAAIAAABgAhsEjIAAgBIAAAAIAAABgAhxEgIAAgBIABAAIAAABgAh2EcIAAAAIABAAIAAAAgACYEZIAAAAIABAAIAAAAgAh7EZIAAgBIABAAIAAABgAh+EWIAAgBIAAAAIAAABgAiBEUIAAgBIAAAAIAAABgACqEOIAAAAIAAAAIAAAAgAiKENIAAgBIABAAIAAABgAiQEIIAAgBIABAAIAAABgACzEHIAAgBIABAAIAAABgAiXECIAAgBIABAAIAAABgAC+D+IAAgBIABAAIAAABgAC/D9IAAgBIAAAAIAAABgAC/D8IAAgBIABAAIAAABgADBD7IAAgBIABAAIAAABgADCD6IAAgBIABAAIAAABgADFD3IAAgBIABAAIAAABgADGD2IAAgBIAAAAIAAABgADGD1IAAgBIABAAIAAABgADHD0IAAAAIABAAIAAAAgADHD0gAA3DwIAAAAIABAAIAAAAgAA9DwIAAgBIABAAIAAABgAhEDwIAAgBIABAAIAAABgABCDvIAAgBIABAAIAAABgAhGDvIAAgBIABAAIAAABgADNDuIAAgBIABAAIAAABgABIDuIAAgBIABAAIAAABgADODtIAAAAIAAAAIAAAAgAj9DoIAAgBIABAAIAAABgAiyDmIAAAAIABAAIAAAAgADUDmIAAgBIABAAIAAABgABnDmIAAgBIABAAIAAABgAizDmIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgAj5DlIAAgBIABAAIAAABgADWDiIAAAAIABAAIAAAAgAj1DiIAAgBIABAAIAAABgABiDfIAAgBIABAAIAAABgAjxDeIAAAAIAAAAIAAAAgAi7DdIAAgBIABAAIAAABgABfDbIAAAAIABAAIAAAAgAjtDbIAAAAIAAAAIAAAAgAhrDbIAAgBIABAAIAAABgAjpDYIAAgBIAAAAIAAABgAjADXIAAgBIABAAIAAABgAjmDVIAAgBIABAAIAAABgABZDUIAAgBIABAAIAAABgAh0DTIAAAAIABAAIAAAAgABXDTIAAgBIABAAIAAABgABXDSIAAgBIAAAAIAAABgAjiDSIAAgBIABAAIAAABgABVDQIAAAAIABAAIAAAAgAjeDPIAAgBIABAAIAAABgAjJDMIAAAAIABAAIAAAAgAh+DMIAAgBIABAAIAAABgAjaDMIAAgBIABAAIAAABgABODKIAAgBIABAAIAAABgAkGDKIAAgBIABAAIAAABgAjMDIIAAAAIABAAIAAAAgAjWDIIAAAAIABAAIAAAAgAjNDIIAAgBIABAAIAAABgAiDDHIAAgBIABAAIAAABgABJDGIAAgBIABAAIAAABgAjSDFIAAAAIABAAIAAAAgAkEDFIAAgBIABAAIAAABgABGDEIAAgBIABAAIAAABgAiMC+IAAgBIAAAAIAAABgAiNC9IAAAAIgBAAIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABIABAAIAAAAgAiQC6IAAAAIAAAAIAAAAgAAqCzIAAgBIABAAIAAABgAAjCwIAAgBIABAAIAAABgAiaCwIAAgBIgBAAIAAAAIABAAIAAAAIAAAAIAAABgAj5CwIAAgBIABAAIAAABgACvCvIAAAAIABAAIAAAAgAicCvIAAgBIABAAIAAABgACiCuIAAgBIABAAIAAABgAidCuIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAIAAAAIAAABIABAAIAAABIABAAIAAABgACNCqIAAgBIAAAAIAAABgAARCqIAAgBIABAAIAAABgAihCqIAAgBIABAAIAAABgACJCpIAAgBIABAAIAAABgAj0CpIAAgBIABAAIAAABgACGCoIAAgBIABAAIAAABgAAMCoIAAgBIAAAAIAAABgAB+CmIAAgBIABAAIAAABgAjwCkIAAAAIABAAIAAAAgAjvCkIAAgBIABAAIAAABgAjvCkgAjuCjIAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABgAjuCjgAiqCgIAAgBIABAAIAAABgAgvCfIAAgBIAAAAIAAABgAioCbIAAgBIABAAIAAABgAinCaIAAgBIABAAIAAABgAjkCZIAAgBIABAAIAAABgAjjCYIAAgBIABAAIAAABgAjjCYgAjiCXIAAgBIAAAAIAAABgAjiCXgAjiCWIAAgBIABAAIAAABgAjiCWgAijCVIAAAAIABAAIAAAAgAjcCRIAAAAIABAAIAAAAgAieCQIAAgBIAAAAIAAABgAhOCMIAAgBIABAAIAAABgAiaCKIAAAAIABAAIAAAAgAjQCHIAAAAIABAAIAAAAgAA/CHIAAgBIABAAIAAABgAiVCFIAAgBIABAAIAAABgAjJCDIAAgBIAAAAIAAABgAjGCAIAAgBIAAAAIAAABgAiQB/IAAAAIAAAAIAAAAgAA7B/IAAgBIABAAIAAABgAjBB8IAAAAIABAAIAAAAgAi+B7IAAgBIAAAAIAAABgAA+B6IAAgBIAAAAIAAABgAiMB6IAAgBIABAAIAAABgABBB2IAAgBIABAAIAAABgABCB1IAAgBIAAAAIAAABgABCB1gAiHB0IAAAAIABAAIAAAAgAiwBxIAAAAIAAAAIAAAAgAiCBvIAAgBIABAAIAAABgADuBrIAAgBIABAAIAAABgADKBrIAAgBIAAAAIAAABgADxBqIAAgBIAAAAIAAABgADHBqIAAgBIABAAIAAABgADFBpIAAAAIABAAIAAAAgAh+BpIAAAAIABAAIAAAAgADDBpIAAgBIAAAAIAAABgADABoIAAgBIABAAIAAABgAC+BnIAAgBIABAAIAAABgAC8BmIAAAAIAAAAIAAAAgAD7BmIAAgBIABAAIAAABgAC5BmIAAgBIABAAIAAABgAC3BlIAAgBIABAAIAAABgAh6BlIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAC0BkIAAgBIABAAIAAABgACyBjIAAgBIABAAIAAABgACwBiIAAAAIABAAIAAAAgAh1BfIAAgBIABAAIAAABgAh0BeIAAAAIABAAIAAAAgAAyBeIAAgBIABAAIAAABgAAMBdIAAgBIABAAIAAABgAAFBcIAAgBIAAAAIAAABgABBBZIAAgBIABAAIAAABgAgvBXIAAgBIAAAAIAAABgAB/BWIAAgBIABAAIAAABgAg3BWIAAgBIABAAIAAABgACCBVIAAgBIAAAAIAAABgACEBUIAAgBIABAAIAAABgABQBUIAAgBIAAAAIAAABgABeBQIAAgBIABAAIAAABgABhBPIAAgBIABAAIAAABgABtBLIAAgBIABAAIAAABgABwBKIAAgBIAAAAIAAABgAACBJIAAgBIABAAIAAABgAB8BGIAAgBIABAAIAAABgAAABGIAAgBIABAAIAAABgAB/BFIAAAAIAAAAIAAAAgAAABDIAAgBIAAAAIAAABgAEGBBIAAAAIABAAIAAAAgACNBBIAAgBIABAAIAAABgAgDBAIAAgBIABAAIAAABgAEDA/IAAgBIAAAAIAAABgAgFA9IAAgBIABAAIAAABgACcA8IAAgBIABAAIAAABgAAXA6IAAgBIAAAAIAAABgAgIA6IAAgBIABAAIAAABgACrA3IAAgBIABAAIAAABgACtA2IAAAAIABAAIAAAAgADyAzIAAgBIABAAIAAABgAC6AzIAAgBIABAAIAAABgAC8AyIAAgBIABAAIAAABgADvAwIAAgBIABAAIAAABgAhzAvIAAgBIABAAIAAABgADsAuIAAgBIABAAIAAABgADJAuIAAgBIABAAIAAABgADLAtIAAgBIABAAIAAABgAAaAsIAAgBIABAAIAAABgADpArIAAAAIABAAIAAAAgAhpArIAAgBIABAAIAAABgAiXArIAAgBIAAAAIAAABgADmApIAAgBIABAAIAAABgADaAoIAAAAIABAAIAAAAgAiaAoIAAAAIAAAAIAAAAgAhgAnIAAgBIABAAIAAABgAicAnIAAgBIABAAIAAABgAieAlIAAgBIABAAIAAABgAhWAjIAAgBIAAAAIAAABgAhNAfIAAgBIABAAIAAABgAikAcIAAgBIABAAIAAABgAhEAbIAAgBIABAAIAAABgAAeAXIAAgBIAAAAIAAABgAiqgHIAAgBIABAAIAAABgAipgIIAAgBIAAAAIAAABgAipgIgAipgJIAAgBIABAAIAAABgAiogKIAAAAIABAAIAAAAgAiogKgAingKIAAgBIABAAIAAABgAimgLIAAgBIABAAIAAABgAimgLgAilgMIAAgBIAAAAIAAABgAilgNIAAAAIABAAIAAAAgAilgNgAikgNIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABIgBAAIAAABgAArgPIAAgBIABAAIAAABgAiigQIAAgBIABAAIAAABgAAzgVIAAgBIABAAIAAABgAiigYIAAgBIABAAIAAABgAA7gcIAAAAIABAAIAAAAgABBggIAAgBIABAAIAAABgABCghIAAgBIAAAAIAAABgAikgqIAAgBIABAAIAAABgABOguIAAAAIABAAIAAAAgABPguIAAgBIABAAIAAABgAgkgxIAAgBIAAAAIAAABgABTgzIAAgBIABAAIAAABgAgig0IAAgBIABAAIAAABgAimg1IAAgBIABAAIAAABgABWg3IAAgBIABAAIAAABgAgfg4IAAgBIABAAIAAABgAing5IAAAAIABAAIAAAAgAgZg+IAAgBIAAAAIAAABgABchAIAAgBIABAAIAAABgAgIhQIAAgBIABAAIAAABgAgHhRIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAgHhRgABmhWIAAAAIAAAAIAAAAgAiwhWIAAgBIAAAAIAAABgAAAhYIAAgBIAAAAIAAABgAAFhdIAAgBIAAAAIAAABgAizhdIAAgBIABAAIAAABgAAyhtIAAgBIABAAIAAABgAAzhuIAAgBIAAAAIAAABgAAzhvIAAgBIABAAIAAAAIABAAIAAAAIgBAAIAAABgAAzhvgAA1hwIAAgBIABAAIAAABgAA2hxIAAgBIABAAIAAABgAA3hyIAAgBIAAAAIAAAAIABAAIAAAAIgBAAIAAABgAA3hygAA4hzIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAA6h1IAAgBIABAAIAAABgAA7h2IAAgBIAAAAIAAABgAA7h2gAA7h3IAAAAIABAAIAAAAgAA7h3gAA8h3IAAgBIABAAIAAABgAA9h4IAAgBIABAAIAAABgAA9h4gAA+h5IAAgBIAAAAIAAABgAA+h5gAA+h6IAAgBIABAAIAAAAIABAAIAAAAIgBAAIAAABgAA+h6gAg5h+IAAAAIABAAIAAAAgAg3h/IAAgBIABAAIAAABgAg2iAIAAgBIAAAAIAAABgACRiEIAAgBIABAAIAAABgAgwiEIAAgBIABAAIAAABgAgpiHIAAgBIABAAIAAABgACYiJIAAAAIAAAAIAAAAgABNiJIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgAggiKIAAgBIABAAIAAABgABMiLIAAgBIgBAAIAAgBIABAAIAAABIAAAAIAAABgABKiNIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAgAgWiNIAAAAIAAAAIAAAAgACeiOIAAgBIABAAIAAABgAA3iOIAAgBIAAAAIAAABgACfiPIAAgBIAAAAIAAABgACfiPgAA3iPIAAgBIABAAIAAABgAA3iPgACfiQIAAgBIABAAIAAABgAA4iQIAAgBIABAAIAAABgAgGiQIAAgBIABAAIAAABgAA5iRIAAAAIABAAIAAAAgAA6iRIAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABgAA6iRgAhIiUIAAAAIABAAIAAAAgAhTiUIAAAAIABAAIAAAAgAhCiVIAAgBIABAAIAAABgAhZiWIAAgBIABAAIAAABgACmiZIAAgBIABAAIAAABgAg0iaIAAgBIABAAIAAABgACpifIAAAAIABAAIAAAAgAgoifIAAAAIAAAAIAAAAgAh2ihIAAgBIABAAIAAABgACqijIAAAAIAAAAIAAAAgAh8ijIAAgBIABAAIAAABgAiCimIAAgBIABAAIAAABgACqinIAAgBIAAAAIAAABgAgLivIAAgBIABAAIAAABgAgIiyIAAAAIABAAIAAAAgAgDi1IAAgBIABAAIAAABgAAEi+IAAgBIABAAIAAABgAB6jAIAAgBIABAAIAAABgABWjHIAAAAIABAAIAAAAgABJjIIAAgBIABAAIAAABgABCjJIAAgBIABAAIAAABgAA7jNIAAgBIABAAIAAABgAA9jUIAAgBIABAAIAAABgAA+jbIAAgBIABAAIAAABgAATjbIAAgBIABAAIAAABgAAUjeIAAgBIABAAIAAABgABAjiIAAgBIABAAIAAABgAAVjiIAAgBIABAAIAAABgAAWjoIAAgBIABAAIAAABgABCjpIAAgBIAAAAIAAABgABDjwIAAgBIABAAIAAABgAANkxIAAAAIABAAIAAgBIABAAIAAABIgBAAIAAAAgAAPkyIAAgBIABAAIAAABgAAQkzIAAgBIAAAAIAAABgAAQkzgAAQk0IAAgBIABAAIAAABgAAQk0gAARk1IAAAAIABAAIAAAAgAARk1gAASk1IAAgBIABAAIAAABgAASk1gAATk2IAAgBIAAAAIAAABgAATk2gAATk3IAAgBIABAAIAAABgAATk3gAAUk4IAAgBIABAAIAAABgAAVk5IAAAAIABAAIAAAAgAAVk5gAAWk5IAAgBIABAAIAAABgAAWk5g");
	this.shape_99.setTransform(308.5,375.1);

	this.shape_100 = new cjs.Shape();
	this.shape_100.graphics.f("#A8A8A8").s().p("AAlEtIAAgBIABAAIAAABgAA2EsIAAgBIABAAIAAABgAA7ErIAAgBIABAAIAAABgABGEqIAAgBIABAAIAAABgAg9EqIAAgBIABAAIAAABgAhEEnIAAAAIABAAIAAAAgABaElIAAgBIABAAIAAABgABgEkIAAgBIABAAIAAABgAhMEkIAAgBIABAAIAAABgABpEhIAAAAIAAAAIAAAAgABuEgIAAgBIABAAIAAABgABwEfIAAAAIABAAIAAAAgAB8EbIAAgBIABAAIAAABgAheEaIAAgBIABAAIAAABgACCEZIAAgBIABAAIAAABgAhiEYIAAAAIABAAIAAAAgACGEYIAAgBIABAAIAAABgAhoEUIAAgBIAAAAIAAABgAhtERIAAgBIABAAIAAABgAhvEPIAAAAIAAAAIAAAAgAh4EJIAAgBIABAAIAAABgACnEGIAAAAIABAAIAAAAgACuEBIAAgBIABAAIAAABgAC8D3IAAgBIAAAAIAAABgADBDzIAAgBIABAAIAAABgAAwDkIAAgBIABAAIAAABgAA2DjIAAgBIABAAIAAABgAiiDjIAAgBIAAAAIAAABgAhEDgIAAgBIAAAAIAAABgAhNDbIAAAAIABAAIAAAAgAhPDbIAAgBIABAAIAAABgADaDXIAAgBIABAAIAAABgADcDUIAAgBIABAAIAAABgAi3DNIAAgBIAAAAIAAABgAhmDMIAAgBIABAAIAAABgAhpDJIAAAAIABAAIAAAAgAkCDFIAAgBIABAAIAAABgAhwDEIAAAAIABAAIAAAAgAh6C9IAAgBIABAAIAAABgAkAC8IAAgBIABAAIAAABgAjIC5IAAgBIABAAIAAABgABNC3IAAgBIAAAAIAAABgABFCyIAAgCIAAAAIAAACgAA+CtIAAAAIABAAIAAAAgAj4CoIAAgBIABAAIAAABgAAsCkIAAgBIAAAAIAAABgADLCjIAAAAIABAAIAAAAgAj0CjIAAAAIAAAAIAAAAgAC2CiIAAgBIABAAIAAABgACpCgIAAgBIABAAIAAABgACjCfIAAgBIABAAIAAABgACbCdIAAAAIAAAAIAAAAgACXCdIAAgBIABAAIAAABgAjwCdIAAgBIABAAIAAABgACQCbIAAgBIABAAIAAABgACNCaIAAAAIAAAAIAAAAgAAQCaIAAAAIABAAIAAAAgACKCaIAAgBIAAAAIAAABgABtCRIAAgBIABAAIAAABgABWCFIAAgCIABAAIAAACgABSCCIAAgBIABAAIAAABgABQCAIAAAAIAAAAIAAAAgAjQB/IAAgBIAAAAIAAABgAjABxIAAAAIABAAIAAAAgAi4BsIAAAAIABAAIAAAAgABEBsIAAgBIABAAIAAABgAi2BrIAAgBIABAAIAAABgAixBnIAAgBIABAAIAAABgAivBmIAAgBIABAAIAAABgAiABlIAAgBIABAAIAAABgADcBiIAAgBIABAAIAAABgADlBhIAAAAIABAAIAAAAgADaBhIAAAAIABAAIAAAAgADYBhIAAgCIAAAAIAAACgAimBhIAAgCIABAAIAAACgADVBfIAAAAIABAAIAAAAgABRBfIAAAAIABAAIAAAAgAh7BfIAAAAIABAAIAAAAgADvBfIAAgBIABAAIAAABgADTBfIAAgBIABAAIAAABgADRBeIAAgBIAAAAIAAABgAieBbIAAAAIAAAAIAAAAgAh3BaIAAAAIABAAIAAAAgAECBXIAAgBIABAAIAAABgABmBUIAAAAIAAAAIAAAAgAB0BPIAAgCIABAAIAAACgABBBNIAAgBIABAAIAAABgABDBMIAAgBIABAAIAAABgAgRBLIAAgBIABAAIAAABgACBBKIAAgBIABAAIAAABgACDBJIAAgBIABAAIAAABgABSBIIAAgBIABAAIAAABgABhBCIAAAAIABAAIAAAAgABwA+IAAgBIAAAAIAAABgAByA9IAAAAIABAAIAAAAgAAHA6IAAAAIABAAIAAAAgAB/A5IAAgBIAAAAIAAABgACBA4IAAgBIABAAIAAABgAAFA3IAAAAIAAAAIAAAAgAAXA2IAAgBIAAAAIAAABgACNA1IAAgBIABAAIAAABgAACA1IAAgBIABAAIAAABgACQA0IAAgBIABAAIAAABgAAAAxIAAgBIABAAIAAABgACfAvIAAgBIAAAAIAAABgAAAAuIAAAAIAAAAIAAAAgAgDArIAAAAIABAAIAAAAgACtArIAAgCIABAAIAAACgAAdArIAAgCIABAAIAAACgAC8AmIAAgBIABAAIAAABgAAeAmIAAgBIABAAIAAABgAD3AkIAAgBIABAAIAAABgAD0AiIAAgBIABAAIAAABgADLAhIAAgBIABAAIAAABgAiLAhIAAgBIABAAIAAABgADOAgIAAgBIAAAAIAAABgAhrAgIAAgBIABAAIAAABgAiRAdIAAAAIABAAIAAAAgADaAcIAAAAIABAAIAAAAgAhhAcIAAAAIAAAAIAAAAgADcAcIAAgBIABAAIAAABgAhYAYIAAgBIABAAIAAABgAAiAXIAAgBIAAAAIAAABgAhPAUIAAgBIABAAIAAABgAhFARIAAgBIABAAIAAABgAg8AMIAAgBIABAAIAAABgAigALIAAgBIABAAIAAABgAgHAJIAAgBIABAAIAAABgAgFAGIAAgBIABAAIAAABgAgEAEIAAgBIABAAIAAABgAgCABIAAAAIABAAIAAAAgAAAAAIAAAAIAAAAIAAAAgAAAgCIAAAAIAAAAIAAAAgAikgCIAAgBIABAAIAAABgAAmgWIAAgBIABAAIAAABgAApgYIAAAAIAAAAIAAAAgAAwgcIAAgBIAAAAIAAABgAA4giIAAgBIABAAIAAABgAA/goIAAgBIABAAIAAABgAggg9IAAgBIABAAIAAABgAihhDIAAAAIABAAIAAAAgABchFIAAgBIABAAIAAABgAiihFIAAgBIABAAIAAABgAikhOIAAgBIABAAIAAABgABqhgIAAgBIABAAIAAABgAAAhiIAAAAIABAAIAAAAgABrhiIAAgBIABAAIAAABgAishmIAAgBIABAAIAAABgAAMhsIAAgBIAAAAIAAABgAiwhwIAAgBIABAAIAAABgAB/iDIAAgBIAAAAIAAABgACLiKIAAgBIABAAIAAABgACWiRIAAgBIABAAIAAABgAgZiYIAAgBIAAAAIAAABgACgiZIAAAAIABAAIAAAAgAgQibIAAAAIABAAIAAAAgAhPiiIAAgBIABAAIAAABgAhVilIAAgBIABAAIAAABgAggiuIAAgBIABAAIAAABgAhyiwIAAgBIABAAIAAABgAh4iyIAAAAIABAAIAAAAgAh+i0IAAgBIAAAAIAAABgAgSi1IAAgBIABAAIAAABgACvi2IAAgBIABAAIAAABgACsi7IAAgBIABAAIAAABgAgEi9IAAgCIAAAAIAAACgAACjFIAAAAIABAAIAAAAgACAjOIAAgBIABAAIAAABgAB0jRIAAgBIABAAIAAABgABwjSIAAAAIABAAIAAAAgABnjTIAAgBIABAAIAAABgABijUIAAgBIAAAAIAAABgAAQjVIAAAAIAAAAIAAAAgABQjWIAAgBIABAAIAAABgABKjXIAAAAIABAAIAAAAgABBjaIAAgBIABAAIAAABgABCjhIAAgBIABAAIAAABgAAZjpIAAgBIABAAIAAABgABRkkIAAgBIABAAIAAABgABTkrIAAgCIABAAIAAACg");
	this.shape_100.setTransform(307.9,376.5);

	this.shape_101 = new cjs.Shape();
	this.shape_101.graphics.f("#C5C5C5").s().p("AAIE8IAAgBIABAAIAAABgAgnE8IAAgBIABAAIAAABgAAXE7IAAgBIAAAAIAAABgAAiE6IAAgBIABAAIAAABgAgzE6IAAgBIAAAAIAAABgAhAE3IAAAAIABAAIAAAAgAhHE1IAAgBIABAAIAAABgAhME0IAAgCIABAAIAAACgABVEyIAAgBIABAAIAAABgAhPExIAAAAIAAAAIAAAAgABbExIAAgBIABAAIAAABgAhVEvIAAgBIABAAIAAABgAhWEuIAAAAIAAAAIAAAAgABmEuIAAgBIABAAIAAABgAheErIAAgBIAAAAIAAABgAhgEqIAAgBIABAAIAAABgAB5EnIAAgBIABAAIAAABgACFEjIAAgBIABAAIAAABgACYEaIAAgBIAAAAIAAABgACZEZIAAgBIABAAIAAABgAh9EXIAAgBIABAAIAAABgAiEESIAAgBIABAAIAAABgAiIEOIAAgBIABAAIAAABgAiMELIAAgBIAAAAIAAABgACxEIIAAAAIABAAIAAAAgAC1EFIAAgBIABAAIAAABgAiTEFIAAgBIABAAIAAABgAiTEEIAAgBIAAAAIAAABgAC7EAIAAAAIABAAIAAAAgAicD8IAAgBIABAAIAAABgAidD7IAAgBIABAAIAAABgAieD6IAAAAIABAAIAAAAgADLDvIAAAAIABAAIAAAAgAhADvIAAAAIABAAIAAAAgAirDuIAAgBIgBAAIAAgBIAAAAIAAgBIAAAAIAAABIABAAIAAABIABAAIAAABgABLDtIAAgBIABAAIAAABgAhJDtIAAgBIABAAIAAABgABQDsIAAgBIABAAIAAABgAhMDsIAAgBIABAAIAAABgABWDrIAAgBIABAAIAAABgAitDrIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgABbDqIAAgBIABAAIAAABgAi3DgIAAgBIgBAAIAAgBIABAAIAAABIAAAAIAAABgAi+DZIAAgBIABAAIAAABgABcDXIAAgBIABAAIAAABgAjCDUIAAgBIAAAAIAAABgAjDDTIAAgBIABAAIAAABgAh3DRIAAgCIABAAIAAACgABRDMIAAgBIABAAIAAABgABQDLIAAAAIABAAIAAAAgAjLDKIAAgBIABAAIAAABgAjODFIAAgBIABAAIAAABgAkEDDIAAgBIABAAIAAABgABFDCIAAAAIAAAAIAAAAgAiHDCIAAAAIABAAIAAAAgAkCC/IAAAAIAAAAIAAAAgAA2C5IAAgBIABAAIAAABgADKCwIAAAAIABAAIAAAAgAC8CwIAAgBIAAAAIAAABgAAiCwIAAgBIABAAIAAABgACsCuIAAgBIABAAIAAABgACkCtIAAgBIABAAIAAABgAAcCtIAAgBIABAAIAAABgAAaCsIAAgBIAAAAIAAABgAAXCrIAAgBIABAAIAAABgAAVCqIAAAAIABAAIAAAAgAj2CqIAAAAIABAAIAAAAgACSCqIAAgBIABAAIAAABgAATCqIAAgBIAAAAIAAABgACLCoIAAgBIABAAIAAABgAANCoIAAgBIABAAIAAABgACICnIAAAAIABAAIAAAAgACFCnIAAgBIABAAIAAABgACCCmIAAgBIAAAAIAAABgAjyCmIAAgBIABAAIAAABgAikClIAAAAIABAAIAAAAgAB8ClIAAgBIABAAIAAABgAilClIAAgBIABAAIAAABgAilCkIAAgBIAAAAIAAABgAgJCjIAAgBIABAAIAAABgAimCjIAAgBIABAAIAAABgAgYCgIAAgBIABAAIAAABgAhFCeIAAgBIABAAIAAABgAipCdIAAgBIAAAAIAAABgAhGCcIAAgBIABAAIAAABgAhHCaIAAgBIABAAIAAABgAhICYIAAAAIABAAIAAAAgABcCYIAAgBIABAAIAAABgABbCXIAAgBIAAAAIAAABgAhICXIAAgBIAAAAIAAABgAilCXIAAgBIABAAIAAABgABZCWIAAgBIABAAIAAABgAhJCVIAAAAIABAAIAAAAgAhKCUIAAgBIABAAIAAABgAjfCTIAAAAIAAAAIAAAAgABSCTIAAgBIABAAIAAABgAhLCTIAAgBIABAAIAAABgAigCSIAAgCIABAAIAAACgAhMCQIAAAAIABAAIAAAAgAhMCPIAAgBIAAAAIAAABgAhNCNIAAAAIABAAIAAAAgAhOCMIAAAAIABAAIAAAAgAibCMIAAAAIABAAIAAAAgAjVCLIAAgBIABAAIAAABgAiXCGIAAAAIABAAIAAAAgAA9CEIAAgBIABAAIAAABgAiTCCIAAgBIABAAIAAABgAiOB8IAAgBIABAAIAAABgAjCB8IAAgBIAAAAIAAABgAhXB5IAAgBIABAAIAAABgAhWB3IAAgBIAAAAIAAABgAiJB3IAAgBIAAAAIAAABgAhWB1IAAgBIABAAIAAABgAhVBzIAAAAIABAAIAAAAgAhUByIAAgBIABAAIAAABgAiFBxIAAAAIABAAIAAAAgAhTBxIAAgBIAAAAIAAABgAhTBvIAAgBIABAAIAAABgADkBuIAAgCIABAAIAAACgAhSBuIAAgCIABAAIAAACgAhRBsIAAgBIABAAIAAABgAiABsIAAgBIABAAIAAABgAhQBqIAAgBIABAAIAAABgAilBpIAAgBIAAAAIAAABgAhPBoIAAAAIAAAAIAAAAgAhPBnIAAAAIABAAIAAAAgABXBnIAAgBIAAAAIAAABgAh7BnIAAgBIABAAIAAABgAD6BmIAAgBIABAAIAAABgABYBmIAAgBIABAAIAAABgABaBlIAAgBIABAAIAAABgABbBkIAAgBIABAAIAAABgAicBkIAAgBIABAAIAAABgABdBjIAAgBIABAAIAAABgAD/BiIAAgBIABAAIAAABgACtBhIAAgBIAAAAIAAABgAh3BhIAAgBIABAAIAAABgACqBgIAAAAIABAAIAAAAgABkBgIAAAAIABAAIAAAAgAiVBgIAAAAIABAAIAAAAgACoBgIAAgBIABAAIAAABgACmBfIAAgBIAAAAIAAABgACjBeIAAgBIABAAIAAABgABpBeIAAgBIABAAIAAABgAChBdIAAgBIABAAIAAABgAhzBdIAAgBIAAAAIAAABgACfBcIAAAAIAAAAIAAAAgACcBcIAAgCIABAAIAAACgAA2BcIAAgCIABAAIAAACgACaBaIAAAAIABAAIAAAAgABxBaIAAAAIABAAIAAAAgACYBaIAAgBIAAAAIAAABgAgFBaIAAgBIABAAIAAABgACVBZIAAgBIABAAIAAABgAgNBZIAAgBIABAAIAAABgACTBYIAAgBIABAAIAAABgAB3BYIAAgBIABAAIAAABgABFBXIAAgBIAAAAIAAABgAhDBUIAAgBIABAAIAAABgABUBSIAAgBIAAAAIAAABgABiBNIAAAAIABAAIAAAAgABlBNIAAgBIABAAIAAABgABxBIIAAgBIABAAIAAABgAALBIIAAgBIABAAIAAABgAB0BHIAAAAIAAAAIAAAAgACCBDIAAgBIABAAIAAABgAEEA/IAAgBIABAAIAAABgACRA+IAAgBIABAAIAAABgACgA6IAAgBIABAAIAAABgAAXA6IAAgBIAAAAIAAABgACvA1IAAgBIABAAIAAABgACxA0IAAgBIABAAIAAABgAC+AwIAAAAIABAAIAAAAgADAAwIAAgBIABAAIAAABgAhyAuIAAgBIABAAIAAABgAAaAtIAAgBIABAAIAAABgADqArIAAgBIABAAIAAABgADNArIAAgBIABAAIAAABgADPAqIAAAAIABAAIAAAAgAhoAqIAAgBIAAAAIAAABgADnApIAAAAIABAAIAAAAgADeAmIAAgBIABAAIAAABgAhfAmIAAgBIABAAIAAABgAhWAiIAAgBIABAAIAAABgAhMAeIAAAAIAAAAIAAAAgAhDAaIAAgBIABAAIAAABgAg6AXIAAgBIABAAIAAABgAimAXIAAgBIABAAIAAABgAg2AVIAAgBIABAAIAAABgAgwASIAAgBIABAAIAAABgAgsARIAAgBIABAAIAAABgAgnAOIAAAAIABAAIAAAAgAgjANIAAgBIABAAIAAABgAgdALIAAgCIAAAAIAAACgAgZAJIAAgBIAAAAIAAABgAgUAGIAAAAIABAAIAAAAgAgQAFIAAgBIABAAIAAABgAiqAEIAAAAIABAAIAAAAgAgLADIAAgBIABAAIAAABgAgHABIAAgBIABAAIAAABgAAygUIAAgBIABAAIAAABgAA5gaIAAgBIABAAIAAABgAiigeIAAAAIAAAAIAAAAgAA+geIAAgBIABAAIAAABgAgugjIAAgBIABAAIAAABgABFglIAAAAIABAAIAAAAgABGglIAAgBIABAAIAAABgABGglgAgsglIAAgBIABAAIAAABgABHgmIAAgBIABAAIAAABgABIgnIAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABgABIgngABJgpIAAAAIABAAIAAAAgABKgpIAAgCIABAAIAAACgABKgpgABLgrIAAAAIABAAIAAAAgAgkgzIAAgBIABAAIAAABgAimg1IAAgBIABAAIAAABgAgdg7IAAgCIABAAIAAACgABbg+IAAgBIAAAAIAAABgABdhCIAAgBIABAAIAAABgAgWhDIAAgBIABAAIAAABgAgVhEIAAgBIABAAIAAABgAgVhEgAgUhFIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAgUhFgAirhGIAAgBIABAAIAAABgAgShHIAAgBIAAAAIAAABgAgShIIAAgBIABAAIAAABgAgRhJIAAAAIABAAIAAAAgAgQhJIAAgBIABAAIAAABgAgQhJgAgPhKIAAAAIAAAAIAAAAgAgPhKgAgPhKIAAgBIABAAIAAABgAgPhKgAgOhLIAAgBIABAAIAAABgAgNhMIAAgBIABAAIAAABgAgNhMgAgMhNIAAgBIABAAIAAABgAgLhOIAAgBIAAAAIAAABgAgLhOgAiwhUIAAgBIABAAIAAABgAgDhVIAAgBIABAAIAAABgAgChWIAAgBIABAAIAAABgAgChWgABmhYIAAgBIABAAIAAABgAi0hhIAAgBIAAAAIAAABgAAMhkIAAgBIAAAAIAAABgAi2hlIAAAAIABAAIAAAAgAi3hoIAAgCIAAAAIAAACgAi6huIAAgBIABAAIAAABgAi8h0IAAAAIABAAIAAAAgAB+h4IAAgBIABAAIAAABgAi/h6IAAgCIABAAIAAACgAjAh8IAAgBIABAAIAAABgACHh+IAAgBIABAAIAAABgAguiGIAAgBIABAAIAAABgACWiIIAAAAIABAAIAAAAgAgoiIIAAAAIAAAAIAAAAgAgmiIIAAgBIABAAIAAABgAjGiJIAAgBIAAAAIAAABgACaiLIAAgBIABAAIAAABgAjHiLIAAgBIABAAIAAABgAjIiMIAAgCIABAAIAAACgAgSiOIAAgBIABAAIAAABgAjJiOIAAgBIABAAIAAABgAgNiPIAAgBIABAAIAAABgAgEiRIAAgBIABAAIAAABgAjKiSIAAAAIABAAIAAAAgACjiTIAAgBIAAAAIAAABgAhSiUIAAgBIABAAIAAABgAg+iYIAAgBIABAAIAAABgAg8iZIAAAAIABAAIAAAAgAg6iZIAAgBIABAAIAAABgACnibIAAAAIABAAIAAAAgAg1ibIAAAAIABAAIAAAAgAhiibIAAAAIABAAIAAAAgACoicIAAgBIABAAIAAABgAgvidIAAgBIABAAIAAABgAhoidIAAgBIAAAAIAAABgAhvigIAAAAIABAAIAAAAgAh1iiIAAgBIABAAIAAABgAgiijIAAgBIABAAIAAABgAghikIAAAAIABAAIAAAAgAgfikIAAgBIABAAIAAABgAh7ikIAAgBIABAAIAAABgAgdilIAAAAIAAAAIAAAAgAgcilIAAgBIABAAIAAABgAgaimIAAgBIABAAIAAABgAiMirIAAAAIABAAIAAAAgACpirIAAgBIABAAIAAABgAiSitIAAAAIABAAIAAAAgACnitIAAgBIABAAIAAABgAiYivIAAgBIABAAIAAABgACjiyIAAgBIABAAIAAABgAAAi5IAAgBIAAAAIAAABgACPi8IAAgBIABAAIAAABgACNi9IAAgBIAAAAIAAABgACKi+IAAAAIABAAIAAAAgACFi/IAAAAIABAAIAAAAgACCi/IAAgBIAAAAIAAABgAB/jAIAAgBIAAAAIAAABgAAFjBIAAgBIABAAIAAABgABwjEIAAgBIAAAAIAAABgABnjFIAAgBIABAAIAAABgABTjIIAAgBIABAAIAAABgAA7jMIAAgBIABAAIAAABgAAOjPIAAgBIABAAIAAABgAA9jTIAAgBIABAAIAAABgAARjWIAAgBIABAAIAAABgAASjYIAAgBIABAAIAAABgAA+jaIAAgBIABAAIAAABgAATjaIAAgBIAAAAIAAABgAAUjhIAAgBIABAAIAAABgABAjiIAAAAIABAAIAAAAgABCjpIAAAAIAAAAIAAAAgABDjvIAAgCIABAAIAAACgAAQkiIAAAAIABAAIAAAAgAAQklIAAAAIAAAAIAAAAgAAPkoIAAgBIABAAIAAABgAAOkrIAAgBIABAAIAAABgAA+ktIAAgBIABAAIAAABgAA+kuIAAgBIAAAAIAAABgAANkuIAAgBIABAAIAAABgAA9kvIAAgBIABAAIAAABgAA8kwIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABIAAAAIAAAAIABAAIAAABIABAAIAAABgAA4k0IAAAAIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgCIgBAAIAAgBIABAAIAAABIABAAIAAACIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAgAAzk6IAAAAIAAAAIAAAAgAAyk6IAAgBIABAAIAAABg");
	this.shape_101.setTransform(308.5,375.2);

	this.shape_102 = new cjs.Shape();
	this.shape_102.graphics.f("#E2E2E2").s().p("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BAAIAAABgAiaB+IAAgBIABAAIAAABgAjUB8IAAgBIABAAIAAABgAiVB4IAAgBIAAAAIAAABgAjNB2IAAAAIABAAIAAAAgAA9B1IAAgBIAAAAIAAABgAjKB0IAAgBIABAAIAAABgAiRBzIAAgBIABAAIAAABgAjHByIAAgBIABAAIAAABgAiMBtIAAgBIABAAIAAABgAA+BsIAAgBIABAAIAAABgAi/BsIAAgBIABAAIAAABgABABpIAAgBIABAAIAAABgABBBoIAAAAIABAAIAAAAgABBBogAiHBoIAAgBIABAAIAAABgABEBmIAAgBIAAAAIAAABgABEBlIAAgBIABAAIAAABgAixBjIAAgBIABAAIAAABgAiDBiIAAgBIABAAIAAABgADXBhIAAgBIAAAAIAAABgADiBgIAAAAIAAAAIAAAAgADUBgIAAAAIABAAIAAAAgADSBgIAAgBIABAAIAAABgADpBfIAAgBIAAAAIAAABgADQBfIAAgBIAAAAIAAABgAiqBfIAAgBIAAAAIAAABgADNBeIAAgBIABAAIAAABgADuBdIAAAAIABAAIAAAAgADwBdIAAgBIABAAIAAABgAh+BdIAAgBIABAAIAAABgAijBaIAAgBIABAAIAAABgAD8BXIAAgBIABAAIAAABgAh5BXIAAgBIABAAIAAABgAiYBUIAAgBIABAAIAAABgAh1BSIAAAAIABAAIAAAAgAh0BSIAAgBIAAAAIAAABgAALBOIAAgBIAAAAIAAABgAADBNIAAgBIABAAIAAABgAB0BMIAAgBIABAAIAAABgAA9BLIAAgBIABAAIAAABgAB9BJIAAgBIAAAAIAAABgAgxBHIAAAAIABAAIAAAAgABMBHIAAgBIABAAIAAABgAg5BHIAAgBIABAAIAAABgABPBGIAAgBIAAAAIAAABgABbBCIAAgBIABAAIAAABgABdBBIAAgBIABAAIAAABgABqA9IAAgBIABAAIAAABgABsA8IAAAAIABAAIAAAAgAANA6IAAgBIABAAIAAABgAB7A4IAAgBIABAAIAAABgAEHAzIAAgBIABAAIAAABgACKAzIAAgBIABAAIAAABgAEEAxIAAgBIABAAIAAABgAEBAuIAAgBIABAAIAAABgACZAuIAAgBIABAAIAAABgACbAtIAAAAIABAAIAAAAgAD+AsIAAgBIAAAAIAAABgACoAqIAAgBIAAAAIAAABgACqApIAAgBIABAAIAAABgAC3AlIAAgBIAAAAIAAABgAC5AkIAAgBIABAAIAAABgAAbAgIAAgBIABAAIAAABgADtAfIAAAAIABAAIAAAAgADIAfIAAAAIABAAIAAAAgADqAdIAAgBIABAAIAAABgAiYAcIAAgBIABAAIAAABgADnAbIAAgBIABAAIAAABgADXAbIAAgBIAAAAIAAABgAicAZIAAgBIABAAIAAABgADkAYIAAgBIABAAIAAABgAiqgQIAAgBIAAAAIAAABgAAsgeIAAAAIABAAIAAAAgAAyghIAAgBIAAAAIAAABgAA5gnIAAgBIAAAAIAAABgAA9grIAAgBIABAAIAAABgAgvgtIAAgBIABAAIAAABgABEgwIAAgBIAAAAIAAABgABEgxIAAgBIABAAIAAABgABFgyIAAgBIABAAIAAABgAgqg3IAAAAIABAAIAAAAgABNg6IAAgBIABAAIAAABgABOg7IAAAAIABAAIAAgBIAAAAIAAABIAAAAIAAAAgABOg7gAgkhAIAAgBIABAAIAAABgAgehIIAAgBIABAAIAAABgAiohLIAAgBIABAAIAAABgAgYhOIAAgBIABAAIAAABgAgXhPIAAgBIAAAAIAAABgAgXhPgAgXhQIAAgBIABAAIAAABgAiqhRIAAAAIABAAIAAAAgABhhVIAAgBIAAAAIAAABgAgJhdIAAgBIAAAAIAAABgAgJheIAAgBIABAAIAAABgABlhgIAAgBIABAAIAAABgABmhjIAAAAIABAAIAAAAgAgBhlIAAgBIABAAIAAABgAixhmIAAgBIABAAIAAABgAAEhqIAAgBIAAAAIAAABgAAJhvIAAgBIABAAIAAABgABphwIAAgBIABAAIAAABgAi2h0IAAgBIABAAIAAABgABqh4IAAgBIABAAIAAABgABvh+IAAgBIABAAIAAABgACBiHIAAgBIABAAIAAABgACFiKIAAgBIABAAIAAABgAg1iPIAAAAIABAAIAAAAgACWiVIAAgBIAAAAIAAABgAgpiVIAAgBIAAAAIAAABgACaiYIAAgBIAAAAIAAABgAgbiaIAAAAIABAAIAAAAgABIidIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABIAAAAIAAABIABAAIAAAAgAgHidIAAgBIABAAIAAABgABFigIAAgBIABAAIAAABgAhMihIAAgBIABAAIAAABgACkiiIAAgBIABAAIAAABgAhZilIAAgBIABAAIAAABgAhfioIAAAAIAAAAIAAAAgAgzipIAAgBIABAAIAAABgAhmiqIAAgBIABAAIAAABgAiDi1IAAgBIABAAIAAABgAgWi3IAAAAIABAAIAAAAgAiJi3IAAgBIABAAIAAABgAiPi6IAAgBIABAAIAAABgAgLi+IAAAAIABAAIAAAAgACjjAIAAgBIABAAIAAABgACUjIIAAgBIABAAIAAABgACIjMIAAgBIABAAIAAABgAAFjOIAAgBIABAAIAAABgABtjSIAAgBIABAAIAAABgABfjUIAAgBIABAAIAAABgAARjiIAAgBIABAAIAAABgAA/jnIAAgBIABAAIAAABgABAjuIAAgBIABAAIAAABgAAVj1IAAAAIABAAIAAAAgABCj1IAAgBIABAAIAAABgABEj8IAAgBIAAAAIAAABgABFkEIAAAAIABAAIAAAAgABHkLIAAAAIABAAIAAAAgABIkSIAAAAIABAAIAAAAgABKkZIAAgBIABAAIAAABgAAVkcIAAgBIABAAIAAABgAAVkfIAAgBIAAAAIAAABgAAUkiIAAgBIABAAIAAABgAATklIAAgBIABAAIAAABgAASkoIAAgBIABAAIAAABgAASksIAAAAIAAAAIAAAAg");
	this.shape_102.setTransform(308.4,376.5);

	this.shape_103 = new cjs.Shape();
	this.shape_103.graphics.f("#797979").s().p("AASE2IAAAAIABAAIAAAAgAg1E1IAAgBIABAAIAAABgAA7EyIAAAAIABAAIAAAAgABEExIAAgBIABAAIAAABgAhIEvIAAAAIAAAAIAAAAgABQEvIAAgBIAAAAIAAABgAhPEtIAAgBIABAAIAAABgABgErIAAgBIAAAAIAAABgAhVEqIAAgBIABAAIAAABgAhZEoIAAgBIABAAIAAABgABqEnIAAAAIABAAIAAAAgAhdEmIAAgBIABAAIAAABgAhfElIAAgBIABAAIAAABgAB2EkIAAgBIABAAIAAABgAB8EhIAAgBIABAAIAAABgAhzEZIAAgBIAAAAIAAABgACgEPIAAgBIABAAIAAABgAiCEOIAAgBIABAAIAAABgACoEKIAAAAIAAAAIAAAAgACwEEIAAgBIABAAIAAABgAiQECIAAgBIAAAAIAAABgAC4D+IAAgBIABAAIAAABgAiXD7IAAAAIAAAAIAAAAgAAsDtIAAgBIABAAIAAABgAAyDsIAAgBIABAAIAAABgAhMDnIAAgBIABAAIAAABgADRDkIAAgBIABAAIAAABgAixDiIAAgBIABAAIAAABgAhZDhIAAgBIABAAIAAABgAhcDeIAAAAIABAAIAAAAgAhhDcIAAgBIABAAIAAABgADYDbIAAgBIAAAAIAAABgAhjDbIAAgBIABAAIAAABgAi6DYIAAgBIACAAIAAABgAhnDXIAAAAIABAAIAAAAgAh4DLIAAgBIABAAIAAABgAkGDIIAAAAIAAAAIAAAAgAh9DHIAAgBIABAAIAAABgAjQC+IAAgBIABAAIAAABgABEC8IAAgBIAAAAIAAABgAkCC7IAAgBIAAAAIAAABgABBC6IAAAAIABAAIAAAAgAiLC6IAAgBIABAAIAAABgAA+C5IAAgBIABAAIAAABgAApCtIAAgBIABAAIAAABgAj7CtIAAgBIABAAIAAABgAC3CrIAAgBIABAAIAAABgACoCpIAAgBIAAAAIAAABgAAgCpIAAgBIAAAAIAAABgAj2CnIAAAAIABAAIAAAAgACYCnIAAgBIABAAIAAABgAB5CfIAAgBIABAAIAAABgAgCCfIAAgBIAAAAIAAABgAB2CeIAAgBIABAAIAAABgAB0CdIAAgBIAAAAIAAABgAgUCcIAAgBIAAAAIAAABgABtCbIAAgBIACAAIAAABgABrCaIAAgBIABAAIAAABgABmCYIAAgBIABAAIAAABgAhFCYIAAgBIABAAIAAABgAhGCWIAAgBIABAAIAAABgABfCVIAAgBIABAAIAAABgAhHCVIAAgBIABAAIAAABgABdCUIAAgBIABAAIAAABgABbCTIAAgBIACAAIAAABgAhICTIAAgBIABAAIAAABgABYCRIAAAAIABAAIAAAAgAhICRIAAAAIAAAAIAAAAgAhJCQIAAgBIABAAIAAABgAhJCOIAAAAIAAAAIAAAAgABRCOIAAgBIABAAIAAABgAhKCNIAAgBIABAAIAAABgAhLCLIAAgBIABAAIAAABgABLCKIAAgBIABAAIAAABgAhMCKIAAgBIABAAIAAABgABJCIIAAgBIAAAAIAAABgAhNCIIAAgBIABAAIAAABgAjXCIIAAgBIAAAAIAAABgAjTCFIAAgBIABAAIAAABgABECEIAAgBIAAAAIAAABgAjNB/IAAAAIABAAIAAAAgAjEB5IAAgBIABAAIAAABgAi8B0IAAgBIABAAIAAABgAhWBzIAAgBIABAAIAAABgAhVBxIAAAAIABAAIAAAAgAhUBwIAAgBIAAAAIAAABgAiIBwIAAgBIABAAIAAABgAi1BvIAAgBIABAAIAAABgAhUBuIAAgBIABAAIAAABgAizBtIAAAAIABAAIAAAAgAhTBtIAAgBIABAAIAAABgABHBrIAAgBIABAAIAAABgAhSBrIAAgBIAAAAIAAABgADYBqIAAgBIAAAAIAAABgAiDBqIAAgBIABAAIAAABgADWBpIAAAAIAAAAIAAAAgAhSBpIAAAAIABAAIAAAAgAisBpIAAAAIAAAAIAAAAgADTBpIAAgBIABAAIAAABgADmBoIAAgBIABAAIAAABgADRBoIAAgBIABAAIAAABgAhRBoIAAgBIABAAIAAABgADOBnIAAgBIABAAIAAABgADMBmIAAAAIABAAIAAAAgAhQBmIAAAAIABAAIAAAAgABQBlIAAgBIABAAIAAABgAhPBlIAAgBIAAAAIAAABgAh+BlIAAgBIAAAAIAAABgAD1BjIAAgBIABAAIAAABgAhPBjIAAgBIABAAIAAABgAihBiIAAAAIABAAIAAAAgABWBiIAAgBIABAAIAAABgAhOBiIAAgBIABAAIAAABgABXBhIAAgBIABAAIAAABgABZBgIAAgBIABAAIAAABgABbBfIAAgBIAAAAIAAABgAibBfIAAgBIABAAIAAABgAD9BeIAAAAIABAAIAAAAgABdBeIAAAAIAAAAIAAAAgABeBeIAAgBIABAAIAAABgAiWBcIAAgBIABAAIAAABgABlBbIAAgBIABAAIAAABgAEDBYIAAgBIABAAIAAABgABvBXIAAgBIAAAAIAAABgAgBBWIAAgBIABAAIAAABgAA8BVIAAgBIABAAIAAABgAgJBVIAAgBIABAAIAAABgAB0BUIAAgBIABAAIAAABgAA+BUIAAgBIABAAIAAABgAEGBTIAAgBIABAAIAAABgAB9BRIAAgBIABAAIAAABgABLBQIAAAAIABAAIAAAAgABOBQIAAgBIABAAIAAABgAg/BQIAAgBIABAAIAAABgABdBLIAAgBIAAAAIAAABgABrBGIAAgBIABAAIAAABgAAKBEIAAgBIABAAIAAABgAB6BBIAAAAIABAAIAAAAgACJA9IAAgBIABAAIAAABgACMA8IAAgBIAAAAIAAABgACYA4IAAgBIAAAAIAAABgAEAA3IAAgBIABAAIAAABgACaA3IAAgBIABAAIAAABgAD8A1IAAgBIABAAIAAABgACnAzIAAAAIABAAIAAAAgACpAzIAAgBIABAAIAAABgAAYAyIAAgBIAAAAIAAABgAC4AuIAAgBIABAAIAAABgAAZAtIAAgBIABAAIAAABgADGApIAAgBIABAAIAAABgAiQApIAAgBIABAAIAAABgAhtAnIAAgBIABAAIAAABgADWAkIAAAAIAAAAIAAAAgADYAkIAAgBIAAAAIAAABgAAbAjIAAgBIABAAIAAABgAhjAjIAAgBIAAAAIAAABgAibAiIAAgBIABAAIAAABgADjAhIAAgBIABAAIAAABgAhaAfIAAgBIAAAAIAAABgAhRAbIAAgBIABAAIAAABgAAdAZIAAgBIABAAIAAABgAhIAXIAAgBIABAAIAAABgAg+ATIAAgBIABAAIAAABgAg6ASIAAgBIABAAIAAABgAgLAQIAAgBIABAAIAAABgAg1APIAAgBIABAAIAAABgAgJAOIAAgBIAAAAIAAABgAgwAOIAAgBIAAAAIAAABgAgIALIAAgBIAAAAIAAABgAgrALIAAgBIAAAAIAAABgAgnAKIAAgBIABAAIAAABgAgHAJIAAgBIABAAIAAABgAgiAHIAAAAIABAAIAAAAgAipAHIAAAAIABAAIAAAAgAgFAHIAAgBIABAAIAAABgAgeAGIAAgBIABAAIAAABgAgZADIAAAAIABAAIAAAAgAipADIAAgBIAAAAIAAABgAgUACIAAgBIAAAAIAAABgAgPAAIAAAAIAAAAIAAAAgAgLAAIAAgBIABAAIAAABgAgGgCIAAgBIABAAIAAABgAiqgDIAAgBIABAAIAAABgAAggNIAAAAIAAAAIAAAAgAAigOIAAgBIAAAAIAAABgAAkgQIAAgBIABAAIAAABgAAxgYIAAgBIABAAIAAABgAgxgaIAAgBIABAAIAAABgAA3geIAAgBIACAAIAAABgAgwgeIAAgBIAAAAIAAABgAA8giIAAgBIABAAIAAABgAgugnIAAAAIABAAIAAAAgABDgnIAAgBIABAAIAAABgAgsgqIAAAAIABAAIAAAAgAgrgrIAAgBIAAAAIAAABgAgpgvIAAgBIABAAIAAABgAgmg0IAAgBIABAAIAAABgAilg4IAAgBIABAAIAAABgAiohBIAAgBIABAAIAAABgAgZhEIAAgBIABAAIAAABgAiphHIAAgBIAAAAIAAABgAishRIAAgBIAAAAIAAABgABihTIAAAAIABAAIAAAAgABkhWIAAgBIABAAIAAABgABlhZIAAgBIABAAIAAABgABohlIAAgBIABAAIAAABgAAJhmIAAgBIABAAIAAABgABphsIAAAAIAAAAIAAAAgAi3huIAAgBIABAAIAAABgAi4hyIAAgBIAAAAIAAABgAg8h8IAAgBIABAAIAAABgAB9h9IAAgBIABAAIAAABgAB/h+IAAAAIAAAAIAAAAgAi/iAIAAgBIABAAIAAABgACIiDIAAgBIABAAIAAABgAgxiJIAAAAIABAAIAAAAgAgtiLIAAgBIABAAIAAABgAjGiNIAAgBIABAAIAAABgAjGiPIAAgBIAAAAIAAABgAjHiRIAAAAIABAAIAAAAgAjHiSIAAgBIAAAAIAAABgAgTiTIAAgBIABAAIAAABgAgPiUIAAAAIAAAAIAAAAgAjIiUIAAAAIABAAIAAAAgAjJiVIAAgBIABAAIAAABgAjKiXIAAgBIABAAIAAABgAhPiYIAAAAIABAAIAAAAgAjKiYIAAgBIAAAAIAAABgACjiaIAAgBIACAAIAAABgAhfieIAAgBIABAAIAAABgACoigIAAgBIAAAAIAAABgAhligIAAgBIABAAIAAABgAguiiIAAgBIABAAIAAABgAhsijIAAAAIABAAIAAAAgAhyilIAAgBIABAAIAAABgAghinIAAgBIAAAAIAAABgAh4inIAAgBIABAAIAAABgAgfioIAAgBIABAAIAAABgAgbiqIAAgBIABAAIAAABgAiIiuIAAAAIAAAAIAAAAgAgUiuIAAgBIABAAIAAABgACpivIAAgBIABAAIAAABgAiPiwIAAgBIABAAIAAABgAiViyIAAgBIABAAIAAABgACii3IAAgBIABAAIAAABgAgDi6IAAgBIABAAIAAABgACGjDIAAgBIABAAIAAABgACEjEIAAAAIAAAAIAAAAgACBjEIAAgBIAAAAIAAABgAB9jFIAAgBIABAAIAAABgAByjIIAAAAIABAAIAAAAgABgjLIAAAAIABAAIAAAAgAAJjLIAAAAIABAAIAAAAgABbjLIAAgBIAAAAIAAABgAALjNIAAgBIABAAIAAABgAAOjRIAAgBIAAAAIAAABgAAOjSIAAgBIABAAIAAABgAAPjUIAAgBIABAAIAAABgABFj9IAAgBIAAAAIAAABgABGkEIAAgBIABAAIAAABgABIkLIAAgBIABAAIAAABgABJkSIAAgBIACAAIAAABgABLkZIAAgBIABAAIAAABgABNkgIAAgBIABAAIAAABgAARklIAAgBIABAAIAAABgAAQkoIAAgBIABAAIAAABgAAQkrIAAgBIAAAAIAAABgAAPkuIAAgBIABAAIAAABgAAOkxIAAgBIABAAIAAABgAAOk1IAAAAIAAAAIAAAAg");
	this.shape_103.setTransform(308.5,375.6);

	this.shape_104 = new cjs.Shape();
	this.shape_104.graphics.f("#F5F5F5").s().p("AAzE7IAAgBIABAAIAAABgAA+E4IAAAAIAAAAIAAAAgAhAE4IAAAAIAAAAIAAAAgABEE4IAAgBIAAAAIAAABgABPE1IAAAAIABAAIAAAAgABTE1IAAgBIABAAIAAABgAhME1IAAgBIABAAIAAABgABZEzIAAAAIABAAIAAAAgABiExIAAgBIAAAAIAAABgABoEvIAAgBIAAAAIAAABgAhZEvIAAgBIABAAIAAABgAhaEuIAAgBIAAAAIAAABgAhbEtIAAAAIAAAAIAAAAgAB6EpIAAgBIABAAIAAABgAhkEpIAAgBIABAAIAAABgAhoEmIAAAAIABAAIAAAAgACGEkIAAgBIAAAAIAAABgACHEjIAAAAIABAAIAAAAgAhuEjIAAgBIABAAIAAABgAhzEgIAAgBIAAAAIAAABgAh2EeIAAgBIABAAIAAABgACXEbIAAAAIABAAIAAAAgAh4EbIAAAAIAAAAIAAAAgACdEZIAAgBIABAAIAAABgACgEWIAAAAIABAAIAAAAgACjEUIAAAAIAAAAIAAAAgAiCEUIAAAAIABAAIAAAAgAClETIAAgBIABAAIAAABgACoERIAAgBIAAAAIAAABgAiGERIAAgBIABAAIAAABgACqEQIAAgBIAAAAIAAABgAiKEPIAAgBIABAAIAAABgACtEOIAAgBIABAAIAAABgACwELIAAgBIABAAIAAABgAiQEJIAAAAIABAAIAAAAgACzEJIAAgBIABAAIAAABgAiQEJIAAgBIAAAAIAAABgAC4EFIAAgBIABAAIAAABgAiWEEIAAAAIgBAAIAAgCIAAAAIAAgBIAAAAIAAABIABAAIAAACIABAAIAAAAgAiYEBIAAAAIABAAIAAAAgAC+D/IAAAAIABAAIAAAAgAC/D/IAAgBIABAAIAAABgAC/D/gADAD+IAAgBIABAAIAAABgADCD8IAAgBIABAAIAAABgADDD7IAAgBIAAAAIAAABgADDD7gADGD3IAAgBIAAAAIAAABgADGD2IAAAAIABAAIAAgBIACAAIAAABIgCAAIAAAAgADGD2gADJD1IAAAAIAAAAIAAAAgAg7DyIAAgBIABAAIAAABgADODvIAAgBIAAAAIAAABgADODuIAAgBIABAAIAAABgADODugABJDuIAAgBIACAAIAAABgAhIDuIAAgBIAAAAIAAABgABP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BAAIAAAAgAhMBjIAAAAIABAAIAAAAgACsBjIAAgBIABAAIAAABgACqBiIAAgBIAAAAIAAABgAhLBiIAAgBIABAAIAAABgACoBhIAAgBIAAAAIAAABgABnBhIAAgBIABAAIAAABgAiUBhIAAgBIABAAIAAABgAClBgIAAgBIABAAIAAABgAhKBgIAAgBIABAAIAAABgAh0BgIAAgBIABAAIAAABgAiSBgIAAgBIABAAIAAABgACjBfIAAgBIAAAAIAAABgACgBeIAAgBIABAAIAAABgAhJBeIAAgBIAAAAIAAABgACeBdIAAAAIABAAIAAAAgAEFBdIAAgBIABAAIAAABgACcBdIAAgBIABAAIAAABgAA1BdIAAgBIABAAIAAABgAhJBdIAAgBIABAAIAAABgACZBcIAAAAIABAAIAAAAgAEGBcIAAgBIABAAIAAABgACXBcIAAgBIABAAIAAABgAhIBcIAAgBIAAAAIAAABgACVBbIAAgBIABAAIAAABgAB5BaIAAgBIABAAIAAABgAgSBaIAAgBIAAAAIAAABgAhIBaIAAgBIABAAIAAABgAgaBZIAAgBIABAAIAAABgABEBYIAAgBIABAAIAAABgAhHBYIAAgBIABAAIAAABgABGBXIAAgBIABAAIAAABgAhGBWIAAAAIABAAIAAAAgAhFBVIAAgBIABAAIAAABgABTBTIAAAAIABAAIAAAAgABWBTIAAgBIAAAAIAAABgABiBOIAAAAIAAAAIAAAAgABkBOIAAgBIABAAIAAABgABzBJIAAAAIABAAIAAAAgAAQBHIAAgBIAAAAIAAABgACCBEIAAgBIABAAIAAABgAEGBCIAAgBIABAAIAAABgACRBAIAAgBIAAAAIAAABgAEDA/IAAgBIAAAAIAAABgAAVA/IAAgBIABAAIAAABgAAWA+IAAgCIABAAIAAACgAEAA8IAAAAIABAAIAAAAgACfA7IAAgBIABAAIAAABgAD8A6IAAgBIABAAIAAABgACiA6IAAgBIABAAIAAABgAD5A4IAAgBIABAAIAAABgAgJA4IAAgBIAAAAIAAABgACvA3IAAgBIABAAIAAABgAD3A2IAAgBIAAAAIAAABgACxA2IAAgBIAAAAIAAABgAgMA1IAAgBIABAAIAAABgAgPAxIAAAAIACAAIAAAAgADAAxIAAgBIABAAIAAABgAhxAvIAAAAIABAAIAAAAgAgRAvIAAgBIABAAIAAABgADsAuIAAgBIABAAIAAABgAhsAtIAAgBIABAAIAAABgADOAsIAAAAIABAAIAAAAgAAbAsIAAAAIABAAIAAAAgAiXAsIAAAAIAAAAIAAAAgADpAsIAAgCIABAAIAAACgAgTAsIAAgCIABAAIAAACgAhnAsIAAgCIABAAIAAACgAiaAqIAAgBIgBAAIAAgBIABAAIAAABIAAAAIAAABgADmApIAAgBIABAAIAAABgAhiApIAAgBIABAAIAAABgAgVAoIAAgBIABAAIAAABgADeAnIAAgBIAAAAIAAABgAheAnIAAgBIABAAIAAABgAicAnIAAgBIAAAAIAAABgADjAmIAAAAIABAAIAAAAgAieAmIAAgBIABAAIAAABgAhZAlIAAAAIABAAIAAAAgAgVAlIAAgBIABAAIAAABgAhUAkIAAgBIAAAAIAAABgAgUAiIAAgBIABAAIAAABgAhPAhIAAgBIAAAAIAAABgAiiAgIAAgBIAAAAIAAABgAgSAfIAAAAIAAAAIAAAAgAhLAfIAAAAIABAAIAAAAgAgRAeIAAgBIABAAIAAABgAhGAeIAAgBIABAAIAAABgAhCAcIAAgBIABAAIAAABgAgPAbIAAgBIAAAAIAAABgAg9AaIAAgCIABAAIAAACgAgNAYIAAAAIABAAIAAAAgAg4AYIAAgBIABAAIAAABgAgMAWIAAgBIABAAIAAABgAinAWIAAgBIABAAIAAABgAAfAVIAAAAIABAAIAAAAgAgKAUIAAgBIABAAIAAABgAgvAUIAAgBIABAAIAAABgAgJASIAAgBIABAAIAAABgAgmAQIAAgBIABAAIAAABgAgIAPIAAgBIABAAIAAABgAgGANIAAgBIABAAIAAABgAgcAMIAAgBIAAAAIAAABgAgEALIAAgBIABAAIAAABgAgTAIIAAAAIABAAIAAAAgAgJAEIAAgBIAAAAIAAABgAirgGIAAgBIABAAIAAABgAAigHIAAgBIAAAAIAAABgAiqgHIAAgBIABAAIAAABgAipgIIAAgBIAAAAIAAABgAipgIgAAkgJIAAgBIABAAIAAABgAipgJIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAipgJgAAngLIAAAAIAAAAIAAAAgAingLIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABIgBAAIAAAAgAApgMIAAgBIABAAIAAABgAikgNIAAgBIABAAIAAABgAArgOIAAAAIABAAIAAAAgAijgOIAAAAIAAAAIAAAAgAijgOgAijgOIAAgBIABAAIAAABgAiigPIAAgBIAAAAIAAABgAiigPgAAugQIAAAAIABAAIAAAAgAAxgRIAAgBIABAAIAAABgAA3gXIAAAAIACAAIAAAAgAA8gaIAAgBIAAAAIAAABgAA8gbIAAgBIABAAIAAABgABBgfIAAgBIABAAIAAABgABCggIAAAAIABAAIAAgBIABAAIAAABIgBAAIAAAAgABCgggAgugiIAAAAIABAAIAAAAgAgqgpIAAAAIABAAIAAAAgAgogrIAAgBIABAAIAAABgABPgtIAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABgAgmguIAAgBIAAAAIAAABgAglgwIAAgBIABAAIAAABgABUgyIAAgBIABAAIAAABgAgjgzIAAgBIABAAIAAABgAing1IAAgCIABAAIAAACgAgfg3IAAgBIABAAIAAABgAgeg4IAAgBIAAAAIAAABgAgeg4gABYg5IAAgBIABAAIAAABgAiog5IAAgBIABAAIAAABgABag7IAAgBIABAAIAAABgAgbg8IAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgABbg9IAAgBIACAAIAAABgAgZg+IAAgBIAAAAIAAABgABehCIAAAAIABAAIAAAAgAirhDIAAgBIABAAIAAABgABihLIAAgBIABAAIAAABgABkhOIAAgBIABAAIAAABgAgIhQIAAgBIABAAIAAABgAgHhRIAAgBIABAAIAAABgAgHhRgAgGhSIAAgBIABAAIAAABgAivhSIAAgBIABAAIAAABgAAAhXIAAgBIAAAAIAAABgAAAhYIAAgBIAAAAIAAABgAAAhYgAizhbIAAAAIABAAIAAAAgAAFhdIAAgBIABAAIAAABgAi0hfIAAAAIAAAAIAAAAgAAJhgIAAgBIABAAIAAABgAAKhhIAAgBIABAAIAAABgAAKhhgAi3hmIAAgBIABAAIAAABgAi4hqIAAgBIAAAAIAAABgABqhtIAAgBIABAAIAAABgABshuIAAAAIABAAIAAAAgAAyhuIAAAAIABAAIAAAAgAAzhuIAAgBIAAAAIAAABgAAzhvIAAgBIABAAIAAABgAAzhvgAA0hwIAAAAIABAAIAAAAgAi7hwIAAAAIAAAAIAAAAgAA1hwIAAgBIABAAIAAABgAA2hxIAAgBIABAAIAAABgAA2hxgAA3hyIAAAAIAAAAIAAAAgAA3hyIAAgBIACAAIAAABgAA3hygAA5hzIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABIgBAAIAAABgAB6h0IAAgBIABAAIAAABgAB8h1IAAgBIAAAAIAAABgAB9h2IAAgBIABAAIAAABgAA7h2IAAgBIABAAIAAABgAA8h3IAAgBIAAAAIAAABgAA8h3gAi+h3IAAgBIABAAIAAABgAA8h4IAAAAIABAAIAAAAgAg7h4IAAAAIABAAIAAAAgAA9h4IAAgBIABAAIAAABgACDh5IAAgBIABAAIAAABgAA+h5IAAgBIAAAAIAAAAIABAAIAAAAIgBAAIAAABgACGh7IAAgBIABAAIAAABgAg4h8IAAgBIABAAIAAAAIAAAAIAAAAIAAAAIAAABgACLh9IAAgCIABAAIAAACgAg2h/IAAgBIAAAAIAAABgACNiAIAAAAIAAAAIAAAAgACPiBIAAgBIABAAIAAABgAjDiBIAAgBIABAAIAAABgAgxiCIAAgBIABAAIAAABgACRiDIAAAAIACAAIAAAAgAjEiDIAAAAIABAAIAAAAgAjFiEIAAgBIABAAIAAABgACViFIAAAAIABAAIAAAAgAgriFIAAAAIAAAAIAAAAgAjGiFIAAgBIABAAIAAABgAgniGIAAgBIABAAIAAABgACYiHIAAgBIAAAAIAAABgABNiJIAAgBIABAAIAAABgABMiKIAAgBIgBAAIAAAAIABAAIAAAAIABAAIAAABgABLiLIAAgBIgCAAIAAAAIACAAIAAAAIAAAAIAAABgAgXiLIAAgBIABAAIAAABgAgUiMIAAAAIABAAIAAAAgACeiMIAAgBIABAAIAAABgAA3iMIAAgBIAAAAIAAABgACfiNIAAgBIAAAAIAAABgAA3iNIAAgBIACAAIAAABgAgMiNIAAgBIABAAIAAABgACfiOIAAgBIABAAIAAABgAA5iOIAAgBIAAAAIAAABgAA5iOgAA5iPIAAgBIABAAIAAABgAgCiPIAAgBIABAAIAAABgAA6iQIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAA6iQgAAIiRIAAgBIAAAAIAAABgAjKiRIAAgBIAAAAIAAABgAhRiTIAAgBIABAAIAAABgACliUIAAgBIAAAAIAAABgAjNiUIAAgBIABAAIAAABgAhXiVIAAgBIABAAIAAABgAjNiVIAAgBIAAAAIAAABgACmiWIAAgBIABAAIAAABgAg/iWIAAgBIABAAIAAABgAg9iXIAAAAIABAAIAAAAgAjOiXIAAAAIABAAIAAAAgAg6iXIAAgBIABAAIAAABgAhdiXIAAgBIABAAIAAABgAg2iZIAAgBIABAAIAAABgAgvicIAAgBIABAAIAAABgAgridIAAgBIAAAAIAAABgAhtieIAAAAIAAAAIAAAAgAgkigIAAgBIAAAAIAAABgAh0igIAAgBIABAAIAAABgAgjihIAAgBIABAAIAAABgAh6ijIAAgBIABAAIAAABgAiAilIAAgBIABAAIAAABgAgZimIAAgBIAAAAIAAABgAgYinIAAAAIABAAIAAAAgAiHinIAAgBIABAAIAAABgAgQisIAAAAIABAAIAAAAgAgNitIAAgBIABAAIAAABgAiXiuIAAgBIAAAAIAAABgACiixIAAgBIABAAIAAABgAgDi1IAAgBIABAAIAAgBIAAAAIAAABIAAAAIAAABgACWi4IAAgBIABAAIAAABgACNi7IAAgBIABAAIAAABgACMi8IAAAAIAAAAIAAAAgAAEi+IAAAAIAAAAIAAAAgAAEi+IAAgBIABAAIAAABgAByjCIAAgBIAAAAIAAABgAAHjCIAAgBIABAAIAAABgABpjDIAAgBIAAAAIAAABgAAIjEIAAgBIABAAIAAABgAAKjHIAAgBIABAAIAAABgABIjJIAAAAIABAAIAAAAgABBjJIAAgBIABAAIAAABgAANjKIAAgBIABAAIAAABgAAPjQIAAAAIABAAIAAAAgAAQjRIAAgBIAAAAIAAABgAARjVIAAAAIABAAIAAAAgAASjXIAAgBIABAAIAAABgAA/jbIAAgBIABAAIAAABgAATjdIAAAAIACAAIAAAAgABBjiIAAgBIABAAIAAABgABDjpIAAgBIABAAIAAABgABEjwIAAgBIABAAIAAABgABFj3IAAgBIABAAIAAABgABHj/IAAgBIABAAIAAABgABJkGIAAgBIAAAAIAAABgABLkMIAAgBIAAAAIAAABgAANkwIAAAAIABAAIAAAAgAAOkwIAAgBIAAAAIAAABgAAOkxIAAgCIABAAIAAACgAAPkzIAAAAIABAAIAAAAgAAQkzIAAgBIAAAAIAAABgAAQk0IAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIAAAAIAAgBIACAAIAAABIgCAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIAAABgAAQk0gAAVk4IAAAAIAAAAIAAgBIABAAIAAABIgBAAIAAAAg");
	this.shape_104.setTransform(308.5,375);

	this.shape_105 = new cjs.Shape();
	this.shape_105.graphics.f("#CFCFCF").s().p("Ag3EsIAAAAIABAAIAAAAgAAyEsIAAgBIABAAIAAABgAg6EsIAAgBIAAAAIAAABgAA3ErIAAgBIABAAIAAABgAg9ErIAAgBIAAAAIAAABgAA9EqIAAgBIABAAIAAABgABCEpIAAgBIAAAAIAAABgABGEoIAAAAIABAAIAAAAgABKEoIAAgBIABAAIAAABgABOEnIAAgBIABAAIAAABgABYEkIAAAAIABAAIAAAAgABhEiIAAgBIABAAIAAABgABuEeIAAgBIABAAIAAABgABwEdIAAAAIABAAIAAAAgABzEdIAAgBIABAAIAAABgAB1EcIAAgBIABAAIAAABgAB7EZIAAAAIABAAIAAAAgAhlEZIAAAAIAAAAIAAAAgAB/EYIAAgBIABAAIAAABgAhvETIAAgBIAAAAIAAABgAiAEHIAAAAIABAAIAAAAgACjEFIAAgBIABAAIAAABgACmEDIAAAAIAAAAIAAAAgACoECIAAgBIABAAIAAABgAC0D4IAAAAIABAAIAAAAgAC6D0IAAgBIABAAIAAABgAibDwIAAgBIABAAIAAABgAg3DkIAAgBIABAAIAAABgAg8DjIAAgBIABAAIAAABgABhDcIAAgBIABAAIAAABgABmDbIAAAAIABAAIAAAAgAj7DZIAAgBIAAAAIAAABgADVDWIAAgBIABAAIAAABgABmDWIAAgBIAAAAIAAABgAj4DWIAAgBIABAAIAAABgAhgDUIAAgBIABAAIAAABgAj0DTIAAgBIABAAIAAABgAhlDQIAAAAIABAAIAAAAgAjwDQIAAgBIABAAIAAABgAhpDNIAAAAIABAAIAAAAgAjsDNIAAgBIABAAIAAABgAhsDLIAAgBIAAAAIAAABgABbDJIAAAAIABAAIAAAAgAjoDJIAAAAIABAAIAAAAgAhzDGIAAgBIABAAIAAABgAjHDBIAAgBIABAAIAAABgABMC6IAAAAIAAAAIAAAAgAkGC6IAAAAIABAAIAAAAgABIC3IAAAAIABAAIAAAAgAiIC0IAAgBIgBAAIAAAAIABAAIAAAAIABAAIAAABgAA+CwIAAgBIAAAAIAAABgAj8CmIAAgBIABAAIAAABgAApClIAAgBIABAAIAAABgACyChIAAAAIABAAIAAAAgACfCfIAAgBIABAAIAAABgACWCdIAAAAIABAAIAAAAgAAECZIAAgBIABAAIAAABgAABCYIAAgBIAAAAIAAABgAB6CWIAAAAIABAAIAAAAgABwCTIAAgBIABAAIAAABgABqCRIAAgBIABAAIAAABgABlCOIAAAAIABAAIAAAAgAjfCFIAAgBIABAAIAAABgAjZCAIAAAAIABAAIAAAAgABHB+IAAgBIABAAIAAABgAjRB6IAAgBIABAAIAAABgAiSB0IAAgBIABAAIAAABgAiNBuIAAAAIABAAIAAAAgAA/BqIAAAAIABAAIAAAAgAiJBpIAAgBIABAAIAAABgAi4BoIAAgBIABAAIAAABgADcBiIAAgBIABAAIAAABgABJBiIAAgBIAAAAIAAABgAitBhIAAgBIABAAIAAABgADqBfIAAgBIABAAIAAABgABNBfIAAgBIABAAIAAABgAD0BbIAAAAIABAAIAAAAgAiiBaIAAgBIABAAIAAABgABtBPIAAgBIABAAIAAABgAAIBOIAAgBIABAAIAAABgAA4BNIAAAAIABAAIAAAAgAABBNIAAAAIAAAAIAAAAgAA7BNIAAgBIAAAAIAAABgAEGBMIAAgBIABAAIAAABgACRBKIAAgBIAAAAIAAABgAB6BKIAAgBIABAAIAAABgACOBJIAAAAIABAAIAAAAgACMBJIAAgBIABAAIAAABgABHBJIAAgBIABAAIAAABgACJBIIAAgBIABAAIAAABgABJBIIAAgBIABAAIAAABgAgzBIIAAgBIAAAAIAAABgACHBHIAAgBIABAAIAAABgAg7BHIAAgBIABAAIAAABgABWBEIAAgBIABAAIAAABgABYBDIAAgBIABAAIAAABgABnA+IAAAAIABAAIAAAAgAAJA8IAAgBIABAAIAAABgAB2A6IAAgBIABAAIAAABgAABA6IAAgBIAAAAIAAABgAAAA3IAAgBIAAAAIAAABgACFA1IAAgBIABAAIAAABgACHA0IAAgBIABAAIAAABgAgCAzIAAAAIABAAIAAAAgACUAwIAAgBIAAAAIAAABgAgEAwIAAgBIAAAAIAAABgACWAvIAAAAIABAAIAAAAgAgHAtIAAgBIABAAIAAABgACjAsIAAgBIAAAAIAAABgAClArIAAgBIABAAIAAABgAC0AmIAAgBIAAAAIAAABgAD0AlIAAAAIABAAIAAAAgADxAjIAAgBIAAAAIAAABgADDAhIAAAAIAAAAIAAAAgADuAhIAAgBIAAAAIAAABgAhuAfIAAgBIABAAIAAABgADRAdIAAgBIABAAIAAABgADUAcIAAgBIABAAIAAABgAhlAbIAAgBIABAAIAAABgADgAYIAAgBIABAAIAAABgAgWAXIAAgBIAAAAIAAABgAhbAXIAAgBIABAAIAAABgAgVAVIAAgBIABAAIAAABgAigAVIAAgBIABAAIAAABgAAdATIAAgBIABAAIAAABgAhSATIAAgBIABAAIAAABgAgTASIAAAAIABAAIAAAAgAgSAQIAAgBIABAAIAAABgAhIAPIAAAAIAAAAIAAAAgAgQAOIAAgBIABAAIAAABgAAeANIAAgBIAAAAIAAABgAgPALIAAAAIABAAIAAAAgAg/ALIAAAAIABAAIAAAAgAinAHIAAgBIABAAIAAABgAAngaIAAgBIABAAIAAABgAAvgfIAAgBIABAAIAAABgAgyglIAAgBIABAAIAAABgAgxgoIAAgBIABAAIAAABgAgwgqIAAgBIABAAIAAABgAA+grIAAgBIAAAAIAAABgAgvguIAAgBIABAAIAAABgAijgxIAAgBIABAAIAAABgAgog7IAAgBIABAAIAAABgABShAIAAAAIABAAIAAAAgABVhDIAAgBIABAAIAAABgAghhDIAAgBIAAAAIAAABgAinhFIAAgBIABAAIAAABgABXhHIAAAAIABAAIAAAAgAgdhIIAAgBIAAAAIAAABgAiohIIAAgBIABAAIAAABgABZhJIAAgBIABAAIAAABgAiphOIAAAAIAAAAIAAAAgAishWIAAAAIABAAIAAAAgAishYIAAgBIAAAAIAAABgAithaIAAgBIABAAIAAABgAiuhdIAAAAIABAAIAAAAgAivhfIAAgBIABAAIAAABgAiyhoIAAAAIABAAIAAAAgAAChpIAAgBIABAAIAAABgABohsIAAAAIABAAIAAAAgAAIhuIAAgBIAAAAIAAABgAAMhyIAAgBIABAAIAAABgAg7iHIAAgBIABAAIAAABgACLiNIAAgBIABAAIAAABgACNiPIAAgBIABAAIAAABgACQiQIAAgBIABAAIAAABgAgxiRIAAgBIABAAIAAABgAgkiXIAAAAIABAAIAAAAgAgeiYIAAgBIABAAIAAABgAgVibIAAAAIABAAIAAAAgAAAifIAAAAIABAAIAAAAgAAGifIAAgBIABAAIAAABgABEigIAAgBIABAAIAAABgAhPihIAAgBIAAAAIAAABgAhJiiIAAAAIABAAIAAAAgAhEijIAAgBIABAAIAAABgAhWijIAAgBIABAAIAAABgAhcimIAAAAIABAAIAAAAgAg3inIAAgBIABAAIAAABgAgxiqIAAAAIABAAIAAAAgAgtirIAAgBIABAAIAAABgAgpitIAAAAIABAAIAAAAgACqiuIAAgBIAAAAIAAABgAh5ixIAAAAIABAAIAAAAgAh/izIAAgBIABAAIAAABgAiFi1IAAgBIAAAAIAAABgAgPi7IAAgBIABAAIAAABgAgMi8IAAgBIABAAIAAABgAgJi/IAAgBIABAAIAAABgACgjBIAAgBIABAAIAAABgAgFjCIAAgBIABAAIAAABgACVjHIAAAAIABAAIAAAAgAB7jOIAAgBIABAAIAAABgAALjWIAAgBIABAAIAAABgABGjXIAAgBIABAAIAAABgAA/jYIAAgBIABAAIAAABgAATjrIAAAAIABAAIAAAAgABFkHIAAgBIABAAIAAABgABHkOIAAgBIABAAIAAABgABJkVIAAgBIAAAAIAAABgABKkcIAAgBIABAAIAAABgABMkjIAAgBIAAAAIAAABgABNkqIAAgBIABAAIAAABg");
	this.shape_105.setTransform(308.5,376.5);

	this.shape_106 = new cjs.Shape();
	this.shape_106.graphics.f("#6F6F6F").s().p("AgsEtIAAgBIABAAIAAABgAAlErIAAAAIAAAAIAAAAgAhOEkIAAgBIABAAIAAABgAhSEjIAAgBIABAAIAAABgAhZEgIAAgBIABAAIAAABgAhcEeIAAAAIABAAIAAAAgABrEeIAAgBIABAAIAAABgAhdEeIAAgBIAAAAIAAABgABtEdIAAgBIABAAIAAABgABwEcIAAgBIABAAIAAABgAByEbIAAgBIABAAIAAABgAhmEYIAAAAIABAAIAAAAgAB/EXIAAgBIAAAAIAAABgAhqEXIAAgBIABAAIAAABgAhsEVIAAgBIAAAAIAAABgACEEUIAAgBIABAAIAAABgAhwESIAAgBIABAAIAAABgACVEMIAAgBIABAAIAAABgAh6EMIAAgBIAAAAIAAABgAh+EJIAAgBIABAAIAAABgACbEIIAAgBIABAAIAAABgAiBEGIAAAAIABAAIAAAAgAChEFIAAgBIABAAIAAABgACjEDIAAgBIABAAIAAABgAiIEBIAAgBIAAAAIAAgBIAAAAIAAABIABAAIAAABgACrD9IAAAAIABAAIAAAAgAiMD9IAAAAIAAAAIAAAAgACyD3IAAAAIABAAIAAAAgAiTD3IAAAAIABAAIAAAAgAC3D0IAAgBIABAAIAAABgAC4DzIAAgBIAAAAIAAABgAiaDyIAAgBIAAAAIAAgBIAAAAIAAABIABAAIAAABgAibDwIAAgBIABAAIAAABgAicDvIAAgBIABAAIAAABgAC/DsIAAAAIAAAAIAAAAgADBDqIAAAAIABAAIAAAAgADCDqIAAgBIABAAIAAABgADDDpIAAgBIAAAAIAAABgAhDDhIAAAAIABAAIAAAAgADKDgIAAAAIABAAIAAAAgAhKDgIAAgBIABAAIAAABgAhMDfIAAgBIABAAIAAABgADODbIAAgBIABAAIAAABgABiDbIAAgBIABAAIAAABgABoDaIAAAAIAAgBIABAAIAAABgAiwDaIAAAAIgBAAIAAgBIABAAIAAABIAAAAIAAAAgAiyDZIAAAAIABAAIAAAAgAj+DZIAAgBIABAAIAAABgADRDYIAAgBIABAAIAAABgABmDYIAAgBIABAAIAAABgAhcDXIAAgBIABAAIAAABgAj6DVIAAAAIABAAIAAAAgADUDVIAAgCIABAAIAAACgABkDVIAAgCIABAAIAAACgA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gAi7BpIAAgBIABAAIAAABgAiKBnIAAgBIABAAIAAABgAi5BnIAAgBIABAAIAAABgABBBlIAAAAIABAAIAAAAgABCBlIAAgBIAAAAIAAgBIABAAIAAABIgBAAIAAABgABCBlgAiFBiIAAgBIAAAAIAAABgAiwBiIAAgBIAAAAIAAABgABGBhIAAgBIABAAIAAABgABJBgIAAgCIAAAAIAAACgADnBeIAAgBIABAAIAAABgABLBeIAAgBIABAAIAAABgAiqBeIAAgBIABAAIAAABgABNBcIAAgBIABAAIAAABgAiBBcIAAgBIABAAIAAABgAimBbIAAgBIABAAIAAABgAD1BZIAAAAIAAAAIAAAAgABTBZIAAAAIABAAIAAAAgAD2BZIAAgBIABAAIAAABgAihBZIAAgBIABAAIAAABgAD4BYIAAgBIABAAIAAABgAhPBXIAAgBIABAAIAAABgAh8BXIAAgBIABAAIAAABgAhOBVIAAgBIABAAIAAABgAibBVIAAgBIABAAIAAABgAD8BUIAAgBIABAAIAAABgABeBTIAAAAIABAAIAAAAgAhNBTIAAAAIABAAIAAAAgAhMBSIAAAAIABAAIAAAAgAiWBSIAAAAIABAAIAAAAgAh3BSIAAgBIABAAIAAABgAiUBSIAAgBIABAAIAAABgABlBRIAAgBIABAAIAAABgAhLBRIAAgBIAAAAIAAABgABpBPIAAgBIAAAAIAAABgAhLBPIAAgBIABAAIAAABgAEDBOIAAgCIABAAIAAACgAhKBOIAAgCIABAAIAAACgAA4BMIAAAAIABAAIAAAAgAEEBMIAAgBIAAAAIAAABgABxBMIAAgBIAAAAIAAABgAhJBMIAAgBIABAAIAAABgAgSBLIAAgBIAAAAIAAABgAgaBKIAAgBIABAAIAAABgAhIBKIAAgBIAAAAIAAABgACRBJIAAAAIAAAAIAAAAgAB3BJIAAAAIABAAIAAAAgACOBJIAAgBIABAAIAAABgAB5BJIAAgBIABAAIAAABgAhIBJIAAgBIABAAIAAABgACMBIIAAgBIABAAIAAABgABHBIIAAgBIABAAIAAABgACKBHIAAAAIAAAAIAAAAgAhHBHIAAAAIABAAIAAAAgACHBHIAAgBIABAAIAAABgACFBGIAAgBIABAAIAAABgABWBDIAAgBIABAAIAAABgABYBCIAAgBIABAAIAAABgABlA/IAAgBIABAAIAAABgABnA+IAAgBIABAAIAAABgAAGA8IAAgCIABAAIAAACgAACA8IAAgCIAAAAIAAACgAB0A5IAAAAIAAAAIAAAAgAB2A5IAAgBIABAAIAAABgAALA5IAAgBIABAAIAAABgAAAA4IAAgBIAAAAIAAABgAgBA1IAAgBIABAAIAAABgACFA0IAAAAIABAAIAAAAgAAQA0IAAgBIABAAIAAABgAgEAyIAAgBIABAAIAAABgAECAxIAAgBIABAAIAAABgAASAxIAAgBIABAAIAAABgACUAvIAAgBIAAAAIAAABgAATAvIAAgBIAAAAIAAABgAgGAuIAAAAIABAAIAAAAgAATAuIAAgBIABAAIAAABgAAUAtIAAgBIABAAIAAABgAgIAsIAAgBIABAAIAAABgACjArIAAgBIAAAAIAAABgAD4AqIAAgCIABAAIAAACgAgLAoIAAgBIABAAIAAABgAD1AnIAAgBIAAAAIAAABgACxAmIAAgBIABAAIAAABgAAXAmIAAgBIAAAAIAAABgAC0AlIAAAAIABAAIAAAAgAgNAlIAAAAIABAAIAAAAgADyAlIAAgBIAAAAIAAABgADuAiIAAAAIABAAIAAAAgAgPAiIAAAAIABAAIAAAAgADAAiIAAgBIABAAIAAABgADDAhIAAgBIAAAAIAAABgAAYAhIAAgBIABAAIAAABgADrAgIAAgBIABAAIAAABgAgSAfIAAgBIABAAIAAABgAhzAfIAAgBIABAAIAAABgADoAdIAAgBIABAAIAAABgADRAcIAAgBIABAAIAAABgAgUAcIAAgBIABAAIAAABgADlAbIAAAAIABAAIAAAAgAhpAbIAAAAIABAAIAAAAgAiaAbIAAAAIABAAIAAAAgAgWAZIAAgBIAAAAIAAABgAhgAXIAAgBIABAAIAAABgAieAXIAAgBIAAAAIAAABgAAbAVIAAgBIAAAAIAAABgAgWAVIAAgBIAAAAIAAABgAhWATIAAgBIAAAAIAAABgAihATIAAgBIAAAAIAAABgAgVASIAAAAIABAAIAAAAgAijARIAAgBIABAAIAAABgAgTAQIAAAAIABAAIAAAAgAhNAQIAAgBIABAAIAAABgAgSAOIAAgBIABAAIAAABgAgQALIAAgBIABAAIAAABgAhEALIAAgBIABAAIAAABgAgOAJIAAAAIAAAAIAAAAgAinAJIAAAAIABAAIAAAAgAg6AIIAAgBIABAAIAAABgAipAEIAAgBIABAAIAAABgAgxADIAAAAIABAAIAAAAgAgoAAIAAAAIABAAIAAAAgAgegDIAAAAIABAAIAAAAgAgVgGIAAgBIABAAIAAABgAgLgKIAAgBIAAAAIAAABgAAlgaIAAgBIAAAAIAAABgAAngcIAAgBIABAAIAAABgAAtggIAAgBIAAAAIAAABgAAzglIAAAAIABAAIAAAAgAA3goIAAgBIAAAAIAAABgAA7gsIAAAAIABAAIAAAAgAgxgsIAAAAIABAAIAAAAgABCgyIAAAAIAAAAIAAAAgAgvgyIAAAAIABAAIAAAAgABCgyIAAgBIABAAIAAABgABCgygABKg6IAAgBIABAAIAAABgABLg7IAAgBIABAAIAAABgABLg7gAgpg7IAAgBIABAAIAAABgABMg8IAAAAIABAAIAAAAgAglhAIAAgBIABAAIAAABgABQhBIAAgBIABAAIAAABgAgjhEIAAAAIABAAIAAAAgABUhFIAAgBIAAAAIAAABgAgghHIAAgBIABAAIAAABgABWhIIAAgBIABAAIAAABgABYhLIAAgBIAAAAIAAABgABZhNIAAgBIABAAIAAABgAiqhOIAAgBIABAAIAAABgAgZhPIAAAAIAAAAIAAAAgAgZhPIAAgBIABAAIAAABgABbhRIAAAAIABAAIAAAAgABchSIAAgBIABAAIAAABgAishWIAAgBIAAAAIAAABgAithYIAAAAIABAAIAAAAgABghaIAAgBIABAAIAAABgAgLhdIAAgBIAAAAIAAABgAivhdIAAgBIABAAIAAABgAgLheIAAAAIABAAIAAgBIABAAIAAABIgBAAIAAAAgAiwhfIAAgBIABAAIAAABgAgChlIAAgBIABAAIAAABgAgBhmIAAgBIABAAIAAABgAizhoIAAgBIABAAIAAABgABmhqIAAgBIAAAAIAAABgAAChqIAAgBIABAAIAAABgAi1huIAAgBIABAAIAAABgAAHhvIAAgBIABAAIAAABgAi3hyIAAgBIABAAIAAABgAi4h1IAAgBIABAAIAAABgABph9IAAAAIAAAAIAAAAgAi9iBIAAgBIABAAIAAABgAB4iFIAAAAIABAAIAAAAgAB6iFIAAgBIABAAIAAABgAi/iGIAAgBIABAAIAAABgAjAiHIAAgBIABAAIAAABgACBiKIAAAAIABAAIAAAAgACFiMIAAgBIABAAIAAABgAg7iMIAAgBIABAAIAAABgACJiOIAAgBIABAAIAAABgACLiPIAAgBIABAAIAAABgACNiRIAAgBIABAAIAAABgAg1iRIAAgBIABAAIAAABgAjFiSIAAgBIABAAIAAABgAjFiUIAAgBIAAAAIAAABgACTiVIAAgBIABAAIAAABgAgtiVIAAgBIABAAIAAABgAjGiVIAAgBIABAAIAAABgAgniXIAAgBIABAAIAAABgACXiYIAAgBIABAAIAAABgACYiZIAAgBIAAAAIAAABgACYiZgAgciaIAAgCIABAAIAAACgABHidIAAgBIABAAIAAABgABGieIAAgBIAAAAIAAgBIgBAAIAAAAIABAAIAAAAIAAAAIAAABIABAAIAAABgABEigIAAgBIABAAIAAABgAABigIAAgBIABAAIAAABgABDihIAAAAIABAAIAAAAgAhLihIAAgBIAAAAIAAABgAChiiIAAgBIABAAIAAABgAhGijIAAgBIABAAIAAABgAhWijIAAgBIAAAAIAAABgAjNijIAAgBIABAAIAAABgAjOilIAAgBIABAAIAAABgACjimIAAgBIABAAIAAABgAhdimIAAgBIABAAIAAABgAjPinIAAAAIABAAIAAAAgAg5inIAAgBIAAAAIAAABgAhjioIAAgBIABAAIAAABgAgzipIAAgBIABAAIAAABgAhpiqIAAgBIABAAIAAABgAjQirIAAgBIABAAIAAABgAgrisIAAgCIAAAAIAAACgAhvisIAAgCIAAAAIAAACgAjNisIAAgCIABAAIAAACgAjLiuIAAgBIABAAIAAABgAgkiwIAAgBIAAAAIAAABgAjJiwIAAgBIABAAIAAABgAjGiyIAAAAIABAAIAAAAgAiAizIAAAAIABAAIAAAAgAjEizIAAAAIABAAIAAAAgAgbi0IAAgBIABAAIAAABgAjCi0IAAgBIABAAIAAABgAgZi1IAAgBIAAAAIAAABgAiGi1IAAgBIABAAIAAABgAi/i2IAAgBIABAAIAAABgAiMi4IAAgBIAAAAIAAABgAi9i4IAAgBIABAAIAAABgAi6i5IAAgBIAAAAIAAABgAgSi6IAAgBIABAAIAAABgAiTi6IAAgBIABAAIAAABgAi4i7IAAAAIABAAIAAAAgAgPi7IAAgBIABAAIAAABgAiZi8IAAgBIABAAIAAABgAi2i8IAAgBIABAAIAAABgAgKjAIAAgBIABAAIAAABgACejCIAAgBIABAAIAAABgACcjDIAAAAIABAAIAAAAgAgGjDIAAAAIABAAIAAAAgACUjGIAAgBIABAAIAAABgACRjIIAAgBIAAAAIAAABgACHjMIAAAAIABAAIAAAAgAADjNIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgAAGjSIAAgBIABAAIAAABgABojTIAAAAIABAAIAAAAgAAOjfIAAAAIABAAIAAAAgAA7jfIAAgBIAAAAIAAABgAAPjgIAAgBIABAAIAAABgAA8jnIAAAAIABAAIAAAAgAARjnIAAAAIABAAIAAAAgAA+juIAAgBIAAAAIAAABgAA/j1IAAgBIABAAIAAABgABBj8IAAAAIABAAIAAAAgAAUkcIAAAAIABAAIAAAAgAATkeIAAgBIABAAIAAABgAATkhIAAgCIAAAAIAAACgAASklIAAgBIABAAIAAABgAARkoIAAAAIABAAIAAAAgAAQkrIAAgBIABAAIAAABg");
	this.shape_106.setTransform(308.6,376.6);

	this.shape_107 = new cjs.Shape();
	this.shape_107.graphics.f("#666666").s().p("AAOE8IAAAAIgFAAIgBAAIguAAIAAAAIgDAAIgBAAIAAgBIgBAAIAAAAIgBAAIgBAAIgBAAIAAAAIgBAAIgBAAIAAgBIAAAAIgBAAIgCAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAAAIgBAAIAAAAIAAgBIgBAAIgBAAIgBAAIgBAAIAAgCIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAAAIgBAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAAAIAAgBIgBAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAAAIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIgBAAIAAgBIgBAAIAAAAIAAgBIgBAAIgBAAIAAgBIAAAAIAAAAIgBAAIgBAAIAAgCIgBAAIAAAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIgBAAIgBAAIAAAAIAAAAIgBAAIAAgBIAAAAIAAgBIgBAAIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAgBIgBAAIAAAAIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAAAIAAgCIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgCIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIgCAAIAAAAIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIAAAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIAAgBIAAAAIAAAAIgCAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIAAgBIgBAAIAAgCIgBAAIAAACIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIAAABIgBAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIgBAAIAAAAIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgCAAIAAABIgBAAIAAABIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIAAAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAAAIgCAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgJAAIAAgXIAAgBIAAAAIAAgBIAAAAIAAgBIAAgBIAAAAIAAgBIAAgBIAAgBIAAgBIAAAAIABAAIAAgBIAAgBIAAAAIAAgBIABAAIAAgBIAAgBIAAAAIABAAIAAgBIAAgCIAAAAIABAAIAAgBIAAgBIAAAAIAAgBIAAAAIACAAIAAgBIAAgBIAAAAIAAAAIAAgBIAAgBIABAAIAAgBIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAAAIAAgBIAAgBIABAAIAAAAIABAAIAAgBIAAgBIAAAAIAAgBIABAAIAAAAIAAgCIABAAIAAAAIABAAIAAgBIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIAAAAIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAgBIAAgBIABAAIAAgBIAAAAIAAAAIABAAIAAgBIACAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAAAIABAAIAAgCIABAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAAAIABAAIACAAIAAgBIAAAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIABAAIAAAAIAAAAIAAgCIABAAIAAAAIABAAIAAgBIABAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAAAIABAAIAAAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIAAAAIABAAIAAgBIACAAIAAgBIAAAAIAAgBIABAAIABAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAAAIAAgBIABAAIAAAAIABAAIABAAIAAgBIABAAIAAgBIABAAIABAAIAAgBIAAAAIAAAAIABAAIAAgCIABAAIABAAIAAgBIAAAAIAAAAIABAAIABAAIAAgBIAAAAIAAgBIABAAIACAAIAAgBIAAAAIAAAAIABAAIABAAIAAgBIABAAIAAgBIAAAAIABAAIAAAAIAAAAIAAgBIABAAIABAAIAAgBIABAAIAAgBIABAAIABAAIAAgBIABAAIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIABAAIAAgBIABAAIABAAIAAgBIAAAAIAAgBIABAAIAAAAIAAgBIABAAIABAAIAAAAIABAAIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAgBIABAAIABAAIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIABAAIABAAIAAgBIABAAIABAAIAAgBIABAAIABAAIAAgBIAAAAIAAgBIABAAIAAAAIAAgBIABAAIABAAIAaAAIAAABIgBAAIAAABIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAACIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAAAIgBAAIAAABIAAAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIAAAAIgBAAIAAACIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIAAABIgBAAIAAABIAAAAIAAABIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIABAAIAAACIABAAIAAAAIAAAAIAAABIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAAAIAAAAIAAABIAAABIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAABIAAAAIAAAAIABAAIAAACIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAAAIAAAAIAAABIABAAIAAABIABAAIAAAAIAAABIABAAIAAAAIABAAIAAACIABAAIAAABIAAAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIABAAIAAABIAAAAIAAABIABAAIAAAAIAAAAIAAABIABAAIAAABIABAAIAAABIABAAIABAAIAAABIABAAIAAABIABAAIAAABIAAAAIAAAAIABAAIAAABIABAAIAAAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAABIAAAAIABAAIAAABIABAAIAAABIABAAIAAABIABAAIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAAAIAAAAIABAAIAAABIABAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAAAIAAABIABAAIAAABIABAAIABAAIAAABIAAAAIAAABIABAAIABAAIAAABIAAAAIAAABIABAAIABAAIAAAAIABAAIAAABIABAAIABAAIAAAAIABAAIAAAAIAAABIABAAIAAABIAAAAIABAAIAAABIABAAIABAAIAAABIABAAIABAAIAAABIABAAIAAAAIAAABIABAAIABAAIAAAAIAAAAIABAAIAAABIABAAIABAAIAAABIAAAAIABAAIAAAAIABAAIABAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAABIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAAAIABAAIABAAIABAAIAAABIAAAAIABAAIABAAIABAAIABAAIAAABIABAAIABAAIAAAAIABAAIBgAAIABAAIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIABAAIABAAIAAAAIABAAIAAgBIAAAAIABAAIABAAIABAAIABAAIAAAAIACAAIAAgBIAAAAIABAAIABAAIABAAIAAAAIABAAIABAAIAAgBIAAAAIABAAIABAAIABAAIABAAIABAAIABAAIAAAAIAAAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAgBIAAAAIACAAIAAAAIABAAIABAAIABAAIABAAIAAgBIAAAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAgBIABAAIABAAIAAAAIABAAIAAAAIABAAIABAAIAAAAIABAAIABAAIAAAAIACAAIAAAAIABAAIABAAIAAgBIABAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAgBIABAAIABAAIABAAIAAAAIABAAIAAAAIABAAIAAgBIgBAAIAAgBIAAAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIAAgBIgCAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAAAIgBAAIAAgBIAAAAIAAgBIgBAAIAAAAIgBAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAgBIgCAAIAAAAIAAAAIgBAAIAAgBIgBAAIAAgCIgBAAIAAAAIgBAAIAAAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAAAIgBAAIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIgBAAIAAgBIgBAAIAAAAIAAgBIgBAAIgBAAIAAgBIgBAAIAAgBIAAAAIgCAAIAAgBIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIAAAAIAAgBIgBAAIAAAAIAAgBIgBAAIgBAAIAAAAIgBAAIgBAAIAAgBIgBAAIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIAAAAIAAAAIgBAAIgBAAIgBAAIAAgCIAAAAIgCAAIAAAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIgBAAIAAAAIAAgBIgCAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAAAIAAAAIgBAAIgCAAIAAAAIgBAAIAAgBIAAAAIAAAAIAAAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIgBAAIgBAAIAAAAIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIgCAAIAAgBIAAAAIgBAAIgBAAIgBAAIAAAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIgBAAIgBAAIgBAAIAAAAIgCAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAAAIgDAAIAAAAIgDAAIAAgCIgBAAIgBAAIgCAAIgBAAIgFAAIAAAAIgBAAIgGAAIgPAAIAAgBIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAAAIAAgBIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAAAIAAgCIgBAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAAAIAAgBIAAAAIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIgBAAIAAgCIAAgBIgBAAIAAAAIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAgBIAAAAIAAgBIAAAAIABAAIAAgBIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIAAgBIABAAIAAAAIAAgBIABAAIAAAAIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIAAgBIABAAIAAgBIAAAAIABAAIAAgBIAAgBIABAAIAAgBIAAAAIABAAIAAgBIAAgBIABAAIAAAAIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIAAgBIABAAIAAgBIAAAAIAAAAIAAgBIAAAAIABAAIAAgBIAAgBIABAAIAAgBIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAgBIACAAIAAAAIACAAIABAAIAAABIABAAIABAAIACAAIABAAIABAAIABAAIABAAIAAAAIABAAIAAAAIABAAIACAAIAAAAIACAAIABAAIABAAIAAABIABAAIAAAAIABAAIACAAIABAAIABAAIACAAIAAABIABAAIABAAIABAAIACAAIABAAIACAAIAAABIABAAIABAAIABAAIAAAAIACAAIABAAIAAAAIAAABIABAAIACAAIABAAIABAAIAAAAIACAAIAAAAIABAAIAAABIABAAIABAAIADAAIAAAAIACAAIABAAIAAABIABAAIABAAIACAAIABAAIACAAIABAAIAAAAIABAAIAAAAIABAAIAAAAIACAAIABAAIABAAIAAABIABAAIAAAAIABAAIABAAIABAAIABAAIABAAIACAAIAAAAIABAAIAAAAIABAAIABAAIACAAIABAAIACAAIAAABIAdAAIABAAIAAgBIAAAAIABAAIABAAIAAAAIABAAIABAAIAAAAIAAgBIACAAIAAAAIABAAIABAAIAAAAIABAAIAAAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIAAgBIABAAIABAAIAAAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIAAAAIAAgBIACAAIAAAAIABAAIAAAAIABAAIABAAIABAAIAAgBIAAAAIABAAIAAAAIAAgBIABAAIABAAIABAAIAAgBIABAAIABAAIABAAIAAAAIAAAAIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIAAgBIAAAAIACAAIAAAAIAAAAIABAAIABAAIABAAIAAgBIAAAAIABAAIABAAIAAgCIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAgBIAAAAIABAAIAAAAIAAgBIABAAIABAAIABAAIAAgBIABAAIAAAAIACAAIAAAAIAAAAIABAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAgBIACAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAgBIABAAIABAAIAAAAIABAAIAAAAIABAAIABAAIABAAIAAgBIABAAIABAAIAAAAIAAAAIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIAAgBIAAAAIACAAIAAAAIAAgBIABAAIABAAIABAAIAAAAIAAgBIABAAIABAAIAAAAIAAAAIABAAIABAAIABAAIAAgCIABAAIABAAIABAAIAAAAIAAAAIABAAIAAAAIAAgBIABAAIABAAIABAAIAAgBIABAAIAAAAIACAAIAAAAIAAgBIABAAIABAAIABAAIAAAAIAAAAIABAAIABAAIAAgBIAAAAIABAAIABAAIAAgBIABAAIABAAIABAAIAAAAIABAAIAAAAIABAAIAAgBIAAAAIABAAIABAAIABAAIAAgBIAAAAIABAAIACAAIAAgBIAAAAIABAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIAAAAIABAAIAAAAIABAAIABAAIAAgBIAAAAIABAAIABAAIAAgBIABAAIAAAAIABAAIAAAAIACAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAgBIABAAIABAAIAAAAIAAgBIABAAIABAAIABAAIAAAAIABAAIABAAIAAAAIABAAIAAgCIABAAIABAAIAAAAIAAAAIABAAIABAAIAAAAIAAgBIABAAIACAAIAAAAIAAgBIABAAIABAAIABAAIAAgBIAAAAIABAAIAAAAIAAgBIABAAIABAAIACAAIAAABIABAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAIAAAAIABAAIAAACIABAAIAAAAIABAAIAAABIAAAAIABAAIAAABIABAAIAAABIABAAIAAAAIABAAIABAAIAAABIABAAIAAABIAAAAIAAAAIABAAIAAAAIAAABIABAAIAAABIABAAIAAABIABAAIABAAIAAABIABAAIAAABIABAAIAAABIAAAAIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAACIABAAIABAAIAAAAIAAAAIAAABIABAAIAAABIAAAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAAUIAAAAIAAABIAAABIgBAAIAAABIAAABIAAABIgBAAIAAABIAAAAIgBAAIAAABIAAAAIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIgBAAIgBAAIgBAAIAAAAIAAABIgDAAIAAgBIAAAAIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAgBIgCAAIgBAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAAAIgBAAIAAAAIgCAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAgBIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAgBIAAAAIgCAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAgBIAAAAIgBAAIgBAAIAAAAIgBAAIgBAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIAAAAIgCAAIAAAAIAAgBIgBAAIgBAAIgBAAIAAgBIgBAAIAAAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAgBIgBAAIgBAAIgBAAIAAgBIgBAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIgCAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAAAIgBAAIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIAAABIAAAAIgBAAIAAABIAAAAIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAABIAAAAIgBAAIAAABIAAAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAABIgBAAIAAABIAAAAIAAAAIAAABIgBAAIAAACIgBAAIAAAAIAAABIAAABIAAAAIAAABIAAAAIAAABIAAABIAAABIAAABIAAAAIAAABIABAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAIABAAIABAAIAAABIABAAIAAAAIABAAIAAABIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIABAAIAAAAIABAAIAAACIAAAAIACAAIAAAAIAAAAIAAABIABAAIABAAIAAABIABAAIAAABIABAAIAAAAIAAAAIABAAIAAABIAAAAIABAAIAAABIABAAIABAAIAAAAIABAAIABAAIAAABIABAAIAAAAIAAABIABAAIAAABIAAAAIABAAIAAABIABAAIABAAIAAABIABAAIAAAAIAAABIACAAIAAAAIAAAAIABAAIABAAIAAABIABAAIAAAAIAAABIABAAIABAAIAAAAIAAAAIABAAIABAAIAAABIABAAIABAAIAAABIABAAIABAAIAAAAIAAABIABAAIAAAAIAAAAIABAAIABAAIAAACIABAAIABAAIAAAAIAAAAIACAAIAAAAIABAAIAAABIABAAIABAAIAAAAIAAABIABAAIABAAIAAABIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAAAIABAAIABAAIAAAAIABAAIAAAAIACAAIAAABIAAAAIABAAIABAAIABAAIAAABIAAAAIABAAIABAAIAAAAIAAABIABAAIABAAIABAAIABAAIAAABIABAAIABAAIAAAAIABAAIAAABIAAAAIABAAIABAAIABAAIABAAIAAABIAAAAIACAAIAAAAIABAAIABAAIAAAAIABAAIAAAAIABAAIABAAIAAAAIAAABIABAAIABAAIABAAIABAAIABAAIABAAIAAAAIAAAAIABAAIAAAAIABAAIABAAIABAAIAAABIABAAIAAAAIACAAIAAAAIABAAIABAAIABAAIAAABIAAAAIABAAIABAAIACAAIABAAIABAAIABAAIABAAIAAABIAAAAIABAAIAAAAIABAAIABAAIABAAIAAAAIADAAIAAABIABAAIABAAIABAAIACAAIAAAAIADAAIABAAIABAAIAAAAIAAAAIAFAAIAAAAIAFAAIABAAIAMAAIAAAvIAAAAIAAABIAAABIgBAAIAAABIgBAAIAAAAIAAAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAABIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAABIgCAAIAAAAIgBAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAAAIAAABIgBAAIAAACIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgCAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIAAAAIAAABIgCAAIAAAAIAAABIgBAAIAAAAIgBAAIgBAAIAAACIgBAAIAAAAIAAAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIAAAAIgCAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIgBAAIgBAAIAAACIAAAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIgBAAIAAABIAAAAIgCAAIAAAAIAAABIgBAAIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIgBAAIAAABIAAAAIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIgBAAIAAABIAAAAIgCAAIAAAAIgBAAIAAABIgBAAIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIgBAAIAAAAIAAABIgBAAIAAAAIgBAAIgBAAIAAAAIgBAAIgBAAIAAAAIgCAAIAAAAIAAABIgBAAIgBAAIgBAAIgBAAIAAAAIgBAAIAAABIAAAAIgBAAIgBAAIgBAAIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAAAIgCAAIAAAAIgBAAIgBAAIAAACIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIgBAAIAAABIgDAAIgBAAIgBAAIgDAAIAAAAIAAAAIgCAAIAAAAIgCAAIgBAAIgBAAIgBAAIgBAAIgBAAIAAABIgEAAIgBAAIgBAAIgBAAIgCAAIAAAAIgCAAIgBAAIAAABgAAHBKIgBAAIgCAAIAAgBIAAAAIAAgBIgBAAIAAAAIgCAAIAAgBIAAgBIAAAAIAAgBIgBAAIAAgBIAAAAIAAgBIAAgBIAAAAIAAAAIAAAAIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgCIgBAAIAAAAIgBAAIAAgBIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIgBAAIAAgBIAAAAIAAgBIAAAAIgBAAIAAgBIgCAAIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAgCIAAAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIgBAAIAAAAIAAgBIABAAIAAAAIAAAAIAAgBIAAgBIABAAIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIABAAIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAgBIAAAAIAAgBIACAAIAAgBIAAAAIABAAIAAgCIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAgBIAAAAIABAAIAAgBIAAAAIAAgBIAAAAIABAAIAAgBIAAAAIAAgBIAAgBIABAAIAAgBIABAAIAAgBIAAgBIABAAIAAAAIABAAIAAgBIAAgBIABAAIAAAAIAAgBIAAgIIAAgBIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgCAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAACIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgCAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAAAIgBAAIAAAAIAAABIgBAAIgCAAIAAABIAAAAIgBAAIgBAAIAAAAIgBAAIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAACIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIgBAAIAAAAIAAAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIgBAAIAAABIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIAAAAIAAABIgBAAIgBAAIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAAAIgBAAIgBAAIAAACIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIgcAAIAAAAIgBAAIAAAAIgBAAIAAgBIAAAAIgBAAIAAgBIgBAAIgBAAIAAgBIgBAAIgBAAIAAAAIgBAAIAAgCIAAAAIAAAAIgBAAIgBAAIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAAAIAAAAIAAgBIAAgBIgBAAIAAAAIAAgBIAAAAIAAgBIAAgBIgBAAIAAAAIAAgCIAAAAIgBAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIAAgBIgBAAIAAAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIAAAAIAAgBIAAgBIAAgBIAAAAIAAAAIAAgCIAAgBIAAAAIAAgCIAAgBIAAgBIAAAAIgBAAIAAgCIAAgBIAAgEIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIAAgFIAAgBIAAgBIAAgBIgBAAIAAgDIAAgCIAAAAIAAgCIAAgBIAAAAIAAgCIAAAAIAAgBIAAgBIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIAAgBIAAAAIAAgBIAAgBIAAgBIAAgBIAAgBIgBAAIAAAAIAAgBIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIAAAAIAAgBIgBAAIAAgCIAAAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIAAgBIgBAAIAAAAIAAgBIAAgBIAAgBIgBAAIAAgBIAAgBIAAgBIAAAAIAAAAIAAgBIAAAAIgBAAIAAgBIAAgBIAAgBIAAgBIgBAAIAAAAIAAgCIAAAAIgBAAIAAgBIAAgBIAAgBIAAAAIAAgBIAAAAIAAgBIgBAAIAAAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIAAgBIgBAAIAAAAIAAgCIgBAAIAAAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIgBAAIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAgCIAAAAIAAgBIAAAAIgCAAIAAgBIAAgBIgBAAIAAAAIAAgBIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIAAAAIgBAAIAAgBIAAgBIgBAAIAAgBIAAAAIAAAAIAAgCIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIgBAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIgBAAIAAgBIAAAAIAAAAIAAgBIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIgBAAIAAAAIAAgBIAAAAIAAgBIAAgBIgCAAIAAAAIAAgBIgBAAIAAgBIAAAAIAAAAIAAgBIgBAAIAAgBIAAgBIgBAAIAAgBIAAgBIABAAIABAAIAAgBIAAAAIAAAAIABAAIACAAIAAgBIAAAAIAAAAIABAAIABAAIAAgBIABAAIAAgBIAAAAIABAAIAAgBIAAAAIAAgBIABAAIABAAIAAgBIABAAIAAgBIABAAIABAAIAAAAIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIABAAIAAgBIABAAIAAAAIAAgBIABAAIAAAAIACAAIAAAAIAAgBIABAAIAAgCIABAAIABAAIAAAAIAAAAIAAgBIABAAIAAAAIAAgBIAcAAIABAAIAAABIAAAAIABAAIABAAIAAABIABAAIABAAIABAAIAAAAIABAAIAAAAIAAACIABAAIAAAAIABAAIAAABIABAAIABAAIABAAIAAAAIABAAIABAAIAAABIAAAAIABAAIABAAIAAABIAAAAIABAAIABAAIAAAAIABAAIAAAAIAAABIABAAIABAAIABAAIAAABIABAAIABAAIABAAIAAABIAAAAIABAAIAAAAIAAAAIABAAIABAAIAAABIABAAIABAAIABAAIAAABIABAAIAAAAIAAABIABAAIABAAIAAAAIAAABIABAAIABAAIABAAIAAABIAAAAIABAAIAAABIABAAIABAAIABAAIAAAAIABAAIABAAIAAABIAAAAIABAAIAAAAIAAAAIABAAIABAAIABAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIABAAIAAAAIAAABIABAAIABAAIAAABIAAAAIABAAIABAAIAAABIABAAIABAAIABAAIAAAAIABAAIAAAAIAAABIABAAIAAAAIABAAIAAABIABAAIABAAIABAAIAAAAIABAAIABAAIAAABIAAAAIABAAIABAAIAAABIAAAAIABAAIABAAIAAABIABAAIAAAAIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIABAAIABAAIAAgBIABAAIAAAAIABAAIAAAAIABAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAgBIABAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAAAIAAgBIABAAIAAAAIAAgBIABAAIABAAIABAAIAAgBIAAAAIACAAIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIABAAIAAAAIAAAAIABAAIABAAIAAgBIABAAIABAAIAAAAIABAAIAAAAIAAgBIABAAIABAAIAAgBIAAAAIABAAIABAAIAAgBIABAAIAAAAIAAgBIACAAIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAAAIAAAAIABAAIABAAIAAgBIABAAIAAAAIAAgBIABAAIABAAIAAgBIABAAIAAAAIABAAIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAAAIAAAAIABAAIAAgBIABAAIABAAIAAgCIAAAAIAAAAIACAAIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIABAAIAAAAIAAAAIAAgBIABAAIAAAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIAAAAIAAgBIAAAAIAAAAIABAAIAAgBIAAAAIAAgBIACAAIAAgBIABAAIAAAAIAAAAIAAgCIABAAIAAAAIABAAIAAgBIAAAAIAAgBIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIAAAAIABAAIAAgBIABAAIAAgBIAAgBIABAAIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAgBIABAAIAAAAIAAAAIAAgBIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAgCIAAAAIAAAAIAAgBIABAAIAAgBIAAgBIAAgBIABAAIAAAAIAAgBIAAAAIABAAIAAgBIAAgBIAAAAIAAgBIAAgBIAAgBIAAgBIACAAIAAAAIAAgBIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIAAAAIAAgCIABAAIAAAAIAAgBIAAgBIAAgBIAAgBIAAAAIAAgBIABAAIAAgBIAAgDIAAggIAAgBIAAAAIgBAAIAAgBIAAgBIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIgCAAIAAgBIAAgBIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIAAAAIgBAAIAAgBIAAgBIAAAAIAAgBIgBAAIAAgBIAAgBIAAgBIAAgBIgBAAIAAgBIAAAAIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIgBAAIAAgBIAAgBIAAAAIAAgBIAAAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgCIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAAAIACAAIAAgBIAAAAIAAgBIABAAIAAAAIABAAIAAgBIAbAAIAAABIABAAIAAAAIAAAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIAAAAIAAABIACAAIAAAAIAAAAIAAACIABAAIAAABIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAAAIABAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAABIABAAIAAABIAAAAIAAABIABAAIAAABIAAAAIAAAAIABAAIAAABIABAAIAAAAIABAAIAAABIABAAIAAABIAAAAIAAABIACAAIAAABIAAAAIAAABIABAAIAAABIABAAIAAAAIABAAIAAACIAAAAIAAABIgBAAIAAABIAAABIAAAAIAAABIAAABIgBAAIAAABIAAABIAAABIAAABIgBAAIAAAAIAAABIAAAAIAAABIAAABIAAAAIAAABIAAABIAAABIAAABIgCAAIAAAAIAAABIAAABIAAAAIAAABIAAAAIAAABIAAABIAAAAIAAABIgBAAIAAABIAAABIAAABIAAABIAAABIgBAAIAAAAIAAABIAAAAIAAABIgBAAIAAABIAAABIAAABIAAABIAAABIgBAAIAAAAIAAABIAAABIAAABIAAAAIAAAAIAAABIAAABIAAAAIAAABIgBAAIAAACIAAAAIAAABIAAABIAAAAIAAABIAAAAIAAABIAAABIgBAAIAAAAIAAABIAAABIAAABIAAABIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAAAIAAABIAAABIAAABIAAABIgBAAIAAAAIAAACIAAAAIAAABIgBAAIAAABIAAABIAAAAIAAABIAAABIgBAAIAAAAIAAABIAAABIAAABIAAAAIAAABIAAABIAAABIAAAAIAAABIgBAAIAAAAIAAABIAAABIAAABIgBAAIAAABIAAAAIAAACIAAAAIAAABIAAAAIAAABIAAABIAAABIAAAAIAAABIACAAIABAAIABAAIABAAIABAAIABAAIAAAAIABAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAAAIAAABIACAAIAAAAIABAAIABAAIACAAIABAAIAAABIAAAAIABAAIACAAIABAAIABAAIABAAIAAAAIAAABIABAAIAAAAIABAAIABAAIABAAIABAAIAAABIAAAAIACAAIAAAAIABAAIABAAIABAAIAAAAIAAABIABAAIABAAIAAAAIABAAIABAAIABAAIABAAIAAABIABAAIABAAIAAAAIABAAIAAAAIAAAAIABAAIABAAIABAAIABAAIAAAAIACAAIAAABIAAAAIABAAIABAAIABAAIAAAAIAAAAIABAAIABAAIAAAAIABAAIABAAIAAABIABAAIABAAIABAAIABAAIAAAAIAAABIABAAIABAAIAAAAIABAAIAAABIABAAIABAAIAAAAIACAAIAAABIAAAAIABAAIABAAIABAAIAAAAIAAAAIABAAIABAAIAAAAIAAACIABAAIABAAIABAAIAAAAIABAAIABAAIABAAIAAAAIAAABIABAAIAAAAIABAAIAAABIABAAIABAAIABAAIAAABIAAAAIACAAIAAAAIAAAAIABAAIABAAIAAABIABAAIAAAAIAAABIABAAIABAAIAAAAIAAAAIABAAIABAAIAAABIABAAIABAAIAAABIABAAIABAAIAAABIAAAAIAAABIABAAIAAAAIAAABIABAAIABAAIAAABIABAAIAAAAIABAAIAAAAIAAABIACAAIAAABIAAAAIAAAAIABAAIAAABIABAAIAAABIABAAIAAABIAAAAIAAAAIAAACIABAAIAAABIAAAAIABAAIAAABIAAABIAAAAIAAAAIAAABIAAACIAAABIAAAAIAAABIAAABIAAABIAAABIAAABIgBAAIAAAAIAAABIAAAAIgBAAIAAABIAAABIAAAAIAAABIAAABIgBAAIAAABIAAABIgBAAIAAAAIgBAAIAAABIAAABIAAAAIAAAAIgCAAIAAABIAAAAIAAABIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAAAIgCAAIAAABIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAACIgBAAIgBAAIAAAAIgBAAIgBAAIAAABIAAAAIAAABIgBAAIgBAAIAAABIAAAAIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIAAABIAAAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIgBAAIAAAAIAAABIgCAAIAAAAIAAABIgBAAIgBAAIgBAAIAAAAIgBAAIAAABIAAAEIAAABIAAACIAAAAIAAAAIAAACIAAACIAAABIAAABIAAAAIgBAAIAAABIAAAAIAAABIAAABIAAABIAAAAIAAABIAAAAIAAACIAAAAIAAABIgBAAIAAABIAAABIAAAAIgBAAIAAABIAAABIAAAAIgBAAIAAABIAAABIAAABIgBAAIAAABIAAABIAAABIgBAAIAAAAIAAABIgBAAIAAAAIAAABIAAAAIAAABIAAABIAAABIgBAAIAAAAIAAACIgBAAIAAAAIAAABIAAAAIAAABIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAAAIAAABIAAAAIAAABIAAABIgCAAIAAABIAAAAIAAABIAAABIgBAAIAAAAIgBAAIAAABIAAABIgBAAIAAAAIgBAAIAAABIAAABIAAAAIAAABIgBAAIAAAAIAAAAIAAACIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAABIgBAAIAAAAIAAAAIAAABIgCAAIAAAAIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgCAAIAAABIAAAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAABIAAAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIgBAAIgBAAIAAABIgBAAIAAAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgCAAIAAAAIAAAAIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIgBAAIAAABIgBAAIAAAAIAAABIAAAHIAAAHIAAAAIAAAEIgBAAIAAADIAAABIAAADIAAACIAAAAIAAACIgBAAIAAABIAAACIAAABIAAABIAAABIAAAAIAAABIgBAAIAAABIAAAAIAAABIAAABIAAABIAAABIAAABIgBAAIAAABIAAAAIAAABIAAAAIAAABIAAABIgBAAIAAABIAAABIAAAAIAAACIAAAAIgBAAIAAABIAAABIAAABIAAAAIAAAAIAAABIAAABIAAAAIAAABIgBAAIAAABIAAABIAAABIgBAAIAAABIAAABIAAAAIAAAAIAAABIAAABIAAAAIgBAAIAAABIAAABIgBAAIAAABIAAAAIgBAAIAAACIAAAAIAAAAIAAABIAAABIgCAAIAAABIAAABIAAAAIAAAAIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAABIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABgAg8h3IABAAIAABmIAIAAIACAAIAAgCIAAgBIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIAAAAIAAAAIAAgBIAAgBIAAgBIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAgBIAAgBIAAAAIAAgBIAAAAIABAAIAAgBIAAgBIABAAIAAAAIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIAAgBIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAAAIAAgBIABAAIAAgBIABAAIAAgBIAAgBIAAAAIAAgBIABAAIAAgBIAAAAIABAAIAAgBIAAAAIAAgBIABAAIAAgBIAAAAIABAAIAAgBIABAAIAAgBIAAAAIAAAAIAAgBIACAAIAAgCIABAAIAAAAIAAAAIAAgBIABAAIAAgBIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAgBIABAAIAAAAIABAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgCIAAAAIAAAAIACAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAAAIAAgBIAAAAIAAgBIABAAIAAgBIAAAAIAAAAIACAAIAAgCIABAAIAAAAIAAAAIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAguIAAAAIgBAAIgCAAIgCAAIAAAAIgBAAIAAAAIgBAAIgBAAIgBAAIgBAAIAAAAIgBAAIAAABIgCAAIAAAAIgBAAIAAAAIAAAAIAAAAIAAABIgBAAIgBAAIAAAAIgBAAIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIgCAAIAAAAIAAABIgBAAIgBAAIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIgBAAIgBAAIAAAAIgBAAIgBAAIgBAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIAAAAIgBAAIgBAAIAAAAIgBAAIAAABIgCAAIAAAAIgBAAIAAABIgBAAIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIAAABIgBAAIgBAAIgBAAIAAABIgBAAIgBAAIAAAAIAAABIgBAAIgBAAIAAAAIgBAAIAAAAIAAABIgBAAIgBAAIAAABIAAAAIgBAAIAAAAIgCAAIAAAAIAAABIgBAAIAAABIgBAAIAAABIgBAAIAAAAIAAAAIgBAAIAAABIAAAAIAAACIgBAAIAAAAIgBAAIAAABIAAABIgBAAIAAABIAAAAIgBAAIAAABIAAABIAAAAIAAABIgBAAgAAyiKIAAAbIAAAAIAAABIABAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIAAAAIAAgBIACAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAgBIABAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgCIABAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAAAIAAAAIAAgBIACAAIAAgBIAAAAIAAAAIABAAIAAgBIABAAIAAgBIABAAIAAgBIABAAIAAgBIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgBIgBAAIAAAAIAAAAIAAgBIgCAAIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAgBIgBAAIAAAAIAAAAIgBAAIgIAAIgBAAIAAAAIgBAAIAAABIgBAAIAAABIAAAAIAAABIgCAAIAAABIAAAAIAAABIgBAAIAAABIgBAAIAAAAIgBAAIAAABIgBAAIAAAAIAAAAIAAABIgBAAg");
	this.shape_107.setTransform(308.5,375.1);

	this.shape_108 = new cjs.Shape();
	this.shape_108.graphics.f("#9F9F9F").s().p("AAUE2IAAgBIAAAAIAAABgAgyE1IAAgBIABAAIAAABgAg6E0IAAgBIABAAIAAABgAg+EzIAAgBIABAAIAAABgAA7EyIAAAAIAAAAIAAAAgAhOEtIAAgBIABAAIAAABgABsEnIAAgBIACAAIAAABgABvEmIAAgBIABAAIAAABgABxElIAAgBIABAAIAAABgABzEkIAAgBIABAAIAAABgAhjEjIAAgBIABAAIAAABgACHEcIAAgBIABAAIAAABgACJEbIAAgBIABAAIAAABgACSEXIAAgBIABAAIAAABgACUEWIAAgBIABAAIAAABgACbESIAAgBIABAAIAAABgAiGEMIAAgBIABAAIAAABgAiKEIIAAgBIABAAIAAABgAiPEEIAAgBIABAAIAAABgACxEDIAAgBIABAAIAAABgAiVD+IAAgBIgBAAIAAAAIABAAIAAAAIABAAIAAABgAC7D6IAAgBIABAAIAAABgAC8D5IAAgBIABAAIAAABgAC8D5gAihDzIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAgAijDyIAAgBIABAAIAAABgAijDxIAAgBIAAAAIAAABgAikDwIAAAAIAAAAIAAgCIgBAAIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAIAAAAIAAACIABAAIAAAAgADIDtIAAgBIABAAIAAABgAinDtIAAgBIABAAIAAABgADJDsIAAgBIABAAIAAABgAioDsIAAgBIABAAIAAABgAg+DrIAAgBIAAAAIAAABgAhBDqIAAAAIAAAAIAAAAgAiqDqIAAAAIAAAAIAAgBIAAAAIAAABIABAAIAAAAgAhJDpIAAgBIABAAIAAABgADNDnIAAgBIABAAIAAABgAhQDlIAAgBIAAAAIAAABgABhDkIAAgBIABAAIAAABgAhSDkIAAgBIABAAIAAABgABnDjIAAAAIABAAIAAAAgAhcDfIAAgBIABAAIAAABgAi2DeIAAgCIAAAAIAAAAIAAAAIAAAAIABAAIAAACgABhDZIAAgBIAAAAIAAABgAhnDYIAAgBIABAAIAAABgAhpDXIAAgBIABAAIAAABgAi9DWIAAgBIABAAIAAABgABdDUIAAgBIABAAIAAABgAjBDQIAAgBIAAAAIAAABgAjnDQIAAgBIABAAIAAABgAhzDPIAAAAIABAAIAAAAgAjjDNIAAgBIABAAIAAABgAjHDKIAAAAIABAAIAAAAgAjgDKIAAgBIABAAIAAABgAh6DJIAAgBIABAAIAAABgAjbDGIAAAAIABAAIAAAAgABPDGIAAgBIgBAAIAAAAIABAAIAAAAIAAAAIAAABgAjKDGIAAgBIABAAIAAABgAh/DFIAAAAIAAAAIAAAAgAjYDEIAAgBIABAAIAAABgAjTDAIAAgBIAAAAIAAABgAiGC/IAAgBIAAAAIAAABgAkEC9IAAgBIABAAIAAABgABBC7IAAgBIABAAIAAABgAkDC4IAAgBIACAAIAAABgAkBC3IAAgBIAAAAIAAABgAkBC1IAAAAIABAAIAAAAgAiSCzIAAgBIgBAAIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAIAAAAIAAABgAAxCyIAAgBIABAAIAAABgAAwCxIAAgBIABAAIAAABgAiVCxIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgAAuCwIAAgBIABAAIAAABgAAsCvIAAgBIABAAIAAABgAiXCvIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgAiYCtIAAgBIgBAAIAAAAIABAAIAAAAIAAAAIAAABgAiaCsIAAgBIABAAIAAABgAC4CrIAAgBIABAAIAAABgAAgCqIAAgBIABAAIAAABgACoCpIAAgBIABAAIAAABgAAVClIAAAAIABAAIAAAAgACMCkIAAgBIABAAIAAABgAANCjIAAgBIABAAIAAABgAB+CgIAAAAIAAAAIAAAAgAAACgIAAAAIACAAIAAAAgAB7CgIAAgBIAAAAIAAABgAjyCgIAAgBIABAAIAAABgAB5CfIAAgBIAAAAIAAABgAB2CeIAAgBIABAAIAAABgABzCdIAAgBIABAAIAAABgAioCdIAAgBIABAAIAAABgABxCcIAAgBIABAAIAAABgAipCcIAAgBIABAAIAAABgAgXCbIAAgBIABAAIAAABgAiqCbIAAgBIABAAIAAABgABhCWIAAgBIABAAIAAABgAinCTIAAAAIABAAIAAAAgAjiCQIAAgBIABAAIAAABgAijCOIAAAAIABAAIAAAAgAjbCKIAAgBIABAAIAAABgAidCIIAAAAIAAAAIAAAAgAiZCDIAAgBIABAAIAAABgAA9CAIAAgBIABAAIAAABgAiUB+IAAgBIABAAIAAABgAiQB4IAAgBIABAAIAAABgAA8B2IAAgBIABAAIAAABgAiLBzIAAgBIABAAIAAABgAiHBuIAAgBIABAAIAAABgADjBpIAAgCIABAAIAAACgAiCBpIAAgCIABAAIAAACgAipBmIAAgBIABAAIAAABgADyBjIAAAAIABAAIAAAAgAh+BjIAAgBIABAAIAAABgAifBhIAAgBIABAAIAAABgAh5BdIAAgBIABAAIAAABgAiaBdIAAgBIABAAIAAABgAD/BcIAAAAIABAAIAAAAgAEABcIAAgBIABAAIAAABgABhBcIAAgBIAAAAIAAABgAEBBbIAAAAIAAAAIAAAAgAEBBbIAAgBIABAAIAAABgABnBaIAAgBIAAAAIAAABgAiSBaIAAgBIAAAAIAAABgAANBXIAAAAIABAAIAAAAgAA4BVIAAAAIABAAIAAAAgAByBVIAAgBIABAAIAAABgACQBSIAAAAIACAAIAAAAgAB5BSIAAAAIAAAAIAAAAgAgfBSIAAAAIABAAIAAAAgACOBSIAAgBIABAAIAAABgAgnBSIAAgBIABAAIAAABgACMBRIAAgBIAAAAIAAABgABHBRIAAgBIABAAIAAABgAguBRIAAgBIAAAAIAAABgACKBQIAAAAIABAAIAAAAgABKBQIAAAAIAAAAIAAAAgACHBQIAAgBIABAAIAAABgACFBPIAAgBIAAAAIAAABgABWBMIAAgBIABAAIAAABgABYBLIAAgBIABAAIAAABgABlBIIAAgBIABAAIAAABgABnBHIAAgBIABAAIAAABgAAHBFIAAgCIABAAIAAACgAB2BCIAAgBIABAAIAAABgAANBBIAAgBIABAAIAAABgAAOBAIAAgBIABAAIAAAAIAAAAIAAgBIABAAIAAABIgBAAIAAAAIAAAAIAAABgAAOBAgACFA9IAAAAIAAAAIAAAAgAEEA7IAAgBIABAAIAAABgACUA4IAAgBIABAAIAAABgACWA3IAAAAIABAAIAAAAgACiA0IAAgBIACAAIAAABgAClAzIAAAAIABAAIAAAAgAAXAwIAAgBIABAAIAAABgACxAvIAAgBIABAAIAAABgACzAuIAAAAIABAAIAAAAgAAZArIAAgBIAAAAIAAABgADCAqIAAgBIABAAIAAABgADqAnIAAgBIABAAIAAABgAAZAnIAAgBIABAAIAAABgAhsAlIAAAAIABAAIAAAAgADnAlIAAgBIABAAIAAABgADSAlIAAgBIAAAAIAAABgAhiAiIAAgBIAAAAIAAABgADgAgIAAgBIACAAIAAABgAhZAeIAAgBIABAAIAAABgAigAeIAAgBIABAAIAAABgAAcAaIAAgBIABAAIAAABgAhQAaIAAgBIABAAIAAABgAijAZIAAAAIAAAAIAAAAgAhGAWIAAgBIABAAIAAABgAg9ASIAAAAIABAAIAAAAgAg4ARIAAgBIAAAAIAAABgAg0AOIAAgBIACAAIAAABgAioAOIAAgBIABAAIAAABgAgvAMIAAAAIABAAIAAAAgAgqAKIAAgBIABAAIAAABgAAeAJIAAAAIABAAIAAAAgAgmAJIAAgBIABAAIAAABgAggAHIAAgBIABAAIAAABgAgdAEIAAAAIABAAIAAAAgAgXACIAAgBIABAAIAAABgAgTABIAAgBIAAAAIAAABgAgNAAIAAAAIAAAAIAAAAgAgKgBIAAgBIABAAIAAABgAgxgfIAAgBIAAAAIAAABgAgwgjIAAgBIABAAIAAABgAA+gkIAAgBIABAAIAAABgAikgwIAAAAIAAAAIAAAAgAilgzIAAgBIABAAIAAABgABPg1IAAgBIABAAIAAABgAgkg5IAAgBIABAAIAAABgAggg9IAAAAIABAAIAAAAgAiphAIAAgCIABAAIAAACgAgdhCIAAgBIABAAIAAABgABahFIAAgBIACAAIAAABgABehKIAAgBIABAAIAAABgABfhMIAAgBIAAAAIAAABgAishMIAAgBIABAAIAAABgAithOIAAgBIABAAIAAABgABghPIAAAAIABAAIAAAAgABhhQIAAgBIAAAAIAAABgAithQIAAgBIAAAAIAAABgAiuhTIAAgBIABAAIAAABgABihVIAAAAIABAAIAAAAgAivhVIAAgBIABAAIAAABgAivhXIAAgBIAAAAIAAABgAgGhYIAAgBIABAAIAAABgAgFhZIAAgBIABAAIAAABgAAAhgIAAAAIAAAAIAAAAgAi1hkIAAgBIABAAIAAABgAAKhoIAAgBIABAAIAAABgAi3hsIAAAAIABAAIAAAAgAi5hwIAAgBIABAAIAAABgABsh0IAAgBIAAAAIAAABgABuh1IAAgBIAAAAIAAABgAi8h1IAAgBIABAAIAAABgABvh2IAAgBIABAAIAAABgABxh3IAAgBIAAAAIAAABgAB3h6IAAgBIABAAIAAABgAi+h7IAAgBIABAAIAAABgAi+h8IAAgBIAAAAIAAABgACBh/IAAgCIABAAIAAACgAA/iBIAAgBIABAAIAAABgAg7iBIAAgBIABAAIAAABgABAiCIAAgBIABAAIAAABgABAiCgABBiDIAAgBIABAAIAAABgABBiDgABCiEIAAAAIABAAIAAAAgABCiEgACJiEIAAgBIABAAIAAABgABDiEIAAgBIAAAAIAAABgABDiFIAAgBIABAAIAAABgABDiFgAjCiFIAAgBIABAAIAAABgABEiGIAAAAIAAAAIAAAAgABEiGgABEiGIAAgBIABAAIAAABgABEiGgAjDiGIAAgBIABAAIAAABgABFiHIAAgBIABAAIAAABgABGiIIAAgBIABAAIAAgBIABAAIAAABIgBAAIAAABgABGiIgABIiKIAAgBIAAAAIAAABgABIiLIAAgBIACAAIAAAAIAAAAIAAAAIAAAAIAAABgABIiLgABKiMIAAgBIABAAIAAABgAgsiMIAAgBIABAAIAAABgABLiNIAAAAIABAAIAAAAgABLiNgABMiNIAAgBIABAAIAAABgABMiNgABNiOIAAgBIABAAIAAABgABNiOgAgliOIAAgBIAAAAIAAABgAAxiPIAAgBIABAAIAAABgAAyiQIAAgBIAAAAIAAABgAAyiQgAAyiRIAAAAIABAAIAAAAgAAyiRgACbiRIAAgCIABAAIAAACgAAziRIAAgCIABAAIAAAAIABAAIAAgBIABAAIAAABIgBAAIAAAAIgBAAIAAACgAAziRgAgKiVIAAgBIAAAAIAAABgACfiXIAAAAIABAAIAAAAgAgBiXIAAAAIABAAIAAAAgACgiXIAAgBIABAAIAAABgAAMiYIAAAAIABAAIAAAAgAhRiYIAAgBIABAAIAAABgAhGiaIAAgBIABAAIAAABgAClieIAAAAIABAAIAAAAgAg5ieIAAgBIABAAIAAABgAhiifIAAgBIABAAIAAABgAjQigIAAAAIABAAIAAAAgAg0igIAAgBIACAAIAAABgAhnihIAAgBIAAAAIAAABgAgtijIAAAAIABAAIAAAAgAhuijIAAgCIABAAIAAACgAh0imIAAgBIAAAAIAAABgAh6ipIAAAAIABAAIAAAAgAiLivIAAgBIABAAIAAABgAiRixIAAgBIAAAAIAAABgAiYizIAAgBIABAAIAAABgACli0IAAgBIgBAAIAAAAIABAAIAAAAIABAAIAAABgACki1IAAgCIAAAAIAAACgACgi4IAAgBIABAAIAAABgAgIi4IAAgBIABAAIAAABgACbi7IAAgBIABAAIAAABgAgDi8IAAgBIABAAIAAABgACXi9IAAAAIABAAIAAAAgAgBi9IAAgBIAAAAIAAABgACQjAIAAgBIAAAAIAAABgAAAjCIAAgBIACAAIAAABgACJjDIAAAAIABAAIAAAAgAACjDIAAAAIABAAIAAAAgAACjDgACEjEIAAAAIAAAAIAAAAgACAjEIAAgBIABAAIAAABgAB9jFIAAgBIABAAIAAABgAAFjHIAAgBIABAAIAAABgAAHjKIAAgBIABAAIAAABgAAPjYIAAgBIABAAIAAABgAA9jgIAAgBIABAAIAAABgAAUjlIAAgBIAAAAIAAABgAA/jnIAAgBIABAAIAAABgABBjuIAAgBIABAAIAAABgABDj2IAAAAIAAAAIAAAAgABEj8IAAgBIAAAAIAAABgABFkDIAAgBIABAAIAAABgABHkLIAAAAIABAAIAAAAgABIkSIAAgBIACAAIAAABgABMklIAAAAIgBAAIAAgBIABAAIAAABIABAAIAAAAgAAQklIAAAAIABAAIAAAAgABKkmIAAgBIABAAIAAABgABKknIAAAAIAAAAIAAAAgABIknIAAgBIAAAAIAAgBIgBAAIAAgBIgBAAIAAAAIgBAAIAAgCIgBAAIAAAAIABAAIAAAAIABAAIAAACIABAAIAAAAIABAAIAAABIAAAAIAAABIACAAIAAABgAAPknIAAgBIABAAIAAABgAAPkqIAAgCIAAAAIAAACgABEksIAAgBIgBAAIAAgBIAAAAIAAgBIgBAAIAAgBIABAAIAAABIAAAAIAAABIABAAIAAABIAAAAIAAABgAAOkuIAAgBIABAAIAAABgABBkwIAAAAIABAAIAAAAgABAkwIAAgBIgBAAIAAAAIABAAIAAAAIABAAIAAABgAANkxIAAAAIABAAIAAAAgAANk0IAAgBIAAAAIAAABg");
	this.shape_108.setTransform(308.6,375.7);

	this.shape_109 = new cjs.Shape();
	this.shape_109.graphics.f("#8C8C8C").s().p("AAaEqIAAAAIACAAIAAAAgAgkEqIAAAAIABAAIAAAAgAAmEqIAAgBIABAAIAAABgAgpEqIAAgBIAAAAIAAABgAAuEpIAAgBIADAAIAAABgAA3EoIAAgBIABAAIAAABgABHEmIAAgBIABAAIAAABgAg6EmIAAgBIABAAIAAABgABMElIAAgBIABAAIAAABgABQEkIAAgBIABAAIAAABgABUEjIAAAAIABAAIAAAAgABYEjIAAgBIABAAIAAABgABeEhIAAgBIABAAIAAABgABkEfIAAAAIABAAIAAAAgABvEcIAAgBIABAAIAAABgAhZEYIAAAAIABAAIAAAAgACAEXIAAgBIABAAIAAABgAhfEVIAAgBIABAAIAAABgACKETIAAgBIABAAIAAABgAhjETIAAgBIABAAIAAABgACUEOIAAgBIABAAIAAABgACWENIAAAAIAAAAIAAAAgACXENIAAgBIABAAIAAABgACZEMIAAgBIABAAIAAABgAhxEJIAAAAIABAAIAAAAgAChEHIAAgBIABAAIAAABgAClEFIAAgBIABAAIAAABgAh+D/IAAgBIABAAIAAABgAC3D5IAAgBIAAAAIAAABgAiHD3IAAAAIABAAIAAAAgAC+DzIAAgBIABAAIAAABgADQDiIAAgBIAAAAIAAABgAAzDhIAAgBIABAAIAAABgAgyDhIAAgBIABAAIAAABgAA5DgIAAgBIAAAAIAAABgAA+DfIAAgBIABAAIAAABgABEDeIAAgBIAAAAIAAABgABJDdIAAAAIABAAIAAAAgABPDdIAAgBIAAAAIAAABgABVDcIAAgBIABAAIAAABgABaDbIAAgBIABAAIAAABgABgDaIAAgBIABAAIAAABgAhGDaIAAgBIAAAAIAAABgABlDZIAAAAIABAAIAAAAgABrDZIAAgBIAAAAIAAABgABwDYIAAgBIABAAIAAABgADZDXIAAgBIABAAIAAABgAkADXIAAgWIABAAIAAAWgABwDWIAAAAIABAAIAAAAgAhODWIAAAAIABAAIAAAAgAhQDWIAAgBIABAAIAAABgAitDSIAAAAIABAAIAAAAgABsDRIAAgBIABAAIAAABgAhYDRIAAgBIABAAIAAABgAi0DLIAAgBIABAAIAAABgAhqDEIAAAAIABAAIAAAAgAi9DAIAAgBIABAAIAAABgAhxC+IAAgBIAAAAIAAABgAj9C1IAAAAIAAAAIAAAAgAh8C1IAAgBIgBAAIAAgBIABAAIAAABIAAAAIAAABgABECsIAAgBIABAAIAAABgABCCqIAAAAIABAAIAAAAgAiICqIAAgBIABAAIAAABgAiKCoIAAgBIABAAIAAABgAA9CnIAAgBIAAAAIAAABgAj2CmIAAgBIAAAAIAAABgADICfIAAAAIAFAAIAAAAgAiSCfIAAAAIABAAIAAAAgAC/CfIAAgBIABAAIAAABgAC3CeIAAgBIABAAIAAABgACvCdIAAgBIABAAIAAABgACqCcIAAgBIABAAIAAABgAAkCcIAAgBIABAAIAAABgAClCbIAAAAIAAAAIAAAAgACcCaIAAgBIABAAIAAABgACYCZIAAgBIABAAIAAABgAjtCYIAAAAIABAAIAAAAgAAKCUIAAAAIABAAIAAAAgAAACSIAAgBIAAAAIAAABgAgECRIAAgBIABAAIAAABgAgJCQIAAAAIABAAIAAAAgAgOCQIAAgBIABAAIAAABgAgVCPIAAgBIABAAIAAABgAiiCPIAAgBIABAAIAAABgAgeCOIAAgBIACAAIAAABgAgtCNIAAAAIAFAAIAAAAgAibCDIAAgBIABAAIAAABgABaCCIAAAAIABAAIAAAAgABWCAIAAgBIABAAIAAABgAjUB/IAAgBIABAAIAAABgAiWB+IAAgBIABAAIAAABgAjPB7IAAAAIABAAIAAAAgAhHB5IAAgBIABAAIAAABgAiRB4IAAgBIAAAAIAAABgAjLB4IAAgBIAAAAIAAABgABKB3IAAAAIABAAIAAAAgAhIB3IAAAAIABAAIAAAAgAhJB2IAAgBIABAAIAAABgAhKB0IAAgBIABAAIAAABgAhKBzIAAgBIAAAAIAAABgAhLBxIAAgBIABAAIAAABgAhMBwIAAgBIABAAIAAABgAhNBuIAAgBIABAAIAAABgAhNBsIAAAAIAAAAIAAAAgAhOBrIAAgBIABAAIAAABgAhPBpIAAgBIABAAIAAABgADiBeIAAgBIAAAAIAAABgADmBdIAAAAIABAAIAAAAgADqBdIAAgBIABAAIAAABgAijBcIAAgBIABAAIAAABgADzBaIAAAAIABAAIAAAAgAifBaIAAgBIABAAIAAABgAibBXIAAgBIABAAIAAABgAiVBUIAAgBIAAAAIAAABgAiQBRIAAgBIABAAIAAABgABrBPIAAAAIAAAAIAAAAgABwBNIAAgBIABAAIAAABgAAcBMIAAgBIAdAAIAAABgAhtBMIgZAAIAAgBIAaAAIAAABgAAVBLIAAAAIAAAAIAAAAgAANBLIAAgBIABAAIAAABgAB4BKIAAgBIABAAIAAABgAAFBKIAAgBIACAAIAAABgAgBBJIAAgBIABAAIAAABgAgJBIIAAgBIACAAIAAABgAgQBHIAAAAIABAAIAAAAgAgYBHIAAgBIABAAIAAABgAggBGIAAgBIABAAIAAABgAgnBFIAAgBIABAAIAAABgAgvBEIAAAAIABAAIAAAAgAg3BEIAAgBIACAAIAAABgABZBCIAAgBIABAAIAAABgAATA3IAAgBIABAAIAAABgACmAqIAAgBIABAAIAAABgAEAAmIAAgBIABAAIAAABgAgCAlIAAgBIAAAAIAAABgAD9AjIAAAAIABAAIAAAAgAgFAiIAAgBIABAAIAAABgAgHAfIAAgBIABAAIAAABgAiKAcIAAAAIAAAAIAAAAgAgJAcIAAgBIAAAAIAAABgAhnAcIAAgBIABAAIAAABgAiMAcIAAgBIABAAIAAABgAgMAYIAAAAIABAAIAAAAgAheAYIAAgBIABAAIAAABgAgOAVIAAgBIABAAIAAABgAhUAUIAAgBIAAAAIAAABgAAkATIAAgBIAAAAIAAABgAiXATIAAgBIABAAIAAABgAhLAQIAAgBIABAAIAAABgAiZAQIAAgBIABAAIAAABgAAkANIAAAAIABAAIAAAAgAhCAMIAAgBIABAAIAAABgAicAMIAAgBIABAAIAAABgAidAJIAAgBIABAAIAAABgAg4AIIAAgBIAAAAIAAABgAAlAHIAAgBIABAAIAAABgAgvAEIAAgBIABAAIAAABgAgmAAIAAAAIABAAIAAAAgAigAAIAAAAIAAAAIAAAAgAAmgBIAAgDIABAAIAAADgAgcgCIAAgBIABAAIAAABgAgTgGIAAgBIABAAIAAABgAiigHIAAAAIABAAIAAAAgAgJgKIAAgBIAAAAIAAABgAAAgOIAAAAIAAAAIAAAAgAijgOIAAgBIABAAIAAABgAA1giIAAgBIABAAIAAABgAgqgnIAAgBIABAAIAAABgAiagoIAAgCIABAAIAAACgABCgsIAAgBIABAAIAAABgAgngxIAAgBIABAAIAAABgAibgxIAAgCIABAAIAAACgAicg3IAAgBIABAAIAAABgAgfg/IAAgBIAAAAIAAABgAidhAIAAgBIABAAIAAABgAgThPIAAgBIABAAIAAABgAijhVIAAgBIABAAIAAABgAiohmIAAAAIABAAIAAAAgAANhvIAAgBIABAAIAAABgABvhxIAAAAIABAAIAAAAgAithyIAAgBIABAAIAAABgAAShzIAAgBIAAAAIAAABgABwh2IAAgCIABAAIAAACgAiuh2IAAgBIAAAAIAAABgAiyiAIAAAAIABAAIAAAAgAB8iEIAAgBIABAAIAAABgAi1iFIAAgBIABAAIAAABgACIiKIAAgBIAAAAIAAABgACMiNIAAgBIAAAAIAAABgAi5iOIAAAAIABAAIAAAAgAi5iPIAAgBIAAAAIAAABgAgtiTIAAgBIABAAIAAABgAgniWIAAgBIABAAIAAABgACbiXIAAgBIABAAIAAABgAgVidIAAAAIABAAIAAAAgACkieIAAgBIABAAIAAABgAClifIAAgBIAAAAIAAABgAgLifIAAgBIABAAIAAABgAgHigIAAAAIABAAIAAAAgAgDigIAAgBIABAAIAAABgAAAihIAAgBIABAAIAAABgAAFiiIAAgBIABAAIAAABgAALijIAAgBIAAAAIAAABgAARikIAAAAIABAAIAAAAgAhGikIAAAAIAAAAIAAAAgAhDikIAAgBIABAAIAAABgAg4ioIAAAAIAAAAIAAAAgACviuIAAgBIABAAIAAABgAhiiuIAAgBIABAAIAAABgAjGivIAAAAIABAAIAAAAgAhoiwIAAgBIABAAIAAABgAjDiwIAAgBIAAAAIAAABgAjBiyIAAgBIABAAIAAABgACxizIAAgBIABAAIAAABgAi/izIAAgBIABAAIAAABgAgXi1IAAgBIABAAIAAABgAi8i1IAAgBIAAAAIAAABgAgVi2IAAAAIABAAIAAAAgAi6i2IAAgBIABAAIAAABgAi4i4IAAgBIABAAIAAABgAgOi6IAAAAIABAAIAAAAgAi1i6IAAAAIAAAAIAAAAgAizi7IAAgBIABAAIAAABgAgKi8IAAgBIABAAIAAABgACvi9IAAgBIABAAIAAABgAixi9IAAgBIABAAIAAABgAiLi+IAAAAIABAAIAAAAgAiui+IAAgBIAAAAIAAABgAChjIIAAgBIABAAIAAABgACcjKIAAgBIABAAIAAABgACTjNIAAgBIABAAIAAABgAALjPIAAgBIAAAAIAAABgAB9jTIAAgBIABAAIAAABgAAOjUIAAAAIABAAIAAAAgAB2jUIAAgBIAAAAIAAABgAByjVIAAgBIAAAAIAAABgABtjWIAAgBIABAAIAAABgABojXIAAAAIABAAIAAAAgABjjXIAAgBIABAAIAAABgABejYIAAgBIABAAIAAABgABXjZIAAgBIABAAIAAABgABSjaIAAgBIAAAAIAAABgABLjbIAAAAIABAAIAAAAgABDjbIAAgBIABAAIABAAIAAABgAAZjmIAAAAIAAAAIAAAAgABHjwIAAgBIAAAAIAAABgABIj3IAAgBIABAAIAAABgAAdj7IAAgCIABAAIAAACgABKj+IAAgBIABAAIAAABgABLkFIAAgBIABAAIAAABgABNkMIAAgBIABAAIAAABgABPkTIAAgBIAAAAIAAABgABQkaIAAgBIABAAIAAABgABSkhIAAgBIAAAAIAAABgABTkoIAAgBIABAAIAAABg");
	this.shape_109.setTransform(307.7,376.8);

	this.shape_110 = new cjs.Shape();
	this.shape_110.graphics.f("#959595").s().p("AgsEtIAAgBIABAAIAAABgAg2ErIAAAAIABAAIAAAAgAhGEmIAAgBIABAAIAAABgAhSEiIAAgBIAAAAIAAABgABgEhIAAgBIABAAIAAABgABmEfIAAgBIABAAIAAABgAB7EYIAAAAIABAAIAAAAgAB/EWIAAAAIAAAAIAAAAgACMERIAAAAIAAAAIAAAAgAClEDIAAgBIAAAAIAAABgACnEBIAAgBIABAAIAAABgACsD+IAAgBIABAAIAAABgAiWD0IAAgCIABAAIAAACgAg4DjIAAgBIABAAIAAABgABNDeIAAgBIABAAIAAABgABSDdIAAgBIABAAIAAABgABXDcIAAAAIABAAIAAAAgABdDcIAAgBIABAAIAAABgAj9DaIAAgBIAAAAIAAABgAj6DWIAAgBIABAAIAAABgAj1DTIAAAAIAAAAIAAAAgAhhDSIAAgBIAAAAIAAABgAjyDQIAAgBIABAAIAAABgAjuDNIAAgBIABAAIAAABgABfDLIAAgBIABAAIAAABgAjqDKIAAgBIABAAIAAABgAhtDJIAAgBIAAAAIAAABgAh1DDIAAgBIABAAIAAABgAkGC+IAAgBIAAAAIAAABgABLC4IAAgBIAAAAIAAABgABECzIAAgBIABAAIAAABgAAzCoIAAAAIABAAIAAAAgAAsClIAAgBIAAAAIAAABgAj7CkIAAgBIAAAAIAAABgAAlChIAAAAIAAAAIAAAAgAj3CeIAAgBIABAAIAAABgAAaCdIAAAAIABAAIAAAAgACZCdIAAgBIABAAIAAABgAAYCdIAAgBIABAAIAAABgAATCcIAAgBIACAAIAAABgAAICYIAAgBIABAAIAAABgACBCXIAAgBIABAAIAAABgAAGCXIAAgBIAAAAIAAABgAAACWIAAgBIAAAAIAAABgAgDCVIAAgBIABAAIAAABgAgRCTIAAgBIABAAIAAABgABwCSIAAgBIABAAIAAABgAgdCRIAAAAIABAAIAAAAgAgmCRIAAgCIABAAIAAACgABpCPIAAAAIABAAIAAAAgABkCNIAAgBIABAAIAAABgABVCFIAAgBIABAAIAAABgABIB9IAAAAIABAAIAAAAgABFB8IAAgBIABAAIAAABgAjPB4IAAgBIABAAIAAABgAiUB1IAAgBIABAAIAAABgAjMB1IAAgBIACAAIAAABgAjIByIAAAAIABAAIAAAAgAiQBwIAAgBIABAAIAAABgAjDBvIAAgBIABAAIAAABgAiKBqIAAgBIAAAAIAAABgAi4BnIAAAAIAAAAIAAAAgABGBiIAAgBIABAAIAAABgADbBgIAAgBIABAAIAAABgAiuBgIAAgBIABAAIAAABgABJBfIAAAAIACAAIAAAAgADnBeIAAAAIAAAAIAAAAgABPBdIAAgBIAAAAIAAABgABSBaIAAgBIABAAIAAABgACuBTIAAgBIABAAIAAABgACrBSIAAgBIABAAIAAABgACpBRIAAgBIABAAIAAABgACnBQIAAgBIABAAIAAABgAClBPIAAgBIAAAAIAAABgAiQBPIAAgBIAAAAIAAABgACiBOIAAgBIABAAIAAABgAiPBOIAAgBIABAAIAAABgACfBNIAAAAIABAAIAAAAgABtBNIAAAAIAAAAIAAAAgAA0BNIAAAAIABAAIAAAAgACeBNIAAgBIAAAAIAAABgAA3BNIAAgBIAAAAIAAABgAAGBNIAAgBIABAAIAAABgACbBMIAAAAIABAAIAAAAgACYBMIAAgBIABAAIAAABgACWBLIAAgBIABAAIAAABgAEGBKIAAgBIABAAIAAABgACUBKIAAgBIABAAIAAABgAB3BKIAAgBIABAAIAAABgABEBJIAAgBIAAAAIAAABgABFBIIAAgBIABAAIAAABgAg2BGIAAAAIABAAIAAAAgABVBDIAAAAIABAAIAAAAgABjA+IAAAAIABAAIAAAAgAByA6IAAgBIABAAIAAABgAB0A5IAAAAIABAAIAAAAgACBA1IAAgBIABAAIAAABgACEA0IAAgBIAAAAIAAABgAATAyIAAAAIAAAAIAAAAgACQAxIAAgBIABAAIAAABgACTAwIAAgBIAAAAIAAABgAECAvIAAgBIABAAIAAABgAD/AsIAAAAIABAAIAAAAgAChArIAAgBIABAAIAAABgAD8AqIAAgBIAAAAIAAABgACwAnIAAgBIABAAIAAABgAC/AhIAAAAIABAAIAAAAgADBAhIAAgBIABAAIAAABgADOAdIAAgBIAAAAIAAABgAiVAdIAAgBIABAAIAAABgADQAcIAAAAIABAAIAAAAgAAbAaIAAgBIABAAIAAABgAiaAaIAAgBIABAAIAAABgADlAZIAAgBIABAAIAAABgADdAYIAAgBIABAAIAAABgADfAXIAAgBIAAAAIAAABgAgVAVIAAAAIABAAIAAAAgAgUATIAAgBIABAAIAAABgAgSAQIAAgBIAAAAIAAABgAgRAOIAAgBIABAAIAAABgAgPAMIAAgBIAAAAIAAABgAgNAKIAAgCIABAAIAAACgAimAHIAAgBIABAAIAAABgAipAAIAAgBIABAAIAAABgAiqgLIAAgBIABAAIAAABgAAlgbIAAAAIACAAIAAAAgAAogcIAAgBIABAAIAAABgAAwghIAAgBIABAAIAAABgAiiglIAAgBIABAAIAAABgAA3gnIAAAAIAAAAIAAAAgAgwgrIAAgBIABAAIAAABgABAgvIAAgBIABAAIAAABgAijg0IAAgBIABAAIAAABgAgrg3IAAgBIABAAIAAABgAgog6IAAgBIABAAIAAABgABQg/IAAAAIAAAAIAAAAgAilhBIAAgBIABAAIAAABgABUhDIAAgBIABAAIAAABgABYhKIAAAAIABAAIAAAAgABahMIAAAAIABAAIAAAAgAgbhMIAAAAIABAAIAAAAgAiphNIAAgBIABAAIAAABgABghXIAAAAIABAAIAAAAgABnhnIAAgBIABAAIAAABgAAAhoIAAgBIABAAIAAABgAiyhpIAAgCIABAAIAAACgABohrIAAAAIAAAAIAAAAgABziBIAAgBIABAAIAAABgAB2iCIAAgBIABAAIAAABgAg7iJIAAgBIABAAIAAABgAg3iNIAAgCIAAAAIAAACgACMiQIAAAAIABAAIAAAAgACOiRIAAgBIABAAIAAABgACXiXIAAgBIABAAIAAABgAgmiXIAAgBIAAAAIAAABgAgiiZIAAgBIABAAIAAABgAgZibIAAgBIAAAAIAAABgAAEihIAAgBIABAAIAAABgAClilIAAAAIAAAAIAAAAgAhWilIAAAAIABAAIAAAAgAg9imIAAgBIAAAAIAAABgAg7inIAAAAIABAAIAAAAgAhcinIAAAAIABAAIAAAAgAg2ioIAAgBIAAAAIAAABgAhjipIAAgBIABAAIAAABgAgwirIAAgBIABAAIAAABgAgmiuIAAgBIAAAAIAAABgAh/i0IAAgBIAAAAIAAABgAiGi3IAAAAIABAAIAAAAgAiMi5IAAAAIABAAIAAAAgAgPi8IAAAAIAAAAIAAAAgAgMi9IAAgBIAAAAIAAABgACejEIAAgBIAAAAIAAABgACOjKIAAgBIABAAIAAABgAB3jRIAAgBIABAAIAAABgABzjSIAAAAIABAAIAAAAgABnjTIAAgBIABAAIAAABgABQjXIAAgBIABAAIAAABgAAMjZIAAAAIABAAIAAAAgAA8jgIAAAAIABAAIAAAAgAASjlIAAgBIABAAIAAABgAA+jmIAAgBIAAAAIAAABgAATjoIAAgBIAAAAIAAABgAAVjyIAAgBIABAAIAAABgAAWkbIAAgBIABAAIAAABgAAVkeIAAgBIABAAIAAABgAAVkiIAAAAIAAAAIAAAAgABLkiIAAgBIABAAIAAABgAATkkIAAgBIACAAIAAABgAATkoIAAgBIAAAAIAAABgABNkpIAAgBIABAAIAAABgAASkrIAAgBIABAAIAAABg");
	this.shape_110.setTransform(308.5,376.6);

	this.shape_111 = new cjs.Shape();
	this.shape_111.graphics.f("#D8D8D8").s().p("AAKErIAAAAIAEAAIAAAAgAgpErIAAAAIADAAIAAAAgAAYErIAAgBIABAAIAAABgAguErIAAgBIABAAIAAABgAAkEqIAAgBIABAAIAAABgAArEpIAAAAIACAAIAAAAgAAzEpIAAgBIABAAIAAABgAA5EoIAAgCIAAAAIAAACgABDEmIAAgBIABAAIAAABgABIElIAAgBIAAAAIAAABgABLEkIAAgBIABAAIAAABgABSEjIAAgBIABAAIAAABgAhTEgIAAgBIABAAIAAABgABsEbIAAAAIABAAIAAAAgACEETIAAgBIABAAIAAABgAhtERIAAAAIABAAIAAAAgACMEPIAAAAIABAAIAAAAgACOEPIAAgBIABAAIAAABgACPEOIAAgBIABAAIAAABgACRENIAAgBIABAAIAAABgAh0ENIAAgBIABAAIAAABgACTEMIAAgBIAAAAIAAABgAh5EJIAAgBIABAAIAAABgACeEGIAAgBIAAAAIAAABgAChEEIAAgCIABAAIAAACgAiDECIAAgBIABAAIAAABgACuD7IAAgBIABAAIAAABgAiRD1IAAAAIAAAAIAAAAgAg2DhIAAgBIBhAAIAAABgAAvDgIAAgBIABAAIAAABgAA1DfIAAgBIABAAIAAABgAA6DeIAAgBIABAAIAAABgABADdIAAAAIAAAAIAAAAgABFDdIAAgBIABAAIAAABgABLDcIAAgBIAAAAIAAABgABQDbIAAgBIABAAIAAABgABWDaIAAAAIAAAAIAAAAgAhNDaIAAAAIABAAIAAAAgADQDaIAAgBIABAAIAAABgABbDaIAAgBIABAAIAAABgABhDZIAAgBIAAAAIAAABgAiuDZIAAgBIAAAAIAAABgABmDYIAAAAIABAAIAAAAgADTDWIAAgBIABAAIAAABgAhYDVIAAgBIABAAIAAABgADYDQIAAgBIABAAIAAABgAhiDPIAAgBIABAAIAAABgADZDOIAAgvIABAAIAAAvgAhmDLIAAAAIAAAAIAAAAgAi+DIIAAgBIABAAIAAABgAhvDGIAAgBIABAAIAAABgAjjDDIAAgBIAAAAIAAABgAjgDAIAAgBIABAAIAAABgAkHC/IAAAAIABAAIAAAAgABSC9IAAgBIABAAIAAABgAjcC9IAAgBIABAAIAAABgAh7C8IAAgBIABAAIAAABgAkGC6IAAgBIABAAIAAABgAjYC5IAAAAIABAAIAAAAgAiAC3IAAAAIABAAIAAAAgAjUC3IAAgBIABAAIAAABgAjQC0IAAgBIABAAIAAABgABDCwIAAgBIABAAIAAABgAkDCwIAAgBIABAAIAAABgABACuIAAAAIABAAIAAAAgAA1CnIAAgBIABAAIAAABgAAzCmIAAAAIABAAIAAAAgAArCjIAAgBIABAAIAAABgAj6ChIAAgCIABAAIAAACgADMCfIAAAAIANAAIAAAAgAC9CfIAAgBIACAAIAAABgACzCeIAAgBIABAAIAAABgAAgCeIAAgBIABAAIAAABgACtCdIAAgBIABAAIAAABgAClCcIAAgBIABAAIAAABgACbCbIAAgBIABAAIAAABgACPCZIAAgBIABAAIAAABgAALCXIAAgBIAAAAIAAABgAAHCWIAAgBIABAAIAAABgACACUIAAAAIABAAIAAAAgAgBCUIAAgBIABAAIAAABgAgFCTIAAgBIABAAIAAABgAB4CSIAAAAIABAAIAAAAgAgJCSIAAAAIAAAAIAAAAgAB2CSIAAgBIAAAAIAAABgAgNCSIAAgBIABAAIAAABgABzCRIAAgBIABAAIAAABgAgTCRIAAgBIABAAIAAABgAgYCQIAAgBIABAAIAAABgAggCPIAAAAIACAAIAAAAgAgoCPIAAgCIACAAIAAACgABMB+IAAgBIABAAIAAABgABKB9IAAgCIABAAIAAACgAhOB6IAAgBIABAAIAAABgABEB4IAAgBIABAAIAAABgAhPB4IAAgBIABAAIAAABgAhQB3IAAgBIABAAIAAABgABBB2IAAAAIABAAIAAAAgAhRB2IAAgBIABAAIAAABgAhRB0IAAgBIAAAAIAAABgAhSByIAAgBIABAAIAAABgAhTBwIAAAAIABAAIAAAAgAA8BvIAAgBIABAAIAAABgAhUBvIAAgBIABAAIAAABgAhUBuIAAgBIAAAAIAAABgAjEBuIAAgBIABAAIAAABgAhVBsIAAgBIABAAIAAABgAhWBrIAAgCIABAAIAAACgAhXBoIAAAAIABAAIAAAAgAi8BoIAAgBIAAAAIAAABgAi6BmIAAgBIABAAIAAABgAi1BjIAAAAIAAAAIAAAAgABFBiIAAgBIABAAIAAABgAizBiIAAgBIABAAIAAABgAiDBgIAAgBIAAAAIAAABgABLBdIAAgBIABAAIAAABgAh/BbIAAgBIABAAIAAABgAinBaIAAgBIABAAIAAABgABSBZIAAgBIAAAAIAAABgAh6BVIAAgBIABAAIAAABgAidBUIAAgBIABAAIAAABgAD+BTIAAgBIABAAIAAABgABjBRIAAgBIABAAIAAABgAiWBRIAAgBIABAAIAAABgAEEBNIAAgBIABAAIAAABgAiPBMIAAgBIABAAIAAABgAARBLIAAAAIABAAIAAAAgAAKBLIAAgBIABAAIAAABgAACBKIAAAAIABAAIAAAAgAgEBKIAAgBIACAAIAAABgAgMBJIAAgBIACAAIAAABgAgUBIIAAgBIACAAIAAABgAgbBHIAAgBIABAAIAAABgAgjBGIAAgBIABAAIAAABgAEHBFIAAgBIABAAIAAABgAgrBFIAAgBIACAAIAAABgAgyBEIAAAAIABAAIAAAAgAg6BEIAAgBIABAAIAAABgAhCBDIAAgBIACAAIAAABgAAHA5IAAAAIABAAIAAAAgAAEA5IAAAAIAAAAIAAAAgAB5A2IAAgBIABAAIAAABgAASAxIAAAAIABAAIAAAAgAAUAwIAAgBIABAAIAAABgAD7AnIAAgBIAAAAIAAABgAgJAnIAAgBIABAAIAAABgAAZAlIAAAAIAAAAIAAAAgAD3AlIAAgBIABAAIAAABgAgLAkIAAgBIABAAIAAABgAAZAjIAAgBIABAAIAAABgAgNAgIAAgBIABAAIAAABgADFAdIAAAAIABAAIAAAAgAgQAdIAAAAIABAAIAAAAgAiSAdIAAgBIABAAIAAABgAiUAcIAAgBIABAAIAAABgAgSAaIAAAAIABAAIAAAAgAhqAaIAAAAIABAAIAAAAgAAcAaIAAgCIABAAIAAACgAgUAXIAAgBIAAAAIAAABgAhgAWIAAgBIABAAIAAABgAAdAVIAAgBIAAAAIAAABgAhXATIAAgBIABAAIAAABgAAdARIAAgBIABAAIAAABgAhNAOIAAgBIAAAAIAAABgAijANIAAgBIABAAIAAABgAijALIAAAAIAAAAIAAAAgAAeALIAAgBIABAAIAAABgAhEALIAAgBIABAAIAAABgAikAKIAAgBIABAAIAAABgAg7AGIAAAAIABAAIAAAAgAgMAGIAAgBIAAAAIAAABgAgLADIAAAAIABAAIAAAAgAAfADIAAgCIABAAIAAACgAgxADIAAgBIABAAIAAABgAgJABIAAAAIAAAAIAAAAgAinABIAAAAIAAAAIAAAAgAgIAAIAAAAIABAAIAAAAgAgoAAIAAAAIABAAIAAAAgAioAAIAAgBIABAAIAAABgAgGgCIAAgBIABAAIAAABgAgegDIAAgBIAAAAIAAABgAgFgEIAAgBIABAAIAAABgAipgEIAAgBIABAAIAAABgAgVgHIAAgBIABAAIAAABgAAggIIAAgHIAAAAIAAAHgAiqgKIAAgBIABAAIAAABgAgMgLIAAgBIABAAIAAABgAiqgUIAAgEIAAAAIAAAEgAAggYIAAgBIABAAIAAABgAAjgaIAAgBIABAAIAAABgAAqgfIAAgBIABAAIAAABgAgygiIAAgCIABAAIAAACgAA2goIAAAAIAAAAIAAAAgAiigtIAAgCIABAAIAAACgAijg0IAAAAIABAAIAAAAgAgrg4IAAgBIABAAIAAABgAijg6IAAAAIAAAAIAAAAgABMg7IAAgBIABAAIAAABgAgpg7IAAgBIABAAIAAABgAikg9IAAgBIABAAIAAABgAgmhBIAAAAIABAAIAAAAgAilhBIAAgBIABAAIAAABgABdhRIAAgBIAAAAIAAABgAgWhUIAAAAIABAAIAAAAgABkhfIAAgBIAAAAIAAABgAgKhfIAAgBIABAAIAAABgABohrIAAAAIAAAAIAAAAgAizhvIAAgBIABAAIAAABgABphzIAAgBIABAAIAAABgAANh2IAAguIABAAIAAAugAi4h6IAAgBIABAAIAAABgABqh7IAAgEIABAAIAAAEgAB0iDIAAgBIABAAIAAABgAB2iEIAAAAIAAAAIAAAAgAi8iFIAAgBIAAAAIAAABgACAiKIAAAAIABAAIAAAAgAg5iNIAAgBIABAAIAAABgAjAiOIAAgBIAAAAIAAABgACJiPIAAAAIABAAIAAAAgAjBiPIAAgCIABAAIAAACgACTiVIAAgBIAAAAIAAABgAgviVIAAgBIABAAIAAABgAgXidIAAgBIAAAAIAAABgAgQieIAAgBIAAAAIAAABgAgIigIAAgBIABAAIAAABgAgCihIAAgBIAAAAIAAABgAABiiIAAgBIABAAIAAABgAAHijIAAgBIABAAIAAABgAA8ikIAAAAIAIAAIAAAAgACnipIAAgBIABAAIAAABgAhsivIAAgBIABAAIAAABgAgniwIAAgBIABAAIAAABgAhyixIAAgBIABAAIAAABgACqi6IAAgBIABAAIAAABgAgUi6IAAgBIAAAAIAAABgAiVi/IAAgBIAAAAIAAABgACdjHIAAgBIABAAIAAABgACbjIIAAgBIABAAIAAABgACSjLIAAgBIABAAIAAABgAB5jTIAAgBIABAAIAAABgAB1jUIAAAAIABAAIAAAAgABxjUIAAgBIABAAIAAABgABsjVIAAAAIABAAIAAAAgAAIjVIAAAAIABAAIAAAAgABojVIAAgBIABAAIAAABgABkjWIAAgBIAAAAIAAABgABejXIAAgBIABAAIAAABgAAKjXIAAgBIABAAIAAABgABajYIAAgBIAAAAIAAABgABUjZIAAgBIABAAIAAABgABNjaIAAgBIACAAIAAABgABIjbIAAAAIAAAAIAAAAgABAjbIAAgBIABAAIAAABgAA9jgIAAAAIAAAAIAAAAgAA+jnIAAAAIABAAIAAAAgABAjuIAAgBIAAAAIAAABgAAVjyIAAAAIAAAAIAAAAgABBj1IAAgBIABAAIAAABgAAVj3IAAgBIABAAIAAABgABDj8IAAgBIABAAIAAABgABEkDIAAgBIABAAIAAABgABLkjIAAgBIABAAIAAABgABNkqIAAgBIABAAIAAABg");
	this.shape_111.setTransform(308.4,376.8);

	this.shape_112 = new cjs.Shape();
	this.shape_112.graphics.f("#EBEBEB").s().p("AANE3IAAgBIAAAAIAAABgAAYE2IAAAAIABAAIAAAAgAg4E1IAAgBIAAAAIAAABgAg8E0IAAgBIABAAIAAABgAA4EzIAAgBIABAAIAAABgAhGExIAAgBIABAAIAAABgAhKEvIAAAAIAAAAIAAAAgAhTEsIAAgBIABAAIAAABgABeErIAAAAIABAAIAAAAgAhkEkIAAgBIABAAIAAABgAB9EhIAAgBIABAAIAAABgACBEgIAAgBIABAAIAAABgAhsEfIAAgBIABAAIAAABgACIEcIAAAAIABAAIAAAAgACVEWIAAgBIAAAAIAAABgACeERIAAgBIAAAAIAAABgAiLEHIAAgBIAAAAIAAABgAiaD7IAAgBIABAAIAAABgADAD3IAAgBIAAAAIAAABgADEDyIAAgBIABAAIAAABgAAyDrIAAgBIABAAIAAABgAA4DqIAAgBIABAAIAAABgADMDpIAAAAIABAAIAAAAgAA9DpIAAAAIABAAIAAAAgABDDpIAAgBIABAAIAAABgAhQDlIAAAAIABAAIAAAAgAhRDlIAAgBIABAAIAAABgAhTDkIAAgBIABAAIAAABgABoDjIAAgBIAAAAIAAABgAhUDjIAAgBIABAAIAAABgAhVDiIAAAAIAAAAIAAAAgADTDhIAAgBIABAAIAAABgAhbDfIAAgBIAAAAIAAABgAhfDdIAAgBIABAAIAAABgABhDbIAAgBIABAAIAAABgAhrDVIAAgBIABAAIAAABgAhxDQIAAAAIABAAIAAAAgAjlDQIAAgBIABAAIAAABgABXDPIAAgBIgBAAIAAgBIABAAIAAABIABAAIAAABgABVDMIAAAAIABAAIAAAAgAjiDMIAAAAIABAAIAAAAgABUDLIAAgBIABAAIAAABgAjdDJIAAgBIAAAAIAAABgAjaDGIAAgBIABAAIAAABgAjWDDIAAgBIABAAIAAABgAiDDBIAAAAIABAAIAAAAgAjSDAIAAgBIABAAIAAABgAkGDAIAAgBIABAAIAAABgABEC+IAAgBIABAAIAAABgAA7C4IAAgBIABAAIAAABgAkDC4IAAgBIACAAIAAABgAkBC2IAAAAIAAAAIAAAAgAkBC1IAAgBIABAAIAAABgAAvCxIAAgBIABAAIAAABgAAtCwIAAgBIABAAIAAABgAAkCsIAAgBIACAAIAAABgAAdCpIAAgBIABAAIAAABgAClCoIAAgBIABAAIAAABgAAaCoIAAgBIABAAIAAABgACfCnIAAAAIABAAIAAAAgACWCmIAAgBIABAAIAAABgAigClIAAgBIABAAIAAABgAiiCkIAAgBIABAAIAAABgACCChIAAgBIABAAIAAABgAgaCcIAAgBIAAAAIAAABgAjuCcIAAgBIABAAIAAABgABoCZIAAgBIAAAAIAAABgAjlCTIAAgBIABAAIAAABgAikCRIAAgBIABAAIAAABgABPCMIAAgBIAAAAIAAABgAjdCMIAAgBIAAAAIAAABgAifCLIAAgBIABAAIAAABgAibCGIAAgBIABAAIAAABgAjRCCIAAgBIAAAAIAAABgAA/CBIAAgBIABAAIAAABgAA7B4IAAAAIABAAIAAAAgAjCB3IAAgBIABAAIAAABgABHBrIAAgBIABAAIAAABgADdBqIAAgBIAAAAIAAABgAivBqIAAgBIAAAAIAAABgADkBpIAAgBIABAAIAAABgADKBmIAAAAIABAAIAAAAgABOBmIAAAAIABAAIAAAAgADIBmIAAgBIAAAAIAAABgADFBlIAAgBIAAAAIAAABgADCBkIAAgBIABAAIAAABgABSBkIAAgBIABAAIAAABgADABjIAAgBIABAAIAAABgAC+BiIAAAAIABAAIAAAAgAC7BiIAAgBIABAAIAAABgAC5BhIAAgBIABAAIAAABgAigBhIAAgBIABAAIAAABgAC3BgIAAgBIABAAIAAABgAC0BfIAAgBIACAAIAAABgACyBeIAAAAIABAAIAAAAgACwBeIAAgBIAAAAIAAABgAibBeIAAgBIABAAIAAABgABgBdIAAgBIABAAIAAABgABnBaIAAgBIABAAIAAABgAiSBZIAAgBIABAAIAAABgABrBYIAAgBIABAAIAAABgAAyBXIAAAAIAAAAIAAAAgAARBXIAAAAIABAAIAAAAgABvBXIAAgBIABAAIAAABgAB1BUIAAgBIABAAIAAABgAA+BTIAAAAIABAAIAAAAgABBBTIAAgBIABAAIAAABgAEGBSIAAgBIABAAIAAABgAgjBSIAAgBIABAAIAAABgAB+BRIAAgBIAAAAIAAABgAgrBRIAAgBIABAAIAAABgACABQIAAAAIABAAIAAAAgACDBQIAAgBIAAAAIAAABgABPBOIAAgBIABAAIAAABgABfBJIAAgBIAAAAIAAABgABuBFIAAgBIAAAAIAAABgAACBFIAAgBIABAAIAAABgABwBEIAAgBIABAAIAAABgAAABBIAAAAIAAAAIAAAAgAB8BAIAAgBIABAAIAAABgAB+A/IAAgBIACAAIAAABgAgBA+IAAgBIAAAAIAAABgAASA9IAAgBIAAAAIAAABgACLA7IAAgBIABAAIAAABgAgDA7IAAgBIAAAAIAAABgACNA6IAAAAIABAAIAAAAgAgGA4IAAgBIABAAIAAABgACdA2IAAgBIAAAAIAAABgAgJA1IAAgBIABAAIAAABgAAXAzIAAAAIAAAAIAAAAgACrAxIAAgBIABAAIAAABgADyAtIAAgBIABAAIAAABgAC6AsIAAgBIABAAIAAABgAAZAsIAAgBIAAAAIAAABgAC8ArIAAAAIABAAIAAAAgADvArIAAgBIABAAIAAABgAiSApIAAgBIABAAIAAABgAhwAoIAAAAIABAAIAAAAgADJAoIAAgBIABAAIAAABgADLAnIAAgBIABAAIAAABgAiXAnIAAgBIABAAIAAABgAhnAkIAAAAIABAAIAAAAgADXAjIAAgBIABAAIAAABgADaAiIAAgBIABAAIAAABgAhdAgIAAAAIABAAIAAAAgAieAgIAAAAIABAAIAAAAgAhUAdIAAgBIABAAIAAABgAiiAdIAAgBIABAAIAAABgAhKAZIAAgBIAAAAIAAABgAAdAXIAAgBIABAAIAAABgAhBAVIAAgBIAAAAIAAABgAimAUIAAgBIABAAIAAABgAg4ARIAAgBIABAAIAAABgAg0APIAAgBIAAAAIAAABgAgvANIAAgBIABAAIAAABgAgrALIAAgBIABAAIAAABgAglAJIAAgBIAAAAIAAABgAgiAHIAAAAIACAAIAAAAgAgcAFIAAgBIABAAIAAABgAgYADIAAAAIABAAIAAAAgAAfACIAAgBIAAAAIAAABgAgTABIAAgBIABAAIAAABgAirABIAAgBIABAAIAAABgAgOAAIAAAAIABAAIAAAAgAgJgBIAAgBIAAAAIAAABgAgFgCIAAgBIABAAIAAABgAg0gYIAAgBIACAAIAAABgAAzgbIAAgBIABAAIAAABgAgygdIAAgBIABAAIAAABgAikgtIAAgBIAAAAIAAABgABSg4IAAgBIABAAIAAABgAing5IAAAAIABAAIAAAAgAgjg6IAAgBIABAAIAAABgABVg8IAAgBIABAAIAAABgABehJIAAgBIABAAIAAABgABfhLIAAAAIAAAAIAAAAgABghOIAAgBIABAAIAAABgABhhPIAAgBIAAAAIAAABgAgJhWIAAAAIAAAAIAAAAgAixhaIAAgBIABAAIAAABgABmhcIAAgBIABAAIAAABgAgBhdIAAgBIAAAAIAAABgABnhiIAAgBIABAAIAAABgAADhiIAAgBIABAAIAAABgAi2hnIAAgBIABAAIAAABgABsh0IAAgBIACAAIAAABgAi8h0IAAgBIABAAIAAABgABwh2IAAgBIABAAIAAABgABxh3IAAAAIABAAIAAAAgAB2h5IAAgBIABAAIAAABgAB4h6IAAgBIABAAIAAABgAi+h7IAAgBIAAAAIAAABgAg7iAIAAgBIABAAIAAABgAjDiGIAAAAIABAAIAAAAgAg0iHIAAgBIAAAAIAAABgACXiOIAAgBIABAAIAAABgAgiiPIAAgBIACAAIAAABgABIiTIAAgBIAAAAIAAABgAhIiaIAAgBIABAAIAAABgAhUiaIAAgBIABAAIAAABgAhCicIAAAAIABAAIAAAAgAjQifIAAAAIABAAIAAAAgAhkigIAAgBIAAAAIAAABgAjRigIAAgBIABAAIAAABgACoijIAAAAIABAAIAAAAgAhrijIAAAAIABAAIAAAAgAjPijIAAAAIABAAIAAAAgAjNikIAAgBIACAAIAAABgACpilIAAgBIAAAAIAAABgAhxilIAAgBIABAAIAAABgAgnimIAAgBIABAAIAAABgAjKimIAAgBIABAAIAAABgAh3inIAAgBIAAAAIAAABgAjIinIAAgBIABAAIAAABgAjGipIAAgBIABAAIAAABgAh+iqIAAAAIABAAIAAAAgACpiqIAAgCIABAAIAAACgAjDiqIAAgBIABAAIAAABgAjBisIAAgBIABAAIAAABgAi+iuIAAAAIAAAAIAAAAgAi8ivIAAgBIAAAAIAAABgAgTiwIAAgBIAAAAIAAABgAiOiwIAAgBIABAAIAAABgAi5ixIAAgBIAAAAIAAABgACniyIAAAAIABAAIAAAAgAiUiyIAAgBIABAAIAAABgAi3iyIAAgBIABAAIAAABgAi1i0IAAgBIAAAAIAAABgAgJi3IAAgBIAAAAIAAABgACdi6IAAgBIABAAIAAABgAgFi6IAAgBIABAAIAAABgACYi9IAAAAIABAAIAAAAgACXi9IAAgBIAAAAIAAABgACFjEIAAAAIAAAAIAAAAgACCjEIAAgBIABAAIAAABgAB+jFIAAgBIACAAIAAABgAB7jGIAAgBIABAAIAAABgABjjLIAAAAIABAAIAAAAgABZjMIAAgBIABAAIAAABgABUjNIAAgBIABAAIAAABgAALjPIAAgBIABAAIAAABgAA7jQIAAgBIAAAAIAAABgAA8jXIAAgBIABAAIAAABgAASjgIAAgBIAAAAIAAABgABLkaIAAgBIABAAIAAABgABNkhIAAgBIABAAIAAABgAAPkmIAAAAIABAAIAAAAgAAPkpIAAgBIAAAAIAAABgAAOksIAAgBIABAAIAAABgAANkvIAAgBIABAAIAAABgAANkyIAAgBIAAAAIAAABgAAMk1IAAgBIABAAIAAABg");
	this.shape_112.setTransform(308.6,375.6);

	this.shape_113 = new cjs.Shape();
	this.shape_113.graphics.f("#B2B2B2").s().p("AgmEnIAAgBIAvAAIAAABgAAVEmIAAgBIACAAIAAABgAgtEmIAAgBIABAAIAAABgAAhElIAAgBIACAAIAAABgAgyElIAAgBIABAAIAAABgAAqEkIAAAAIABAAIAAAAgAg2EkIAAAAIABAAIAAAAgAAyEkIAAgBIAAAAIAAABgAA3EjIAAgBIABAAIAAABgAA9EiIAAgBIAAAAIAAABgABGEgIAAAAIABAAIAAAAgABKEgIAAgBIABAAIAAABgABOEfIAAgBIABAAIAAABgABSEeIAAgBIAAAAIAAABgABYEcIAAAAIABAAIAAAAgABeEbIAAgBIABAAIAAABgAhYEZIAAgBIABAAIAAABgAhbEXIAAgBIABAAIAAABgAhrEOIAAgBIABAAIAAABgACHENIAAgBIABAAIAAABgAhwEKIAAAAIABAAIAAAAgACWEFIAAgBIABAAIAAABgAh6EDIAAAAIABAAIAAAAgACgD/IAAAAIABAAIAAAAgAiAD/IAAgBIABAAIAAABgAiID4IAAAAIABAAIAAAAgACvD1IAAgBIABAAIAAABgAiND0IAAAAIABAAIAAAAgAArDcIAAgBIAAAAIAAABgAg3DcIAAgBIAAAAIAAABgAAwDbIAAgBIABAAIAAABgAg8DbIAAgBIABAAIAAABgAA2DaIAAAAIABAAIAAAAgAA8DaIAAgBIABAAIAAABgAhCDaIAAgBIAAAAIAAABgABBDZIAAgBIABAAIAAABgAhFDZIAAgBIABAAIAAABgABHDYIAAgBIABAAIAAABgABMDXIAAAAIABAAIAAAAgABSDXIAAgBIAAAAIAAABgABXDWIAAgBIABAAIAAABgABdDVIAAgBIAAAAIAAABgABiDUIAAgBIABAAIAAABgABoDTIAAAAIAAAAIAAAAgADTDSIAAgBIAAAAIAAABgAj6DQIAAAAIABAAIAAAAgAhZDQIAAgBIABAAIAAABgABnDPIAAgBIABAAIAAABgAj2DNIAAgBIABAAIAAABgABkDMIAAgBIABAAIAAABgAi2DMIAAgBIABAAIAAABgAjyDKIAAgBIAAAAIAAABgAhjDJIAAgBIABAAIAAABgAjuDHIAAgBIAAAAIAAABgAjrDEIAAgBIABAAIAAABgAjBC/IAAgBIABAAIAAABgAkHC8IAAgBIABAAIAAABgAkGC2IAAAAIABAAIAAAAgAiGCuIAAgBIABAAIAAABgAA4CkIAAAAIABAAIAAAAgAj/CkIAAgBIABAAIAAABgADHCbIAAgBIAEAAIAAABgAC6CaIAAgBIACAAIAAABgACyCZIAAAAIABAAIAAAAgACqCZIAAgBIACAAIAAABgAAfCZIAAgBIABAAIAAABgACfCXIAAgBIABAAIAAABgACaCWIAAgBIABAAIAAABgAARCUIAAgBIABAAIAAABgAAICRIAAAAIABAAIAAAAgAAFCRIAAgBIABAAIAAABgAAACPIAAgBIAAAAIAAABgAgICOIAAgBIABAAIAAABgAgMCNIAAgBIABAAIAAABgAgRCMIAAgBIABAAIAAABgAgXCLIAAgBIABAAIAAABgAgeCKIAAAAIABAAIAAAAgABsCKIAAgBIABAAIAAABgAhECJIAAgBIAQAAIAAABgAiqCIIAAgBIABAAIAAABgABhCFIAAgBIABAAIAAABgAilCDIAAgBIABAAIAAABgABWB/IAAAAIABAAIAAAAgABFB0IAAAAIABAAIAAAAgAjSBzIAAgBIABAAIAAABgABCByIAAgBIABAAIAAABgAA+BvIAAgBIABAAIAAABgAA9BnIAAgBIAAAAIAAABgAjABmIAAgBIABAAIAAABgAixBcIAAgBIAAAAIAAABgADcBaIAAgBIABAAIAAABgADwBVIAAgBIAAAAIAAABgABQBVIAAgBIABAAIAAABgABUBTIAAgBIABAAIAAABgAD3BSIAAgBIABAAIAAABgABWBSIAAgBIAAAAIAAABgAD5BRIAAgBIABAAIAAABgAifBRIAAgBIAAAAIAAABgAhNBQIAAAAIAAAAIAAAAgAhNBPIAAgBIABAAIAAABgAiaBOIAAgBIABAAIAAABgABfBNIAAgBIABAAIAAABgAhMBNIAAgBIABAAIAAABgAh3BMIAAAAIABAAIAAAAgAhLBMIAAgBIABAAIAAABgAhKBKIAAgBIABAAIAAABgAiTBKIAAgBIABAAIAAABgAhJBIIAAAAIAAAAIAAAAgABrBIIAAgBIABAAIAAABgAEFBHIAAgBIABAAIAAABgAAPBHIAAgBIACAAIAAABgAhJBHIAAgBIABAAIAAABgAAHBGIAAgBIACAAIAAABgAEGBFIAAAAIAAAAIAAAAgAAABFIAAAAIABAAIAAAAgAhIBFIAAAAIABAAIAAAAgAgGBFIAAgBIABAAIAAABgAgOBEIAAgBIACAAIAAABgAhHBEIAAgBIABAAIAAABgAgVBDIAAgBIABAAIAAABgAgdBCIAAgBIABAAIAAABgAhGBCIAAgBIAAAAIAAABgAglBBIAAAAIACAAIAAAAgAgtBBIAAgBIACAAIAAABgAhGBBIAAgBIABAAIAAABgAg0BAIAAgBIABAAIAAABgAEHA/IAAgUIABAAIAAAUgAg8A/IAAgBIABAAIAAABgAhFA/IAAgBIABAAIAAABgACFA+IAAgBIABAAIAAABgAhEA+IAAgBIACAAIAAABgABeA6IAAgBIABAAIAAABgAAEA1IAAgBIADAAIAAABgAAOAyIAAgBIAAAAIAAABgAAVApIAAgBIAAAAIAAABgAAVAnIAAAAIABAAIAAAAgAD7AjIAAgBIABAAIAAABgACrAhIAAgBIABAAIAAABgAD4AgIAAAAIABAAIAAAAgAgKAgIAAgBIABAAIAAABgAD1AeIAAgBIABAAIAAABgAC6AdIAAgBIAAAAIAAABgAgMAdIAAgBIAAAAIAAABgAgPAZIAAAAIABAAIAAAAgAgRAWIAAgBIABAAIAAABgAhsAWIAAgBIABAAIAAABgAAcAUIAAgBIABAAIAAABgAgUATIAAgBIABAAIAAABgAhiASIAAAAIAAAAIAAAAgAgWAQIAAgBIABAAIAAABgAhZAOIAAAAIABAAIAAAAgAAdAKIAAAAIABAAIAAAAgAhQAKIAAAAIABAAIAAAAgAhGAHIAAgBIAAAAIAAABgAAeAEIAAgBIABAAIAAABgAg9ADIAAgBIABAAIAAABgAg0AAIAAAAIABAAIAAAAgAAfgCIAAgDIABAAIAAADgAingCIAAgBIAAAAIAAABgAgqgDIAAgBIABAAIAAABgAiogGIAAAAIABAAIAAAAgAghgHIAAgBIABAAIAAABgAgXgLIAAgBIAAAAIAAABgAgOgPIAAgBIABAAIAAABgAiqgQIAAgBIABAAIAAABgAgFgTIAAgBIABAAIAAABgAAggUIAAgJIAAAAIAAAJgAihgnIAAgEIABAAIAAAEgAgxgsIAAgBIABAAIAAABgAA2gtIAAgBIABAAIAAABgAiig0IAAgBIABAAIAAABgAgtg5IAAgBIABAAIAAABgAijg6IAAgBIABAAIAAABgAgpg/IAAgBIAAAAIAAABgAijg/IAAgBIAAAAIAAABgABVhLIAAAAIABAAIAAAAgABehZIAAgBIABAAIAAABgABoh0IAAgBIABAAIAAABgABph5IAAgCIABAAIAAACgAi4iCIAAAAIAAAAIAAAAgABqiDIAAgBIABAAIAAABgABriEIAAgBIABAAIAAABgAi7iHIAAgBIABAAIAAABgAB7iMIAAgBIABAAIAAABgAB9iNIAAAAIAAAAIAAAAgAi+iOIAAgBIABAAIAAABgAgyiYIAAgBIABAAIAAABgAjDiYIAAgBIABAAIAAABgAjDiaIAAgBIAAAAIAAABgACTibIAAgBIABAAIAAABgAjEicIAAAAIABAAIAAAAgAjFidIAAgBIABAAIAAABgAgNikIAAgBIABAAIAAABgAgJilIAAgBIABAAIAAABgAgEimIAAgBIABAAIAAABgAAAinIAAAAIAAAAIAAAAgAAFinIAAgBIACAAIAAABgACiioIAAgBIABAAIAAABgAALioIAAgBIACAAIAAABgAjKioIAAgBIAAAAIAAABgAhIiqIAAAAIABAAIAAAAgAjLiqIAAAAIABAAIAAAAgAhCirIAAgBIAAAAIAAABgAjMirIAAgBIABAAIAAABgAjNitIAAgBIABAAIAAABgAjOiuIAAgBIABAAIAAABgAjPiyIAAAAIABAAIAAAAgAhqizIAAgBIAAAAIAAABgAgqi0IAAgBIABAAIAAABgAhxi1IAAgBIABAAIAAABgACqi3IAAgBIABAAIAAABgAgji3IAAgBIABAAIAAABgAgYi9IAAAAIABAAIAAAAgAgTjAIAAAAIABAAIAAAAgAiUjDIAAgBIABAAIAAABgAizjEIAAgBIAbAAIAAABgACfjKIAAgBIABAAIAAABgACXjOIAAgBIABAAIAAABgACTjPIAAgBIABAAIAAABgAB4jXIAAgBIABAAIAAABgABdjcIAAgBIABAAIAAABgABYjdIAAAAIABAAIAAAAgABSjdIAAgBIABAAIAAABgABMjeIAAgBIABAAIAAABgABGjfIAAgBIABAAIAAABgAA+jgIAAgBIACAAIAAABgAANjhIAAAAIABAAIAAAAgAA9jlIAAgBIAAAAIAAABgAA+jsIAAgBIABAAIAAABgABAjzIAAgBIAAAAIAAABgAAVj6IAAgBIABAAIAAABgABBj7IAAAAIABAAIAAAAgABDkCIAAAAIABAAIAAAAgAAWkCIAAggIABAAIAAAggABEkJIAAAAIABAAIAAAAgABGkQIAAgBIABAAIAAABgABIkXIAAgBIAAAAIAAABgABJkeIAAgBIABAAIAAABgABLklIAAgBIAAAAIAAABg");
	this.shape_113.setTransform(308.4,377.3);

	this.instance_13 = new lib.shape12("synched",0);

	this.instance_14 = new lib.text11();
	this.instance_14.setTransform(117.6,344.8,0.859,0.859);
	this.instance_14.filters = [new cjs.ColorFilter(0, 0, 0, 1, 102, 102, 102, 0)];

	this.shape_114 = new cjs.Shape();
	this.shape_114.graphics.f("rgba(0,0,0,0.302)").s().p("AASDsMhQkAARIAAh6MBODgAMIAAgCIE6AAIAAACMBNoAAMIAAB6gEBAuAB3IhTgsI2ZgIIAHADIgHAAIA1AsIkQAAIgsgnIgFAAIgHgKI2DAAIAWAsIkYAAIgPgsI0TAAIgPAsIkYAAIAUgsI1/AAIg1AxIkWAAIA7gsIgKAAIAAgDI2ZAIIhTAsIkcACIBmguItMAAIAAkDIBrgkIE3ggIERAPIhTAiIM2gCIAPgHICBgDIA9AKIXwgRIgOAKITAAAIAHgNIB6AAIBOANIShAAIBHgNICDAAIADANIS+AAIgKgKIXpARIBCgKICBADIATAHIM0ACIhVgiIEWgPIE1AgIBGAZIAAEOIslAAIBaAugEBF7gBMIA6ATQAIgPAnAAIBGAAQg1gkBwAFQgegihygbIm2gCQAFAPApAVIBTAZIAFAAQASgCATAAQBNAABkAfgEhIFgBIIBEAAQAdAAAPAPIA6gTQB6gnBfAKIACAAIBRgZIAwgkImxACQh8AbgYAiIAXgBQBPAAgnAggAbAhAIgMgWQgHgnB5AKIAIAAQATgdhLgbIl6AAQgiAYBMAdIAFADQBmgKA/AlIAgAWIAfgIIAxAKgA6RhWIgFAWIAsgKIAfAIIAZgWQA/glBmAKIAKgDQBEgdgdgYIl3AAQhSAbAYAdIAFAAQAYgCATAAQBMAAAAAfgEAxZgBKQgWgkBuAHQgDgkhhgbImZAAQgTAaB1AjQBVgGBhAlIBpgEIAkAEIAAAAgEgusgBKQBdglBaAGQBwgjgRgaImUAAQhpAbAFAkQBmgHgTAkIAigEgAg9hbIADAFIA7AAIARACIAOgCIA4AAIAFgFQAPgWAwgCIAAAEIBOgCQAzgkg1gZImtAAQg6AZA9AkIBGACIAAgEQAuACARAWg");
	this.shape_114.setTransform(491.7,563);

	this.shape_115 = new cjs.Shape();
	this.shape_115.graphics.f().s("rgba(0,0,0,0.302)").ss(0.3,1,1).p("EggJgK/IgxAAIqfAAIg2AAIquAAIgwAAIrRAAIiKAAIrOAAIAAB/IAAAdIAACyIAAAWIAABVIAAEMIAAAOIAAARIAAAgIAAEpIAAAsIAAEmIK4AAIARAAIAxAAIA/AAIVjAAIBrAAIZQAAIARAAIBJAAIRCAAIAMAAIA2AAIbkAAIA4AAIA6AAIBAAAIMhAAIA8AAIQGAAIAwAAIBHAAILfAAIBQAAIFZAAIAAkEIAAhCIAAhTIAAi5IAAg1IAAgFIAAggIAAhyIAAgWIAAggIAAjiIAAgbIAAgNIAAiMIAAgKIAAiLMg5EAAAAxPq/IhmAAIgNAAIgEAAIglAAIieAAIgYAAIpeAA");
	this.shape_115.setTransform(492.3,611.3);

	this.shape_116 = new cjs.Shape();
	this.shape_116.graphics.f("#ECE0B8").s().p("A/JJMIATgMQCUhaEzh8QAHgKARgHIAqgMIACAAIAUAAIAEgDIK2AgIgPACIDqgCIDPAMQGnAYrtgCIsJgMIgHAFIoBDHgEggLAJMQAigTAugWIF5jNItng8IAPAAILwAfIAFgFIAAgCIATAAIBVAKIARAAIAnAEIAHADIAUACIACAFQAFAFgCAKIgDAFIgTAFIn/DpgAliIxQgHgRAYgMIBRgpQAdgWAngRIB+g2IBDgTIAVACIA4gFIMmAeQH3AWyDgRIhOAAIiZAOIgeAAIi3BQQg1AWgnAgIgsAKgAqXErIAigWIAFgDQBMgpAzgYIAdgKIETiNIBtgqIA2gHIAMAAIAlgFIEAADIMAAYIweAHIgOADIgMAFIgIAAIgRACIgJAAIh6A2IgdAJImBC3QhVAhgLAAQgLAAAzgcgEgi4ACAIFcioIAzgRIMZAbIBdAFQKAARqxgDIq6gCQhCgIg6AIIgDAAIg/APIm4DAQhOAggJAAQgQAADDhigAbEgBQgMgKBfhoICbimIADgKQAFgJAOgFIADgDIAJgFIBJAIIAIACIKfAHIroBAIjnDTIgvAUgEgq5gASQgMgRAYgKIA6gWID4gOIC3AEIDFAPQEVAWloAFImWgFQgigHggAHIgOACIhfAZgAnjgXIjfg4Ig9gYIgKgFIgCAAIgNgDIgCgCIgFgFIgFAAIgfgTIgDAAIAAgNIADgCIACgFIAHgFIAigOQAWgMAWgIIAFgCIAHAAIAKACIBdgkIAJgIIAngVIAugRIAIgFIAWgKIBjgiIAggKIAdgOIAPgIIAKAAIAMgCIBBACIBdADIAPgDIJPARIAQAAIAIAAIAEAAIAEAAIAGAAIACAAIAJABIAQAAIBDACQF7ANjMAGQjnAKkYgHIoOgNIgWgCIgMAFIgUAFIh0AsIgUAEIk1CEICQAwQBQAWA/AiQAbANgFACgEglsgBxQgMgMAMgMIACgFIAFgDIBdgdIAMAAIHJiBIgTgCIgsgIQj6hNgFgZQgIgOARgKIAiAAIANACQArANCGA/QBiAugKAKICegsICLglIAHgHIACgCIAWgKIAPACINWAsQshgHg6AHIgbAAIgMgHIgFgHIgCACQkiBkjzBJIkfBagAKwh7IgHgFIAAgCIAMgRIAWgKIBOguIARgHIAngWIAMgFIAdgUIBrg6IAFAAIAYgMIAAgDIAKgCIEEAHIGPAWQBaAKlKgFImcgMIgHAFIlQC2gAUwmQIDxihIAbgTIAHAAIAKAAIAAgDIAHAAIAHgCIAAgCIGlAJIFVAZIraAwQljCkgpAAQgYAABZg7gEgrDgHPIDsAJIjsBMg");
	this.shape_116.setTransform(253.6,622.9);

	this.shape_117 = new cjs.Shape();
	this.shape_117.graphics.f("#EEE3C0").s().p("AgaDUQgIgSARgJIADAAQBRgeBLgmICBhAIARgFIBpgzIACAAIAHAAIADgDIINANICNAFQEdAKsLATIiegFQhEAbg6AgIgZAMQgkAKgdAVIihBMIg7AEgAqnCVIApgTIAIgEIEHh/IAdgJIAFgGIAiAGIJhAPQAfACgwAIIpeAMIlkCNQhTAdgMAAQgPAABkgwgEggZABVQgfgRAfgRIAvgWID/hRIMLATQISAKpeAjIp4gUIgqgFIAAADIk1BfgAXXAHIgIgRIAAgDIANgMIAEAAIAIgFIAWgHIAOgFIAUgKQgsAYAbAFIEmgCIDFATQBhALhmAEIoLADgAOMgeQgJgHAJgDIANgKIEjilIAAgCIANACIgNAAIANAAIAFAKIgFAHIjKB6IhmAxgAaNhzIBOguIAHgFQAbgWAdgPIAPAAIAKgHIAFgFIAEgCIAxAAIgHACIgiACIAfAAIkTCEQAbgTAigPg");
	this.shape_117.setTransform(249.1,562.5);

	this.shape_118 = new cjs.Shape();
	this.shape_118.graphics.f("#E5D7A5").s().p("AHQCQIgHgKQgFgOAWgFIKuAFIkJAVQjbAJhtAAQhaAAgNgGgAxSgsIgagbIgPguIgFAAQgOgTARgKIACAAIAHgDICvAAIJcANQJSAMo3AHIr1ADIAbA6IgDAKIgMAJIgHACQgKAAgKgJg");
	this.shape_118.setTransform(652.4,608.2);

	this.shape_119 = new cjs.Shape();
	this.shape_119.graphics.f("#999D80").s().p("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");
	this.shape_119.setTransform(492.3,611.3);

	this.shape_120 = new cjs.Shape();
	this.shape_120.graphics.f("#DED4A5").s().p("EAgaAIpIgFgDIg/g4IgsgaIgHgMIgFAAIhMhaIgFgKIAFgDIAFgKIADgCIAMAAIAFgCIACgDIBHAFIGbgMIB+AFIm/AxIh6gFIA/A6IADAHQATAIAMAMIBHA6IATATIARANgABHIpIiLiNIgHgCIgFgDIgMgRIAFgDIACgHIAFgEIAigGIIiADIDHAFIAMACIAFAFIhaAZIjdAAQhTgIhOAKIj2AAICJCNgAgpIpIACgbIgCgHIhVhJIgFgHIg6gPIkngKItkAWImcgMIgpCBIg4AAIAuiVIAFgFIAMgOIANgFIAVADICXAAIA2AEIACAAIAPADIAMAAIAYgDIADAAIA9gEITHgDIA4ADIAUAEIAkAMIAnAeIBEBBIAPAdIgCAegEgvcAIpIBJg9IACgFIA7gnIATgRIBTg9IAYAAIAIgCIJnAPIDFAKQCSAJmZAKIm6AHIgzgHIg0ACIgRACIi2CJgEApTAFmIgCgHQgKgWAWgPIAFAAIAHgCIImgRQAiADglAHIohBJgAnYFmIgWgFIgEgHIgxg1IgYgUIgvgrIgCgLIAKgEIACAAIADgDICDgFIARgCIAbACIBfAAIAngEIA4AAIAJACIAWACIADAAQAJAKgOAHIgKADIjAAFIg4gFIgZAFIhEACQAeARAiA9IAdAugAX2FXIiZhXQlGjAg6gpIgFgFIgbgUIgPgDIgJgCIgFgCIgKgNIAFgKIAHgHIAFAAIAAgCIBMAHIAdAAIBVgCIARgDIF0AAIEJAKIqHAdIgOAAIhTADIH1FUgEgzoAEvIAAgIIAFgKICvilIAFgHIARgPIAigkIAzglIAlgNIAFAAIBogVIKMAEQGyANlrAEIrEALIh/AdQgwAbgiApIieCeIgvAkQgGACgGAAQgOAAgIgNgEAtEgAYIgPgCIgMgGIgMgVQgFgNAMgMIAKgEIAHAAIAFgDIAAgDIBMADIAAgDIARADIBaAAID4gHIAAA1IjFAWIgKgCIgWAAIDlCjIAABTgEAgtAAKIgWgCIgCAAIgMgCQgsgNACgWIAKgRIAKgKIAFAAIAHgDIAAgCIACAAIAUgCIAdAKIAFAAIATACII1gWIBTAIIoyBNIglAAICLCOQiohigxgugAzugTIgKgNIAAgCIgKgHIgHgIIguh5IgZg0IgCgCIgPgiIgaguIAAgCQgDgRARgGIADAAQgRALAOATIAFAAIAPAuIAaAbQAPAMAMgFIAuBnIAWBGIKAAOQFJAKlJADIgFAAQixgIijAIIkpACgEAlugCZIjRh1Ig4gqIgHAAIgKgEQgFgMAKgFIAFgHIACAAIAIACIDMgMIDZAAIFNAAQGRAAtpAfIjUAKIADAFQBQAkDACEIBOA8IgCABQgSAAiNhOgEAzRgEXIhCgqIgKgIIgKgEIgCAAIgKgKIgYgPIgbgYIgFgDIgzgoIhkgzIgEgGIgIgCIgYgMIgCgFIgFAAIgFgIIAAgCIACgJIAKgDIATADIAUAAIAWgGIEwgaIAAAaIitALIgHAEIh/APIBMAnIAFAAIA4AmIAkAiICGBWIAAAfg");
	this.shape_120.setTransform(676.1,626.4);

	this.shape_121 = new cjs.Shape();
	this.shape_121.graphics.lf(["#F3ECD5","#E8D9A6","#B5C1A4"],[0,0.235,1],-19.6,-115,19.2,114.9).s().p("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");
	this.shape_121.setTransform(492.3,611.3);

	this.shape_122 = new cjs.Shape();
	this.shape_122.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AguASIgbglIGhimIBGpAIBwAAIBTJKIC2BLABCBKIAAhlIhwAtAN7g6IB6A1IgRAtIhOgiIAABEItUAAIt9KTAOWAGInRi6ImDCZAguLsIo3gIIgYgFIAAAFAp9LfIgnAAIh8gCIgbAAAs9LfIACgCIhJAAIhYgCAMJCTIhOFgIhCA4AEPIrIFqAAIAADPIlqgFIAAjKIjxDDIhMgCAK7HzIm4AAIkxD5AEPL1IjxgHADDCTIJGAAAEDHzIhAlgAv0LbIPGrJAOWBKIiNBJAp9LfINApM");
	this.shape_122.setTransform(865.7,185.5);

	this.shape_123 = new cjs.Shape();
	this.shape_123.graphics.lf(["#84576B","#470F21"],[0,0.914],12.9,-2.6,-8.7,-0.5).s().p("Ah3BeIDvjBIAADHg");
	this.shape_123.setTransform(880.8,251.2);

	this.shape_124 = new cjs.Shape();
	this.shape_124.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],0.1,-10.7,0.1,4.7).s().p("Ai0BiIAAjIIFpAAIAADNg");
	this.shape_124.setTransform(910.9,251.5);

	this.shape_125 = new cjs.Shape();
	this.shape_125.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],0.8,-21.1,-4.8,19.5).s().p("AjiCwIg/lfIJDAAIhOFfg");
	this.shape_125.setTransform(914.3,217.9);

	this.shape_126 = new cjs.Shape();
	this.shape_126.graphics.lf(["#84576B","#470F21"],[0,0.914],48.6,-11.6,-41,28.4).s().p("AmnEkIgYgEINApLIBAFfIkzD4gAm/EkIAAgEIAYAEg");
	this.shape_126.setTransform(846.7,230.3);

	this.shape_127 = new cjs.Shape();
	this.shape_127.graphics.f("#3F1826").s().p("ArQFIIh8gDIgbAAIN+qRINTAAIiNBJIpGAAItAJLIgnAAIAnAAIAAAFgAtnFFIAbAAIgdADg");
	this.shape_127.setTransform(870.1,226.3);

	this.shape_128 = new cjs.Shape();
	this.shape_128.graphics.f("#CEADBA").s().p("Ag7CWIAZiNQAJhAAeg6IAFgKIAkANIAPAYIAAAlIgFAMQgYBYgKBjIgHAsIgUAZQhAgIAKg9gAAFiYQgjgTAWggIANgOQBCAFgZAwIgOAMg");
	this.shape_128.setTransform(908.1,143.3);

	this.shape_129 = new cjs.Shape();
	this.shape_129.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],16.9,-12,-18.2,29.2).s().p("AmoB/IAAhmIGDiXIHOC5IAABEg");
	this.shape_129.setTransform(914.9,180.2);

	this.shape_130 = new cjs.Shape();
	this.shape_130.graphics.f("#724255").s().p("AguLyIExj5IG4AAIhCA4IlqAAIjxDDgAuELjIhYgCIgYAAIPGrJIBwgtIhwAtIgbglIGhimIBGpAIBwAAIBTJKIC2BLIBkAqIB6A0IgRAuIhOgiInRi6ImDCZIAABlIt9KTIhJAAIBJAAIgCACgAGtoRQggA6gJBCIgZCNQgKA9BAAHIAWgYIAHgsQAKhkAYhaIAFgMIAAgkIgPgZIgkgMgAGeprQgWAgAlATIAbAAIAOgMQAZgxhCgFg");
	this.shape_130.setTransform(865.7,184.9);

	this.shape_131 = new cjs.Shape();
	this.shape_131.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("AsThSIC0hOIBTpIIByAAIBHI+IGgCjIgaAnIhzgsImBicInTC8AuUBbIAAhHIhLAiIgUgwIB6gvAsECfIiQhEAhABbIAAhnAsECfIJFAAIMtJKAhABbItUAAAhDLWIjKicIAACcAkAICIEdDUAp3I6IhCg4IhLljAp3LOIAAiUIFqAAAkAICIm5AAAAzAgIPBLJAi/CfIhBFjAhABbIN7KO");
	this.shape_131.setTransform(119.6,181.6);

	this.shape_132 = new cjs.Shape();
	this.shape_132.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],0.1,-7.9,0.1,7.5).s().p("Ai0BGIAAiSIFpAAIAACZg");
	this.shape_132.setTransform(74.4,246.5);

	this.shape_133 = new cjs.Shape();
	this.shape_133.graphics.lf(["#84576B","#470F21"],[0,0.914],-47.4,-10.6,42.2,29.4).s().p("AiYERIkfjTIBCliIMtJJg");
	this.shape_133.setTransform(137.8,226.9);

	this.shape_134 = new cjs.Shape();
	this.shape_134.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],-0.7,-21,4.9,19.6).s().p("AjWCxIhLlhIJDAAIhBFhg");
	this.shape_134.setTransform(71.3,215.3);

	this.shape_135 = new cjs.Shape();
	this.shape_135.graphics.f("#3F1826").s().p("AKbFHIstpJIpFAAIiQhEINUAAIN7KNg");
	this.shape_135.setTransform(115.1,223.5);

	this.shape_136 = new cjs.Shape();
	this.shape_136.graphics.lf(["#FFFFFF","#C7A0AF"],[0,0.914],-16.8,-12,18.3,29.2).s().p("AmoCCIAAhHIHRi8IGACaIAABpg");
	this.shape_136.setTransform(70.4,177.7);

	this.shape_137 = new cjs.Shape();
	this.shape_137.graphics.f("#CEADBA").s().p("AgEDNIgHgMIgKgsQgIhhgYhaIgHgPIAAgiIAOgYIAlgKIAFAHQAgA9AKA9IAWCNQAJA9g/AHgAgdiZIgRgKQgTgzA9gCQAKACAFANQATAdgiATg");
	this.shape_137.setTransform(77.3,141);

	this.shape_138 = new cjs.Shape();
	this.shape_138.graphics.f("#724255").s().p("AM7LpIt7qOIAAhnIBzAsIPBLJgAhDLWIjKicIlqAAIhCg4IG5AAIEdDUgAvzAGIB6gvIBmgpIC0hOIBTpIIByAAIBHI+IGgCjIgaAnIhzgsImBicInTC8IhLAigAnUoGIgPAYIAAAiIAIAPQAYBcAHBiIAKAsIAHAMIAMAMQBAgHgKg9IgWiNQgKhAgig8IgFgIgAnUo5IARAKIAYAAQAlgUgUgdQgFgMgMgDQg9ADAUAzg");
	this.shape_138.setTransform(119.6,181.6);

	this.shape_139 = new cjs.Shape();
	this.shape_139.graphics.f().s("rgba(0,0,0,0.302)").ss(1,1,1,3,true).p("EBOYgBdIApAAEhOsABaIgUAE");
	this.shape_139.setTransform(495.6,347.9);

	this.shape_140 = new cjs.Shape();
	this.shape_140.graphics.lf(["rgba(255,255,255,0)","#000000"],[0,1],-13.6,113.4,-18.4,-106).s().p("EhOhgBMUBOKgIBBO5AGpIAAFRIzbgeI25BCIjRAYIlwgJIq/h1IwHhQQiugNisAPIqxBHIllBJImGBwIrQhsIq1grIlwAJIrBBJIr8COg");
	this.shape_140.setTransform(494.5,321.1);

	this.shape_141 = new cjs.Shape();
	this.shape_141.graphics.f("rgba(0,0,0,0.302)").s().p("ASbMqIAA34QAvgiAsAiIAAX4QgbAPgXAAQgWAAgTgPgEArRAMZIAA34QArgiAvAiIAAX4QgcAPgXAAQgXAAgQgPgEhE/AMSIAA32QAtglArAlIAAX2QgaAOgWAAQgWAAgSgOgEBDoAMBIAA35QAsgiAsAiIAAX5QgbAOgWAAQgWAAgRgOgEgs3AL0IAA31QArglAvAlIAAX1QgcAPgXAAQgXAAgQgPgAzjLQIAA32QBEgkBCAkIAAX2QgqAQghAAQgiAAgZgQg");
	this.shape_141.setTransform(492.3,377.6);

	this.shape_142 = new cjs.Shape();
	this.shape_142.graphics.f("#FFFFFF").s().p("EhPVAPXIAA+tMCerAAAIAAetg");
	this.shape_142.setTransform(499.5,377);

	this.timeline.addTween(cjs.Tween.get({}).to({state:[{t:this.shape_142},{t:this.shape_141},{t:this.shape_140},{t:this.shape_139},{t:this.shape_138},{t:this.shape_137},{t:this.shape_136},{t:this.shape_135},{t:this.shape_134},{t:this.shape_133},{t:this.shape_132},{t:this.shape_131},{t:this.shape_130},{t:this.shape_129},{t:this.shape_128},{t:this.shape_127},{t:this.shape_126},{t:this.shape_125},{t:this.shape_124},{t:this.shape_123},{t:this.shape_122},{t:this.shape_121},{t:this.shape_120},{t:this.shape_119},{t:this.shape_118},{t:this.shape_117},{t:this.shape_116},{t:this.shape_115},{t:this.shape_114},{t:this.instance_14},{t:this.instance_13},{t:this.shape_113},{t:this.shape_112},{t:this.shape_111},{t:this.shape_110},{t:this.shape_109},{t:this.shape_108},{t:this.shape_107},{t:this.shape_106},{t:this.shape_105},{t:this.shape_104},{t:this.shape_103},{t:this.shape_102},{t:this.shape_101},{t:this.shape_100},{t:this.shape_99},{t:this.shape_98},{t:this.instance_12},{t:this.instance_11},{t:this.instance_10},{t:this.instance_9},{t:this.instance_8},{t:this.instance_7},{t:this.instance_6},{t:this.shape_97},{t:this.shape_96},{t:this.shape_95},{t:this.shape_94},{t:this.shape_93},{t:this.shape_92},{t:this.shape_91},{t:this.shape_90},{t:this.shape_89},{t:this.shape_88},{t:this.shape_87},{t:this.shape_86},{t:this.shape_85},{t:this.shape_84},{t:this.shape_83},{t:this.shape_82},{t:this.shape_81},{t:this.shape_80},{t:this.shape_79},{t:this.shape_78},{t:this.shape_77},{t:this.shape_76},{t:this.shape_75},{t:this.shape_74},{t:this.shape_73},{t:this.shape_72},{t:this.shape_71},{t:this.shape_70},{t:this.shape_69},{t:this.shape_68},{t:this.shape_67},{t:this.shape_66},{t:this.shape_65},{t:this.shape_64},{t:this.shape_63},{t:this.shape_62},{t:this.shape_61},{t:this.shape_60},{t:this.shape_59},{t:this.shape_58},{t:this.shape_57},{t:this.shape_56},{t:this.shape_55},{t:this.shape_54},{t:this.shape_53},{t:this.shape_52},{t:this.shape_51},{t:this.shape_50},{t:this.shape_49},{t:this.shape_48},{t:this.shape_47},{t:this.shape_46},{t:this.shape_45},{t:this.shape_44},{t:this.shape_43},{t:this.shape_42},{t:this.shape_41},{t:this.shape_40},{t:this.shape_39},{t:this.shape_38},{t:this.shape_37},{t:this.shape_36},{t:this.shape_35},{t:this.shape_34},{t:this.shape_33},{t:this.shape_32},{t:this.shape_31},{t:this.shape_30},{t:this.shape_29},{t:this.shape_28},{t:this.shape_27},{t:this.shape_26},{t:this.shape_25},{t:this.shape_24},{t:this.shape_23},{t:this.shape_22},{t:this.shape_21},{t:this.shape_20},{t:this.shape_19},{t:this.shape_18},{t:this.shape_17},{t:this.shape_16},{t:this.shape_15},{t:this.shape_14},{t:this.shape_13},{t:this.shape_12},{t:this.shape_11},{t:this.shape_10},{t:this.shape_9},{t:this.shape_8},{t:this.shape_7},{t:this.shape_6},{t:this.shape_5},{t:this.shape_4},{t:this.shape_3},{t:this.shape_2},{t:this.shape_1},{t:this.shape},{t:this.instance_5},{t:this.instance_4},{t:this.instance_3},{t:this.instance_2}]}).wait(92));

	// Layer 1
	this.instance_15 = new lib.sprite8();
	this.instance_15.setTransform(-10,-51.3,1.012,1);

	this.timeline.addTween(cjs.Tween.get(this.instance_15).wait(92));

}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(-313.9,-200.9,2627.8,1681.8);

})(lib = lib||{}, images = images||{}, createjs = createjs||{}, ss = ss||{});
var lib, images, createjs, ss;