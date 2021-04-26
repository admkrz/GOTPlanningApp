function toggleClass(el, className)
{
    if (el.className.indexOf(className) < 0) {
        if(toggleClass.row != null){
            toggleClass.row.className = toggleClass.row.className.replace(className,"");
        }
        toggleClass.row = el;
        toggleClass.rowid = el.getAttribute("data-rowid")
        el.className += className;
    }
}

function getAllCheckedIds()
{
    var inputs = document.querySelectorAll('.chb');
    var ids = "";
    if(inputs.length > 0) {
        for (var i = 0; i < inputs.length - 1; i++) {
            if (inputs[i].checked) {
                ids += inputs[i].getAttribute("rowid");
                ids += ","
            }
        }
        if (inputs[inputs.length - 1].checked) {
            ids += inputs[inputs.length - 1].getAttribute("rowid");
            ids += ","
        }
    }
    return ids;
}

function toggleRoute(el, className)
{
    if (el.className.indexOf(className) < 0) {
        if(toggleRoute.row != null){
            toggleRoute.row.className = toggleRoute.row.className.replace(className,"");
        }
        toggleRoute.row = el;
        toggleRoute.rowid = el.getAttribute("data-rowid")
        toggleRoute.direction = el.getAttribute("data-direction")
        el.className += className;
        document.getElementById('segmentId').value = el.getAttribute("data-rowid");
        document.getElementById('direction').value = el.getAttribute("data-direction");
        document.getElementById('addSegment').disabled = false;
    }
}


