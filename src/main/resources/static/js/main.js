document.addEventListener('DOMContentLoaded', function () {
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(function () {
                alert.remove();
            }, 500);
        }, 3000);
    });

    var deleteButtons = document.querySelectorAll('.btn-danger');
    deleteButtons.forEach(function (btn) {
        if (!btn.getAttribute('onclick')) {
            btn.addEventListener('click', function (e) {
                if (!confirm('确认执行此操作？')) {
                    e.preventDefault();
                }
            });
        }
    });

    var datetimeInputs = document.querySelectorAll('input[type="datetime-local"]');
    datetimeInputs.forEach(function (input) {
        if (!input.value) {
            var now = new Date();
            var offset = now.getTimezoneOffset();
            var local = new Date(now.getTime() - offset * 60000);
            input.value = local.toISOString().slice(0, 16);
        }
    });
});
