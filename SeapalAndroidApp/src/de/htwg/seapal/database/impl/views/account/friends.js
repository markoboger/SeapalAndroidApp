function(doc)
{
    var friend, _i, _len, _ref, _results;
    _ref = doc.friendList;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      friend = _ref[_i];
      _results.push(emit(friend, doc._id));
    }
    return _results;
}