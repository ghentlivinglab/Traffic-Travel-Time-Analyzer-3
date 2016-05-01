window.addEventListener('load',setup,false);

function setup()
{
	console.log("setup ready");
	document.getElementById('convert').addEventListener('click',mercator,false);
}

function mercator(){
	console.log("mercator ready");
	var lat, lon, z, latRad, n, xTile, yTile;
	
	lat = document.getElementById('Xas').value;
	lon = document.getElementById('Yas').value;
	z = document.getElementById('zoom').value;
	
latRad = lat * Math.PI / 180;
n = Math.pow(2, z);
console.log(Math.pow(2, z)*((lon+180)/360));
xTile = n * ((lon + 180) / 360);
yTile = n * (1-(Math.log(Math.tan(latRad) + 1/Math.cos(latRad)) / Math.PI)) / 2;
		
	
	console.log("lat_rad=" + latRad + " en n="+ n +"xtile=" + xTile + " en ytile=" + yTile);
	//tileXYToQuadKey(xtile,ytile, z);
	
	
document.getElementById('oplossing').setAttribute('href',"https://traffic.cit.api.here.com/traffic/6.0/incidents/json/"
+z+"/"+xTile+"/"+yTile
+"?app_id=tsliJF6nV8gV1CCk7yK8"
+"&app_code=o8KURFHJC02Zzlv8HTifkg");

}


function tileXYToQuadKey(xtile, ytile, zoom) {
	console.log("quadkey ready");
	var x = xtile;
	var y = ytile;
	var z = zoom;		
	
  var quadKey = [];
    for (var i = z; i > 0; i--) {
        var digit = '0';
        var mask = 1 << (i - 1);
        if ((x & mask) != 0) {
            digit++;
        }
        if ((y & mask) != 0) {
            digit++;
            digit++;
        }
        quadKey.push(digit);
    }
    //return quadKey.join(''); 

document.getElementById('oplossing').setAttribute('href',"https://traffic.cit.api.here.com/traffic/6.0/incidents.json"
+"?app_id=tsliJF6nV8gV1CCk7yK8"
+"&app_code=o8KURFHJC02Zzlv8HTifkg"
+"&quadKey="+quadKey.join(''));
}