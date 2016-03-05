use URI;
use Web::Scraper;
use Encode;

# First, create your scraper block
my $route = scraper {
	# Parse all TDs inside 'table[width="100%]"', store them into
	# an array 'routes'.  We embed other scrapers for each TD.
	process '.waze-header .waze-header-menu', "routes[]" => scraper {
		# And, in each TD,
		# get the URI of "a" element
		process "a", name => 'TEXT';
	};
};

# https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13&from_lat=51.03687&from_lon=3.7101&to_lat=51.03109&to_lon=3.70653&at_req=0&at_text=Now
my $uri = "https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13" .
				"&from_lat=51.03687" .
				"&from_lon=3.7101" .
				"&to_lat=51.03109" .
				"&to_lon=3.70653" .
				"&at_req=0&at_text=Now";

my $res = $route->scrape( URI->new("https://www.waze.com/livemap") );

# iterate the array 'authors'
for my $route (@{$res->{routes}}) {
	# output is like:
	# Andy Adler      http://search.cpan.org/~aadler/
	# Aaron K Dancygier       http://search.cpan.org/~aakd/
	# Aamer Akhter    http://search.cpan.org/~aakhter/
	print Encode::encode("utf8", "$route->{name}\n");
}