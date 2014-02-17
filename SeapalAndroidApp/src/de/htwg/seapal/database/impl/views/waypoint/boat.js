function(doc) {
    if ((doc != null) && (doc.boat != null) && (doc.owner.length != null) && doc.owner.length > 0 && (doc.boat.length != null) && doc.boat.length > 0) {
      return emit("" + doc.owner + doc.boat, doc);
    }
}
