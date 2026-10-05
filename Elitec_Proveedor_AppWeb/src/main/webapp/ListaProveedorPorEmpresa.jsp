<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Proveedores</title>
    <script src="js/bootstrap.bundle.js" type="text/javascript"></script>
    <script src="js/jquery-4.0.0.min.js" type="text/javascript"></script>
    <script src="js/datatables.js" type="text/javascript"></script>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/datatables.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Lista de Proveedores por Empresa</h1>
    <div class="row" style="margin-top: 2%;">
        <div class="col-3"><label for="empresa">Empresa</label></div>
        <div class="col-6">
            <input type="text" class="form-control" id="empresa" name="empresa"
                   placeholder="Ingrese la empresa" maxlength="200">
        </div>
        <div class="col-3">
            <button class="btn btn-primary" id="btnBuscar" type="button" style="width: 200px">Buscar</button>
        </div>
    </div>
    <div class="row" style="margin-top: 2%;">
        <div class="col-12">
            <table class="table table-striped" id="id_table">
                <thead><tr>
                    <th>Código</th><th>RUC</th><th>Empresa</th><th>Contacto</th>
                    <th>Teléfono</th><th>Correo</th><th>Dirección</th><th>Categoría</th><th>Estado</th>
                </tr></thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
const IDIOMA = {
    processing: "Procesando...", lengthMenu: "_MENU_ Registros por página",
    zeroRecords: "No existen registros", info: "Página _PAGE_ de _PAGES_",
    infoEmpty: "Sin registros", infoFiltered: "(Filtro de _MAX_ registros)",
    search: "Buscar:", paginate: {first: "Primero", last: "Último", next: "Siguiente", previous: "Anterior"}
};
function agregarGrilla(lista) {
    if ($.fn.DataTable.isDataTable('#id_table')) {
        $('#id_table').DataTable().clear().destroy();
    }
    $('#id_table').DataTable({
        data: lista, language: IDIOMA, searching: true, ordering: true,
        processing: true, pageLength: 10, lengthChange: true, info: true, scrollX: true,
        columns: [
            {data: "idProveedor_", className: "text-center"},
            {data: "ruc", className: "text-center"},
            {data: "empresa", className: "text-center"},
            {data: "contacto", className: "text-center"},
            {data: "telefono", className: "text-center"},
            {data: "correo", className: "text-center"},
            {data: "direccion", className: "text-center"},
            {data: "categoriaDescripcion", className: "text-center"},
            {data: function(row) { return row.estado === 1 ? "Activo" : "Inactivo"; }, className: "text-center"}
        ]
    });
}
$('#btnBuscar').on('click', function() {
    $.ajax({
        url: 'listaProveedorPorEmpresa', type: 'GET',
        data: {empresa: $('#empresa').val()}, dataType: 'json',
        success: agregarGrilla,
        error: function() { alert('Error al buscar proveedores por empresa.'); }
    });
});
</script>
</body>
</html>
