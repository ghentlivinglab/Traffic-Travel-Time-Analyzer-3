use URI;
use Web::Scraper;
use WWW::Selenium;
use Encode;

# https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13&from_lat=51.03687&from_lon=3.7101&to_lat=51.03109&to_lon=3.70653&at_req=0&at_text=Now
my $uri = "https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13" .
				"&from_lat=51.03687" .
				"&from_lon=3.7101" .
				"&to_lat=51.03109" .
				"&to_lon=3.70653" .
				"&at_req=0&at_text=Now";

my $sel = WWW::Selenium->new( host => "192.168.0.100",
								port => 5555,
								browser => "*firefox",
								browser_url => URI->new($uri)
							);

$sel->start;
$sel->open(URI->new($uri));
$sel->wait_for_page_to_load(3000);

# Scraper blok aanmaken
my $route = scraper {
	# In welk blok gekeken moet worden
	process ".active", "routes[]" => scraper {
		# Haal info op van elementen uit het blok
		process "a", name => 'TEXT';
	};
};

# Route scrapen
my $res = $route->scrape( $sel->get_html_source() );

$sel->stop;

# Overloop de routes
for my $route (@{$res->{routes}}) {
	my $test = "nothing" unless $route->{name};
	print Encode::encode("utf8", "$test\n");
}