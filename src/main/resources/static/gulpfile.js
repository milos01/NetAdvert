var gulp = require('gulp')
	watch = require('gulp-watch');

//Gulp tasks
gulp.task('movetodest', function(){
	gulp.src('./**')
	.pipe(gulp.dest('/Users/macbookpro/Documents/netadvert/src/main/resources/static'));
});


gulp.task('watchFiles', function () {
    gulp.watch('./core/**', ['movetodest']);
    gulp.watch('./scripts/**', ['movetodest']);
});

gulp.task('default', ['movetodest', 'watchFiles']);