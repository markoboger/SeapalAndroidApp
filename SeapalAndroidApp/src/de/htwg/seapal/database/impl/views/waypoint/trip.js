function(doc) {
    if ((doc != null) && (doc.trip != null) && (doc.owner.length != null) && doc.owner.length > 0 && (doc.trip.length != null) && doc.trip.length > 0) {
      return emit("" + doc.owner + doc.trip, doc);
    }
}