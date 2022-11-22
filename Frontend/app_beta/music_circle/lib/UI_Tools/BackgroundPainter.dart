import 'dart:math';
import 'dart:ui';

import 'package:flutter/animation.dart';
import 'package:flutter/rendering.dart';
import 'package:music_circle/Resources/Colors.dart';

class BackgroundPainter extends CustomPainter{
  final Paint bluePaint;
  final Paint greyPaint;
  final Paint orangePaint;

  final Animation<double> liquidAnim;
  final Animation<double> blueAnim;
  final Animation<double> greyAnim;
  final Animation<double> orangeAnim;

  BackgroundPainter({Animation<double> animation})
      : bluePaint = Paint()
        ..color = Pallet.lightBlue
        ..style = PaintingStyle.fill,
        greyPaint = Paint()
        ..color = Pallet.darkBlue
        ..style = PaintingStyle.fill,
        orangePaint = Paint()
        ..color = Pallet.orange
        ..style = PaintingStyle.fill,
        liquidAnim =CurvedAnimation(
            parent: animation,
            curve: Curves.elasticOut,
            reverseCurve: Curves.easeInCirc,
        ),
        orangeAnim = CurvedAnimation(
          parent: animation,
          curve: const Interval(
            0,
            0.7,
            curve: Interval(0, 0.8, curve: SpringCurve()),
          ),
          reverseCurve: Curves.linear,
        ),
        greyAnim = CurvedAnimation(
            parent: animation,
            curve: const Interval(
                0,
                0.8,
                curve: Interval(0, 0.9, curve: SpringCurve()),
            ),
            reverseCurve: Curves.easeInCirc,
        ),
        blueAnim = CurvedAnimation(
            parent: animation,
            curve: const SpringCurve(),
            reverseCurve: Curves.easeInCirc,
        ),
        super(repaint: animation);

  
  @override
  void paint(Canvas canvas, Size size) {
    // TODO: implement paint
    print('painting');
    paintBlue(canvas, size);
    paintGrey(canvas, size);
    paintOrange(canvas, size);
    //canvas.drawPaint(Paint()..color = Pallet.darkBlue);
  }
  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
    // TODO: implement shouldRepaint
    throw UnimplementedError();
  }

  void paintBlue(Canvas canvas, Size size){
    final path = Path();
    path.moveTo(size.width, size.height / 2);
    path.lineTo(size.width, 0);
    path.lineTo(0, 0);
    path.lineTo(
      0,
      lerpDouble(0, size.height, blueAnim.value),
    );
    _addPointsToPath(path, [
      Point(
        lerpDouble(0, size.width/3, blueAnim.value),
        lerpDouble(0, size.height, blueAnim.value),
      ),
      Point(
          lerpDouble(size.width/2, size.width/4 * 3, liquidAnim.value),
        lerpDouble(size.height/2, size.height/4 * 3, liquidAnim.value),
      ),
      Point(
          size.width,
          lerpDouble(size.height/2, size.height * 3 / 4, liquidAnim.value)
      )
    ]);
    path.close();
    canvas.drawPath(path, bluePaint);
  }

  void paintGrey(Canvas canvas, Size size){
    final path = Path();
    path.moveTo(size.width, 300);
    path.lineTo(size.width, 0);
    path.lineTo(0, 0);
    path.lineTo(
      0,
      lerpDouble(size.height/4, size.height/2, greyAnim.value),
    );
    _addPointsToPath(path, [
      Point(
        size.width/4,
        lerpDouble(size.height/2, size.height * 3/4, liquidAnim.value),
      ),
      Point(
      size.width * 3/5,
        lerpDouble(size.height/4, size.height/2, liquidAnim.value),
      ),
      Point(
        size.width * 4/5,
        lerpDouble(size.height/6, size.height/3, greyAnim.value),
      ),
      Point(
          size.width,
          lerpDouble(size.height/5, size.height/4, greyAnim.value)
      )
    ]);
    path.close();
    canvas.drawPath(path, greyPaint);
  }

  void paintOrange(Canvas canvas, Size size){
    if(orangeAnim.value > 0){
      final path = Path();
      path.moveTo(size.width * 3/4, 0);
      path.lineTo(0, 0);
      path.lineTo(
        0,
        lerpDouble(0, size.height/12, orangeAnim.value),
      );
      _addPointsToPath(path, [
        Point(
          size.width/7,
          lerpDouble(0, size.height/6, orangeAnim.value),
        ),
        Point(
          size.width/3,
          lerpDouble(0, size.height/10, liquidAnim.value),
        ),
        Point(
            size.width/3 * 2,
            lerpDouble(0, size.height/8, liquidAnim.value),
        ),
        Point(
          size.width * 3/4,
          0,
        )
      ]);
      path.close();
      canvas.drawPath(path, orangePaint);
    }

  }

  void _addPointsToPath(Path path, List<Point> points){
    if(points.length < 3){
      throw UnsupportedError('Need 3 or more points to create a path');
    }

    for(var i=0; i<points.length - 2; i++){
      final xc = (points[i].x + points[i+1].x) / 2;
      final yc = (points[i].y + points[i+1].y) / 2;
      path.quadraticBezierTo(points[i].x, points[i].y, xc, yc);
    }

    path.quadraticBezierTo(
        points[points.length - 2].x,
        points[points.length - 2].y,
        points[points.length - 1].x,
        points[points.length - 1].y);
  }
}

class Point{
  final double x;
  final double y;
  Point(this.x, this.y);
}

class SpringCurve extends Curve{
  final double a, w;
  const SpringCurve({
    this.a = 0.15,
    this.w = 19.4,
});

  @override
  double transformInternal(double t) {
    return (-(pow(e, -t / a) * cos(t * w)) + 1).toDouble();
  }
}