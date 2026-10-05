<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Proveedor</title>
    <script src="js/bootstrap.bundle.js" type="text/javascript"></script>
    <script src="js/jquery-4.0.0.min.js" type="text/javascript"></script>
    <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Registro de Proveedor</h1>
    <form id="formProveedor" method="post" novalidate>
        <div class="row" style="margin-top: 2%;">
            <div class="col-4">
                <label for="ruc">RUC</label>
                <input type="text" class="form-control" id="ruc" name="ruc" maxlength="11" pattern="[0-9]{11}" required>
                <div class="invalid-feedback">Ingrese un RUC válido de 11 dígitos</div>
            </div>
            <div class="col-8">
                <label for="empresa">Empresa</label>
                <input type="text" class="form-control" id="empresa" name="empresa" maxlength="200" required>
                <div class="invalid-feedback">Ingrese la empresa</div>
            </div>
        </div>
        <div class="row" style="margin-top: 2%;">
            <div class="col-4">
                <label for="contacto">Contacto</label>
                <input type="text" class="form-control" id="contacto" name="contacto" maxlength="200" required>
                <div class="invalid-feedback">Ingrese el contacto</div>
            </div>
            <div class="col-4">
                <label for="telefono">Teléfono</label>
                <input type="text" class="form-control" id="telefono" name="telefono" maxlength="200" required>
                <div class="invalid-feedback">Ingrese el teléfono</div>
            </div>
            <div class="col-4">
                <label for="correo">Correo</label>
                <input type="email" class="form-control" id="correo" name="correo" maxlength="200" required>
                <div class="invalid-feedback">Ingrese un correo válido</div>
            </div>
        </div>
        <div class="row" style="margin-top: 2%;">
            <div class="col-6">
                <label for="direccion">Dirección</label>
                <input type="text" class="form-control" id="direccion" name="direccion" maxlength="200" required>
                <div class="invalid-feedback">Ingrese la dirección</div>
            </div>
            <div class="col-3">
                <label for="idCategoria_">Categoría</label>
                <select class="form-control" id="idCategoria_" name="idCategoria_" required>
                    <option value="">Cargando categorías...</option>
                </select>
                <div class="invalid-feedback">Seleccione una categoría</div>
            </div>
            <div class="col-3">
                <label for="estado">Estado</label>
                <select class="form-control" id="estado" name="estado" required>
                    <option value="">Seleccione</option>
                    <option value="1">Activo</option>
                    <option value="0">Inactivo</option>
                </select>
                <div class="invalid-feedback">Seleccione el estado</div>
            </div>
        </div>
        <div class="row justify-content-center" style="margin-top: 2%">
            <button class="btn btn-primary" id="btnRegistrar" type="submit" style="width: 200px">Registrar</button>
        </div>
    </form>
    <div id="mensaje" style="margin-top: 1%;"></div>
</div>
<script type="text/javascript">
$(function() {
    $.ajax({
        url: 'cargaComboCategoriaAlias',
        type: 'GET',
        dataType: 'json',
        success: function(lista) {
            const combo = $('#idCategoria_');
            combo.empty().append($('<option>', {value: '', text: 'Seleccione'}));
            $.each(lista, function(_, categoria) {
                combo.append($('<option>', {
                    value: categoria.idCategoria_,
                    text: categoria.descripcion
                }));
            });
        },
        error: function() {
            $('#idCategoria_').empty().append($('<option>', {
                value: '', text: 'No se pudieron cargar las categorías'
            }));
        }
    });

    $('#formProveedor').on('submit', function(e) {
        e.preventDefault();
        const form = this;
        if (!form.checkValidity()) {
            $(form).addClass('was-validated');
            return;
        }
        $('#mensaje').empty();
        $.ajax({
            url: 'registraProveedor',
            type: 'POST',
            data: $(form).serialize(),
            dataType: 'json',
            success: function(response) {
                form.reset();
                $(form).removeClass('was-validated');
                $('#mensaje').html($('<div>', {
                    class: 'alert alert-success', role: 'alert', text: response.mensajeSalida
                }));
            },
            error: function(xhr) {
                let mensaje = 'Error al registrar el proveedor';
                if (xhr.responseJSON && xhr.responseJSON.mensajeSalida) {
                    mensaje = xhr.responseJSON.mensajeSalida;
                }
                $('#mensaje').html($('<div>', {
                    class: 'alert alert-danger', role: 'alert', text: mensaje
                }));
            }
        });
    });
});
</script>
</body>
</html>
