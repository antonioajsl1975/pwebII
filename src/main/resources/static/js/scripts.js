function toggleFields() {
    var tipoPessoa = document.getElementById('tipoPessoa').value;
    var pfFields = document.getElementById('pfFields');
    var pjFields = document.getElementById('pjFields');

    if (tipoPessoa === 'FISICA') {
        pfFields.style.display = 'block';
        pjFields.style.display = 'none';
    } else if (tipoPessoa === 'JURIDICA') {
        pfFields.style.display = 'none';
        pjFields.style.display = 'block';
    } else {
        pfFields.style.display = 'none';
        pjFields.style.display = 'none';
    }
}

window.onload = function() {
    toggleFields();
}