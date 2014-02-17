function(doc) {
    if ((doc != null) && (doc.owner != null) && (doc.owner.length != null) && doc.owner.length > 0) {
      return emit(doc.owner, doc);
    }
}
