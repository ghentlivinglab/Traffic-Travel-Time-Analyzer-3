use URI;
use Web::Scraper;
use Encode;

# Scraper blok aanmaken
my $route = scraper {
	# In welk blok gekeken moet worden
	process ".map-controls", "routes[]" => scraper {
		# Haal info op van elementen uit het blok
		process "option", name => 'TEXT';
	};
};

# https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13&from_lat=51.03687&from_lon=3.7101&to_lat=51.03109&to_lon=3.70653&at_req=0&at_text=Now
my $uri = "https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13" .
				"&from_lat=51.03687" .
				"&from_lon=3.7101" .
				"&to_lat=51.03109" .
				"&to_lon=3.70653" .
				"&at_req=0&at_text=Now";

my $res = $route->scrape( URI->new($uri) );

# Overloop de routes
for my $route (@{$res->{routes}}) {
	my $test = "nothing" unless $route->{name};
	print Encode::encode("utf8", "$test\n");
}