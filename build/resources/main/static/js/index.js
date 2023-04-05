$(document).ready(function() {
    $.fn.letterDrop = function() {
        // Chainability
        return this.each(function() {
            var obj = $(this);

            var drop = {
                arr: obj.text().split(''),

                range: {
                    min: 1,
                    max: 9
                },

                styles: function() {
                    var dropDelays = '\n',
                        addCSS;
                    for (i = this.range.min; i <= this.range.max; i++) {
                        dropDelays += '.ld' + i + ' { animation-delay: 1.' + i + 's; }\n';
                    }
                    addCSS = $('<style>' + dropDelays + '</style>');
                    $('head').append(addCSS);
                },

                main: function() {
                    var dp = 0;
                    obj.text('');
                    var letterSpans = [];

                    $.each(this.arr, function(index, value) {
                        dp = dp.randomInt(drop.range.min, drop.range.max);
                        if (value === ' ') {
                            value = '&nbsp'; //Add spaces
                        }
                        var span = $('<span class="letterDrop ld' + dp + '">' + value + '</span>');
                        obj.append(span);
                        letterSpans.push(span);
                    });

                    var loginIntro = $('#login_intro');
                    var wrap = $('#wrap');
                    var successTimeout;
                    loginIntro.show();

                    // Hide loginIntro after 6 seconds
                    successTimeout = setTimeout(function() {
                        loginIntro.hide();
                    }, 5000);

                    // Hide wrap after 3 seconds
                    wrap.hide();
                    setTimeout(function() {
                        wrap.show();
                    }, 5000);


                    // Cancel timeout and hide loginIntro if user clicks on it
                    loginIntro.click(function() {
                        clearTimeout(successTimeout);
                        loginIntro.hide();
                    });
                }
            };

            Number.prototype.randomInt = function(min, max) {
                return Math.floor(Math.random() * (max - min + 1) + min);
            };

            // Create styles
            drop.styles();

            // Initialise
            drop.main();
        });
    };

    // USAGE
    $('#login_intro').letterDrop();

    //menu

});