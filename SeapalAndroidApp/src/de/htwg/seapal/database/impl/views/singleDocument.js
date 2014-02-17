function(doc) {
    if ((doc != null) && (doc.owner != null) && (doc.owner.length != null) && doc.owner.length > 0 && (doc._id != null)) {
      return emit(doc.owner + doc._id, doc);
    }
}