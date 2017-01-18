								var map;
								var marker;

								function initMap() {
									map = new google.maps.Map(document
											.getElementById('map'), {
										center : {
											lat : -34.397,
											lng : 150.644
										},
										zoom : 16
									});
									var geocoder = new google.maps.Geocoder();
									geocodeAddress(geocoder, map);

								}
								function geocodeAddress(geocoder, resultsMap) {
									var address = "advertt.realestate.location.street" + ","
											+ "advertt.realestate.location.city" + ",Srbija";
									alert(advertt1.realestate.location.street);
									console.log(advertt.realestate.location.street);
									geocoder
											.geocode(
													{
														'address' : address
													},
													function(results, status) {
														if (status === google.maps.GeocoderStatus.OK) {
															resultsMap
																	.setCenter(results[0].geometry.location);
															var marker = new google.maps.Marker(
																	{
																		map : resultsMap,
																		position : results[0].geometry.location,
																		title : '{{advertt.realestate.location.street}}'

																	});
														} else {
															alert('Geocode was not successful for the following reason: '
																	+ status);
														}
													});
								}