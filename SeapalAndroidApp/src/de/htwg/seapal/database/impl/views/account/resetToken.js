function(doc)
{
    var ptrn;
    ptrn = /^\s*-?[0-9]{1,10}\s*$/;
    if ((doc != null) && (doc.resetToken != null) && ptrn.exec(doc.resetToken && doc.resetToken !== 0)) {
        return emit(doc.resetToken, doc);
    }
}
