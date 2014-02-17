function(doc)
{
    if ((doc != null) && (doc.googleID != null) !== "" && doc.googleID.length > 0) {
      return emit(doc.googleID, doc);
    }
}