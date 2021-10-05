library range;

import "dart:collection";

class Range extends IterableBase<int> {
  factory Range(int start, [int? stop, int step = 1]) {
    if (stop == null) {
      // reverse stop and start making start 0
      stop = start;
      start = 0;
    }
    if (step == 0) {
      throw ArgumentError("step must not be 0");
    }

    return Range._(start, stop, step);
  }

  Range._(this.start, this.stop, this.step);

  @override
  Iterator<int> get iterator => RangeIterator(start, stop, step);

  @override
  int get length {
    if ((step > 0 && start > stop) || (step < 0 && start < stop)) {
      return 0;
    }
    return ((stop - start) / step).ceil();
  }

  @override
  bool get isEmpty => length == 0;

  @override
  int get hashCode {
    int result = 17;
    result = 37 * result + start.hashCode;
    result = 37 * result + stop.hashCode;
    result = 37 * result + step.hashCode;
    return result;
  }

  @override
  String toString() =>
      step == 1 ? "Range($start, $stop)" : "Range($start, $stop, $step)";

  bool operator ==(other) =>
      other is Range &&
      start == other.start &&
      stop == other.stop &&
      step == other.step;

  final int start;
  final int stop;
  final int step;
}

class RangeIterator implements Iterator<int> {
  int _pos;
  final int _stop;
  final int _step;

  RangeIterator(int pos, int stop, int step)
      : _stop = stop,
        _pos = pos - step,
        _step = step;

  @override
  int get current => _pos;

  @override
  bool moveNext() {
    if (_step > 0 ? _pos + _step > _stop - 1 : _pos + _step < _stop + 1) {
      return false;
    }
    _pos += _step;
    return true;
  }
}

Range range(int startInclusive, [int? stopExclusive, int step = 1]) =>
    Range(startInclusive, stopExclusive, step);

Range indices(lengthable) => Range(0, lengthable.length);
