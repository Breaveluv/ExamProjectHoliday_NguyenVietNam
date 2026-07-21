/**
 * Football Club Management System
 * Global Application JavaScript
 * Version: 2.0
 */

'use strict';

/* ================================================================
   1. DOM READY
================================================================ */
document.addEventListener('DOMContentLoaded', function () {
    initSidebar();
    initTooltips();
    initDeleteModals();
    initAlertAutoDismiss();
    initSearchHighlight();
    initPasswordToggle();
    initFormValidation();
    initActiveNavLink();
});

/* ================================================================
   2. SIDEBAR TOGGLE (Mobile)
================================================================ */
function initSidebar() {
    const sidebar    = document.getElementById('sidebar');
    const toggleBtn  = document.getElementById('sidebar-toggle');
    const overlay    = document.getElementById('sidebar-overlay');

    if (!sidebar) return;

    // Open sidebar on toggle click
    if (toggleBtn) {
        toggleBtn.addEventListener('click', function () {
            sidebar.classList.toggle('sidebar-open');
            overlay && overlay.classList.toggle('active');
            document.body.style.overflow = sidebar.classList.contains('sidebar-open') ? 'hidden' : '';
        });
    }

    // Close sidebar when clicking overlay
    if (overlay) {
        overlay.addEventListener('click', closeSidebar);
    }

    // Close on ESC key
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') closeSidebar();
    });

    function closeSidebar() {
        sidebar.classList.remove('sidebar-open');
        overlay && overlay.classList.remove('active');
        document.body.style.overflow = '';
    }
}

/* ================================================================
   3. ACTIVE NAV LINK DETECTION
================================================================ */
function initActiveNavLink() {
    const navLinks = document.querySelectorAll('#sidebar .nav-link');
    const currentPath = window.location.pathname;

    navLinks.forEach(function (link) {
        const href = link.getAttribute('href');
        if (!href) return;

        // Match exact or sub-path (e.g. /players matches /players/new)
        const linkPath = href.split('?')[0];
        if (currentPath === linkPath || (linkPath !== '/' && currentPath.startsWith(linkPath))) {
            link.classList.add('active');
        }
    });
}

/* ================================================================
   4. DELETE CONFIRM MODALS
================================================================ */
function initDeleteModals() {
    // Handle delete buttons that use data-delete-url
    const deleteModal = document.getElementById('deleteConfirmModal');
    if (!deleteModal) return;

    const deleteForm = document.getElementById('deleteForm');

    // Attach listener for all delete trigger buttons
    document.querySelectorAll('[data-bs-target="#deleteConfirmModal"]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const deleteUrl  = this.dataset.deleteUrl;
            const entityName = this.dataset.entityName || 'this record';

            if (deleteForm) deleteForm.action = deleteUrl;

            const msgEl = document.getElementById('deleteItemName');
            if (msgEl) msgEl.textContent = entityName;
        });
    });
}

/* ================================================================
   5. ALERT AUTO-DISMISS
================================================================ */
function initAlertAutoDismiss() {
    const alerts = document.querySelectorAll('.alert[data-auto-dismiss]');
    alerts.forEach(function (alert) {
        const delay = parseInt(alert.dataset.autoDismiss) || 4000;
        setTimeout(function () {
            alert.style.transition = 'opacity .4s ease';
            alert.style.opacity = '0';
            setTimeout(function () { alert.remove(); }, 400);
        }, delay);
    });
}

/* ================================================================
   6. SEARCH HIGHLIGHT
================================================================ */
function initSearchHighlight() {
    const urlParams = new URLSearchParams(window.location.search);
    const keyword   = urlParams.get('keyword') || urlParams.get('search') || '';

    if (!keyword) return;

    const tableBody = document.querySelector('.table tbody');
    if (!tableBody) return;

    // Simple highlight: wrap matching text in <mark>
    tableBody.querySelectorAll('td').forEach(function (td) {
        const text = td.innerHTML;
        const regex = new RegExp('(' + escapeRegex(keyword) + ')', 'gi');
        td.innerHTML = text.replace(regex, '<mark style="background:#FEF9C3;padding:0 2px;border-radius:3px;">$1</mark>');
    });
}

function escapeRegex(str) {
    return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

/* ================================================================
   7. PASSWORD TOGGLE (Auth pages)
================================================================ */
function initPasswordToggle() {
    document.querySelectorAll('.password-toggle').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const wrapper = this.closest('.password-wrapper');
            const input   = wrapper ? wrapper.querySelector('input') : null;
            if (!input) return;

            const isPassword = input.type === 'password';
            input.type = isPassword ? 'text' : 'password';

            const icon = this.querySelector('i');
            if (icon) {
                icon.classList.toggle('bi-eye', !isPassword);
                icon.classList.toggle('bi-eye-slash', isPassword);
            }
        });
    });
}

/* ================================================================
   8. BOOTSTRAP TOOLTIPS
================================================================ */
function initTooltips() {
    const tooltipEls = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipEls.forEach(function (el) {
        new bootstrap.Tooltip(el, { trigger: 'hover' });
    });
}

/* ================================================================
   9. FORM VALIDATION STYLING
================================================================ */
function initFormValidation() {
    // Add Bootstrap 'is-invalid' class to fields with Thymeleaf errors
    document.querySelectorAll('.field-error').forEach(function (errorEl) {
        const fieldId = errorEl.dataset.field;
        if (!fieldId) return;
        const field = document.getElementById(fieldId);
        if (field) field.classList.add('is-invalid');
    });

    // Custom HTML5 validation feedback
    const forms = document.querySelectorAll('form.needs-validation');
    forms.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}

/* ================================================================
   10. RIPPLE EFFECT ON BUTTONS
================================================================ */
document.addEventListener('click', function (e) {
    const btn = e.target.closest('.btn');
    if (!btn) return;

    const ripple = document.createElement('span');
    const rect   = btn.getBoundingClientRect();
    const size   = Math.max(rect.width, rect.height);
    const x      = e.clientX - rect.left - size / 2;
    const y      = e.clientY - rect.top  - size / 2;

    ripple.style.cssText = `
        position:absolute; width:${size}px; height:${size}px;
        left:${x}px; top:${y}px;
        background:rgba(255,255,255,.3);
        border-radius:50%; transform:scale(0);
        animation:ripple .5s linear; pointer-events:none;
    `;

    if (getComputedStyle(btn).position === 'static') {
        btn.style.position = 'relative';
    }
    btn.style.overflow = 'hidden';
    btn.appendChild(ripple);

    setTimeout(function () { ripple.remove(); }, 500);
});

// Ripple keyframe (inject once)
if (!document.getElementById('ripple-style')) {
    const style = document.createElement('style');
    style.id = 'ripple-style';
    style.textContent = '@keyframes ripple { to { transform:scale(4); opacity:0; } }';
    document.head.appendChild(style);
}

/* ================================================================
   11. TABLE SORT (client-side, basic)
================================================================ */
document.querySelectorAll('.table thead th.sortable').forEach(function (th) {
    th.addEventListener('click', function () {
        const table   = th.closest('table');
        const tbody   = table.querySelector('tbody');
        const index   = Array.from(th.parentElement.children).indexOf(th);
        const isAsc   = th.classList.contains('sort-asc');

        // Reset all headers
        table.querySelectorAll('th.sortable').forEach(function (t) {
            t.classList.remove('sort-asc', 'sort-desc');
            const icon = t.querySelector('.sort-icon');
            if (icon) icon.className = 'sort-icon bi bi-arrow-down-up';
        });

        // Sort
        const rows = Array.from(tbody.querySelectorAll('tr'));
        rows.sort(function (a, b) {
            const aText = a.cells[index] ? a.cells[index].textContent.trim() : '';
            const bText = b.cells[index] ? b.cells[index].textContent.trim() : '';

            const aNum = parseFloat(aText.replace(/[^0-9.-]/g, ''));
            const bNum = parseFloat(bText.replace(/[^0-9.-]/g, ''));

            if (!isNaN(aNum) && !isNaN(bNum)) {
                return isAsc ? bNum - aNum : aNum - bNum;
            }
            return isAsc
                ? bText.localeCompare(aText)
                : aText.localeCompare(bText);
        });

        rows.forEach(function (row) { tbody.appendChild(row); });

        th.classList.add(isAsc ? 'sort-desc' : 'sort-asc');
        const icon = th.querySelector('.sort-icon');
        if (icon) {
            icon.className = isAsc
                ? 'sort-icon bi bi-arrow-up'
                : 'sort-icon bi bi-arrow-down';
        }
    });
});

/* ================================================================
   12. GLOBAL HELPERS
================================================================ */

/**
 * Show a toast notification (requires Bootstrap 5 Toast)
 * @param {string} message
 * @param {'success'|'danger'|'warning'|'info'} type
 */
function showToast(message, type = 'success') {
    const toastContainer = document.getElementById('toast-container');
    if (!toastContainer) return;

    const icons = {
        success: 'bi-check-circle-fill',
        danger:  'bi-x-circle-fill',
        warning: 'bi-exclamation-triangle-fill',
        info:    'bi-info-circle-fill'
    };

    const toastEl = document.createElement('div');
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    toastEl.setAttribute('role', 'alert');
    toastEl.setAttribute('aria-live', 'assertive');
    toastEl.innerHTML = `
        <div class="d-flex">
            <div class="toast-body d-flex align-items-center gap-2">
                <i class="bi ${icons[type] || ''}"></i>
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>`;

    toastContainer.appendChild(toastEl);
    const toast = new bootstrap.Toast(toastEl, { delay: 3500 });
    toast.show();
    toastEl.addEventListener('hidden.bs.toast', function () { toastEl.remove(); });
}

/* ================================================================
   13. NAVBAR SCROLL EFFECT
================================================================ */
window.addEventListener('scroll', function() {
    const navbar = document.querySelector('.custom-navbar');
    if (navbar) {
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    }
});

/* ================================================================
   14. SCROLL REVEAL ANIMATION
================================================================ */
function reveal() {
    var reveals = document.querySelectorAll(".reveal");
    for (var i = 0; i < reveals.length; i++) {
        var windowHeight = window.innerHeight;
        var elementTop = reveals[i].getBoundingClientRect().top;
        var elementVisible = 150;
        if (elementTop < windowHeight - elementVisible) {
            reveals[i].classList.add("active");
        }
    }
}
window.addEventListener("scroll", reveal);
reveal(); // Trigger once on load

/* ================================================================
   15. COUNTER ANIMATION
================================================================ */
document.addEventListener('DOMContentLoaded', () => {
    const counters = document.querySelectorAll('.counter-value');
    const speed = 200;

    const animateCounters = () => {
        counters.forEach(counter => {
            const updateCount = () => {
                const target = +counter.getAttribute('data-target');
                const count = +counter.innerText;
                const inc = target / speed;

                if (count < target) {
                    counter.innerText = Math.ceil(count + inc);
                    setTimeout(updateCount, 1);
                } else {
                    counter.innerText = target;
                }
            };
            
            // Only animate when visible
            const rect = counter.getBoundingClientRect();
            if(rect.top < window.innerHeight && counter.innerText == '0') {
                updateCount();
            }
        });
    }

    window.addEventListener('scroll', animateCounters);
    animateCounters();
});
