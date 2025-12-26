/**
 * ============================================
 * FLASHCARD APP - UTILITY FUNCTIONS
 * ============================================
 */

const Utils = {
    /**
     * Show toast notification
     */
    showToast(message, type = 'info') {
        // Create toast container if not exists
        let container = document.getElementById('toast-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toast-container';
            container.className = 'toast-container';
            document.body.appendChild(container);
        }

        // Create toast element
        const toast = document.createElement('div');
        toast.className = `toast-custom ${type}`;
        
        const icons = {
            success: 'fas fa-check-circle',
            error: 'fas fa-times-circle',
            warning: 'fas fa-exclamation-triangle',
            info: 'fas fa-info-circle'
        };

        toast.innerHTML = `
            <i class="${icons[type] || icons.info}" style="color: var(--${type === 'error' ? 'danger' : type}-color)"></i>
            <span>${message}</span>
            <button type="button" class="btn-close btn-sm ms-auto" onclick="this.parentElement.remove()"></button>
        `;

        container.appendChild(toast);

        // Auto remove after duration
        setTimeout(() => {
            toast.style.animation = 'slideOut 0.3s ease forwards';
            setTimeout(() => toast.remove(), 300);
        }, CONFIG.TOAST_DURATION);
    },

    /**
     * Show success toast
     */
    success(message) {
        this.showToast(message, 'success');
    },

    /**
     * Show error toast
     */
    error(message) {
        this.showToast(message, 'error');
    },

    /**
     * Show warning toast
     */
    warning(message) {
        this.showToast(message, 'warning');
    },

    /**
     * Show info toast
     */
    info(message) {
        this.showToast(message, 'info');
    },

    /**
     * Show loading spinner
     */
    showLoading(container) {
        if (typeof container === 'string') {
            container = document.querySelector(container);
        }
        if (container) {
            container.innerHTML = `
                <div class="loading-spinner">
                    <div class="spinner"></div>
                </div>
            `;
        }
    },

    /**
     * Hide loading spinner
     */
    hideLoading(container) {
        if (typeof container === 'string') {
            container = document.querySelector(container);
        }
        const spinner = container?.querySelector('.loading-spinner');
        if (spinner) {
            spinner.remove();
        }
    },

    /**
     * Show empty state
     */
    showEmptyState(container, icon, title, message, actionText = '', actionHandler = null) {
        if (typeof container === 'string') {
            container = document.querySelector(container);
        }
        if (container) {
            container.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">
                        <i class="${icon}"></i>
                    </div>
                    <h3>${title}</h3>
                    <p>${message}</p>
                    ${actionText ? `<button class="btn btn-primary-custom" id="empty-action-btn">${actionText}</button>` : ''}
                </div>
            `;
            
            if (actionHandler) {
                const btn = container.querySelector('#empty-action-btn');
                if (btn) {
                    btn.addEventListener('click', actionHandler);
                }
            }
        }
    },

    /**
     * Format date
     */
    formatDate(dateString, format = 'short') {
        const date = new Date(dateString);
        const options = {
            short: { day: '2-digit', month: '2-digit', year: 'numeric' },
            long: { day: '2-digit', month: 'long', year: 'numeric' },
            datetime: { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' }
        };
        return date.toLocaleDateString('vi-VN', options[format] || options.short);
    },

    /**
     * Format relative time (e.g., "2 hours ago")
     */
    formatRelativeTime(dateString) {
        const date = new Date(dateString);
        const now = new Date();
        const diff = now - date;
        
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);
        const weeks = Math.floor(days / 7);
        const months = Math.floor(days / 30);
        
        if (months > 0) return `${months} tháng trước`;
        if (weeks > 0) return `${weeks} tuần trước`;
        if (days > 0) return `${days} ngày trước`;
        if (hours > 0) return `${hours} giờ trước`;
        if (minutes > 0) return `${minutes} phút trước`;
        return 'Vừa xong';
    },

    /**
     * Format number with thousand separators
     */
    formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    },

    /**
     * Get URL parameters
     */
    getUrlParams() {
        const params = {};
        const searchParams = new URLSearchParams(window.location.search);
        for (const [key, value] of searchParams) {
            params[key] = value;
        }
        return params;
    },

    /**
     * Get single URL parameter
     */
    getUrlParam(name) {
        const searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(name);
    },

    /**
     * Set URL parameter
     */
    setUrlParam(name, value) {
        const url = new URL(window.location);
        url.searchParams.set(name, value);
        window.history.pushState({}, '', url);
    },

    /**
     * Debounce function
     */
    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    /**
     * Throttle function
     */
    throttle(func, limit) {
        let inThrottle;
        return function(...args) {
            if (!inThrottle) {
                func.apply(this, args);
                inThrottle = true;
                setTimeout(() => inThrottle = false, limit);
            }
        };
    },

    /**
     * Escape HTML to prevent XSS
     */
    escapeHtml(text) {
        const map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return text.replace(/[&<>"']/g, m => map[m]);
    },

    /**
     * Truncate text with ellipsis
     */
    truncate(text, length = 100) {
        if (text.length <= length) return text;
        return text.substring(0, length) + '...';
    },

    /**
     * Get user initials from name
     */
    getInitials(name) {
        return name
            .split(' ')
            .map(word => word[0])
            .join('')
            .toUpperCase()
            .substring(0, 2);
    },

    /**
     * Generate random color
     */
    getRandomColor() {
        const colors = [
            '#4f46e5', '#7c3aed', '#ec4899', '#f43f5e',
            '#f97316', '#eab308', '#22c55e', '#14b8a6',
            '#06b6d4', '#3b82f6', '#6366f1', '#8b5cf6'
        ];
        return colors[Math.floor(Math.random() * colors.length)];
    },

    /**
     * Shuffle array (Fisher-Yates algorithm)
     */
    shuffleArray(array) {
        const shuffled = [...array];
        for (let i = shuffled.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
        }
        return shuffled;
    },

    /**
     * Validate email format
     */
    isValidEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    },

    /**
     * Validate password strength
     */
    validatePassword(password) {
        const errors = [];
        if (password.length < 6) {
            errors.push('Mật khẩu phải có ít nhất 6 ký tự');
        }
        return { valid: errors.length === 0, errors };
    },

    /**
     * Get current user from localStorage
     */
    getCurrentUser() {
        const userStr = localStorage.getItem(CONFIG.STORAGE.USER);
        return userStr ? JSON.parse(userStr) : null;
    },

    /**
     * Check if user is authenticated, redirect if not
     */
    requireAuth() {
        if (!API.isLoggedIn()) {
            window.location.href = '/pages/login.html';
            return false;
        }
        return true;
    },

    /**
     * Redirect if already authenticated
     */
    redirectIfAuth() {
        if (API.isLoggedIn()) {
            window.location.href = '/pages/dashboard.html';
            return true;
        }
        return false;
    },

    /**
     * Render pagination
     */
    renderPagination(container, currentPage, totalPages, onPageChange) {
        if (typeof container === 'string') {
            container = document.querySelector(container);
        }
        if (!container || totalPages <= 1) {
            if (container) container.innerHTML = '';
            return;
        }

        let html = '<nav><ul class="pagination justify-content-center">';
        
        // Previous button
        html += `
            <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
        `;

        // Page numbers
        const startPage = Math.max(0, currentPage - 2);
        const endPage = Math.min(totalPages - 1, currentPage + 2);

        if (startPage > 0) {
            html += `<li class="page-item"><a class="page-link" href="#" data-page="0">1</a></li>`;
            if (startPage > 1) {
                html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            html += `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `;
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
            }
            html += `<li class="page-item"><a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a></li>`;
        }

        // Next button
        html += `
            <li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        `;

        html += '</ul></nav>';
        container.innerHTML = html;

        // Add click handlers
        container.querySelectorAll('.page-link[data-page]').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const page = parseInt(link.dataset.page);
                if (page >= 0 && page < totalPages && page !== currentPage) {
                    onPageChange(page);
                }
            });
        });
    },

    /**
     * Confirm dialog
     */
    confirm(message, title = 'Xác nhận') {
        return new Promise((resolve) => {
            // Create modal
            const modalId = 'confirm-modal-' + Date.now();
            const modalHtml = `
                <div class="modal fade" id="${modalId}" tabindex="-1">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content modal-custom">
                            <div class="modal-header">
                                <h5 class="modal-title">${title}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <p class="mb-0">${message}</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
                                <button type="button" class="btn btn-primary-custom" id="${modalId}-confirm">Xác nhận</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            
            document.body.insertAdjacentHTML('beforeend', modalHtml);
            const modalEl = document.getElementById(modalId);
            const modal = new bootstrap.Modal(modalEl);
            
            modalEl.querySelector(`#${modalId}-confirm`).addEventListener('click', () => {
                modal.hide();
                resolve(true);
            });
            
            modalEl.addEventListener('hidden.bs.modal', () => {
                modalEl.remove();
                resolve(false);
            });
            
            modal.show();
        });
    }
};

// Add slideOut animation
const style = document.createElement('style');
style.textContent = `
    @keyframes slideOut {
        from {
            opacity: 1;
            transform: translateX(0);
        }
        to {
            opacity: 0;
            transform: translateX(100%);
        }
    }
`;
document.head.appendChild(style);