/**
 * form autofill (jQuery plugin)
 * Version: 0.1
 * Released: 2011-11-30
 * 
 * Copyright (c) 2011 Creative Area
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Require jQuery
 * http://jquery.com/
 * Copyright 2011, John Resig
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 */
(function($){
	$.fn.extend({
		autofill: function(data, options) {
			var settings = {
					findbyname: true,
					restrict: true
				},
				self = this;
				
			if ( options ) {
				$.extend( settings, options );
			}
			
			return this.each(function() {
				$.each( data, function(k, v) {
					
					// switch case findbyname / findbyid
					
					var selector, elt;
					
					if ( settings.findbyname ) { // by name
						
						selector = '[name="'+k+'"]';
						if(k=="dietTaste"){
							var a = k;
						}
						elt = ( settings.restrict ) ? self.find( selector ) : $( selector );
						
						if ( elt.length == 1 ) {
							elt.val( ( elt.attr("type") == "checkbox" ) ? [v] : v );
						} else if ( elt.length > 1 ) {
							if(v!=null && v!="" && typeof(v)!="undefined" && typeof(v)!="number"){
								var vt = v.split(",");
								if(vt.length>1){
									for(var i in vt){
										elt.each(function(){
											if($(this).val() == vt[i]){
												this.checked = true;
											}
										});
									}
								}else{
									// radio
									elt.val([v]);
								}
							}else{
								elt.val([v]);
							}
						} else {
							selector = '[name="'+k+'[]"]';
							elt = ( settings.restrict ) ? self.find( selector ) : $( selector );
							elt.each(function(){
								$(this).val(v);
							});
						}
						
					} else { // by id
						
						selector = '#'+k;
						elt = ( settings.restrict ) ? self.find( selector ) : $( selector );
						
						if ( elt.length == 1 ) {
							elt.val( ( elt.attr("type") == "checkbox" ) ? [v] : v );
						} else {
							var radiofound = false;
							
							// radio
							elt = ( settings.restrict ) ? self.find( 'input:radio[name="'+k+'"]' ) : $( 'input:radio[name="'+k+'"]' );
							elt.each(function(){
								radiofound = true;
								if ( this.value == v ) { this.checked = true; }
							});
							// multi checkbox
							if ( !radiofound ) {
								elt = ( settings.restrict ) ? self.find( 'input:checkbox[name="'+k+'[]"]' ) : $( 'input:checkbox[name="'+k+'[]"]' );
								elt.each(function(){
									$(this).val(v);
								});
							}
						}
					}
				});
			});
		}
	});
})(jQuery);