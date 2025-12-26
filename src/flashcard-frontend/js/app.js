/**
 * ============================================
 * FLASHCARD APP - MAIN APPLICATION
 * ============================================
 */

const App = {
    /**
     * Initialize application
     */
    init() {
        this.initNavbar();
        this.initSidebar();
        this.updateUserInfo();
    },

    /**
     * Initialize navbar
     */
    initNavbar() {
        const navbarHtml = `
            <nav class="navbar navbar-expand-lg navbar-custom">
                <div class="container-fluid px-4">
                    <a class="navbar-brand" href="/pages/dashboard.html">
                        <i class="fas fa-layer-group"></i>
                        FlashCard
                    </a>
                    
                    <button class="navbar-toggler" type="button" id="sidebar-toggle">
                        <i class="fas fa-bars"></i>
                    </button>
                    
                    <div class="d-flex align-items-center gap-3">
                        <!-- Search -->
                        <div class="d-none d-md-block">
                            <div class="input-group">
                                <input type="text" class="form-control form-control-custom" 
                                       placeholder="Tìm kiếm..." id="global-search"
                                       style="width: 250px;">
                                <button class="btn btn-outline-custom" type="button" id="search-btn">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                        
                        <!-- Notifications -->
                        <div class="dropdown">
                            <button class="btn btn-outline-custom position-relative" 
                                    data-bs-toggle="dropdown" id="notification-btn">
                                <i class="fas fa-bell"></i>
                                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger d-none" id="notification-badge">
                                    0
                                </span>
                            </button>
                            <div class="dropdown-menu dropdown-menu-end p-0" style="width: 350px; max-height: 400px; overflow-y: auto;">
                                <div class="p-3 border-bottom">
                                    <h6 class="mb-0 fw-bold">Thông báo</h6>
                                </div>
                                <div id="notification-list">
                                    <div class="p-3 text-center text-muted">
                                        <i class="fas fa-bell-slash mb-2" style="font-size: 2rem;"></i>
                                        <p class="mb-0 small">Không có thông báo mới</p>
                                    </div>
                                </div>
                                <div class="p-2 border-top text-center">
                                    <a href="/pages/notifications.html" class="small">Xem tất cả</a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- User Menu -->
                        <div class="dropdown user-dropdown">
                            <button class="btn dropdown-toggle" data-bs-toggle="dropdown">
                                <div class="user-avatar" id="user-avatar">U</div>
                                <span class="d-none d-md-inline" id="user-name">User</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li>
                                    <a class="dropdown-item" href="/pages/profile.html">
                                        <i class="fas fa-user me-2"></i> Hồ sơ
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/pages/vip.html">
                                        <i class="fas fa-crown me-2"></i> Nâng cấp VIP
                                    </a>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item text-danger" href="#" id="logout-btn">
                                        <i class="fas fa-sign-out-alt me-2"></i> Đăng xuất
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </nav>
        `;

        // Insert navbar at the beginning of body
        const navbarContainer = document.getElementById('navbar-container');
        if (navbarContainer) {
            navbarContainer.innerHTML = navbarHtml;
        }

        // Logout handler
        document.getElementById('logout-btn')?.addEventListener('click', (e) => {
            e.preventDefault();
            API.logout();
        });

        // Search handler
        document.getElementById('search-btn')?.addEventListener('click', () => {
            const query = document.getElementById('global-search').value.trim();
            if (query) {
                window.location.href = `/pages/search.html?q=${encodeURIComponent(query)}`;
            }
        });

        document.getElementById('global-search')?.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                document.getElementById('search-btn').click();
            }
        });

        // Load notifications
        this.loadNotifications();
    },

    /**
     * Initialize sidebar
     */
    initSidebar() {
        const currentPath = window.location.pathname;
        
        const sidebarHtml = `
            <aside class="sidebar" id="sidebar">
                <ul class="sidebar-menu">
                    <li>
                        <a href="/pages/dashboard.html" class="${currentPath.includes('dashboard') ? 'active' : ''}">
                            <i class="fas fa-home"></i>
                            <span>Trang chủ</span>
                        </a>
                    </li>
                    <li>
                        <a href="/pages/folders.html" class="${currentPath.includes('folder') ? 'active' : ''}">
                            <i class="fas fa-folder"></i>
                            <span>Thư mục của tôi</span>
                        </a>
                    </li>
                    <li>
                        <a href="/pages/classes.html" class="${currentPath.includes('class') ? 'active' : ''}">
                            <i class="fas fa-users"></i>
                            <span>Lớp học</span>
                        </a>
                    </li>
                </ul>
                
                <div class="sidebar-divider"></div>
                <div class="sidebar-heading">Học tập</div>
                
                <ul class="sidebar-menu">
                    <li>
                        <a href="/pages/study.html" class="${currentPath.includes('study') ? 'active' : ''}">
                            <i class="fas fa-graduation-cap"></i>
                            <span>Chế độ học</span>
                        </a>
                    </li>
                    <li>
                        <a href="/pages/quiz-history.html" class="${currentPath.includes('quiz') ? 'active' : ''}">
                            <i class="fas fa-history"></i>
                            <span>Lịch sử kiểm tra</span>
                        </a>
                    </li>
                </ul>
                
                <div class="sidebar-divider"></div>
                <div class="sidebar-heading">Khám phá</div>
                
                <ul class="sidebar-menu">
                    <li>
                        <a href="/pages/explore.html" class="${currentPath.includes('explore') ? 'active' : ''}">
                            <i class="fas fa-compass"></i>
                            <span>Khám phá</span>
                        </a>
                    </li>
                    <li>
                        <a href="/pages/textbooks.html" class="${currentPath.includes('textbook') ? 'active' : ''}">
                            <i class="fas fa-book"></i>
                            <span>Sách giáo khoa</span>
                        </a>
                    </li>
                </ul>
                
                <div class="sidebar-divider"></div>
                
                <ul class="sidebar-menu">
                    <li>
                        <a href="/pages/notifications.html" class="${currentPath.includes('notification') ? 'active' : ''}">
                            <i class="fas fa-bell"></i>
                            <span>Thông báo</span>
                        </a>
                    </li>
                    <li>
                        <a href="/pages/profile.html" class="${currentPath.includes('profile') ? 'active' : ''}">
                            <i class="fas fa-cog"></i>
                            <span>Cài đặt</span>
                        </a>
                    </li>
                </ul>
            </aside>
        `;

        const sidebarContainer = document.getElementById('sidebar-container');
        if (sidebarContainer) {
            sidebarContainer.innerHTML = sidebarHtml;
        }

        // Sidebar toggle for mobile
        document.getElementById('sidebar-toggle')?.addEventListener('click', () => {
            document.getElementById('sidebar')?.classList.toggle('show');
        });

        // Close sidebar when clicking outside on mobile
        document.addEventListener('click', (e) => {
            const sidebar = document.getElementById('sidebar');
            const toggle = document.getElementById('sidebar-toggle');
            if (sidebar && window.innerWidth < 992) {
                if (!sidebar.contains(e.target) && !toggle.contains(e.target)) {
                    sidebar.classList.remove('show');
                }
            }
        });
    },

    /**
     * Update user info in navbar
     */
    updateUserInfo() {
        const user = Utils.getCurrentUser();
        if (user) {
            const userAvatar = document.getElementById('user-avatar');
            const userName = document.getElementById('user-name');
            
            if (userAvatar) {
                userAvatar.textContent = Utils.getInitials(user.username);
            }
            if (userName) {
                userName.textContent = user.username;
            }
        }
    },

    /**
     * Load notifications
     */
    async loadNotifications() {
        try {
            const response = await API.getNotifications(0, 5);
            const notifications = response.content || [];
            
            const notificationList = document.getElementById('notification-list');
            const notificationBadge = document.getElementById('notification-badge');
            
            if (notifications.length > 0) {
                notificationList.innerHTML = notifications.map(noti => `
                    <div class="notification-item ${noti.read ? '' : 'unread'}">
                        <div class="notification-icon info">
                            <i class="fas fa-bell"></i>
                        </div>
                        <div class="notification-content">
                            <div class="notification-text">${Utils.escapeHtml(noti.notification)}</div>
                            <div class="notification-time">${Utils.formatRelativeTime(noti.createdAt || new Date())}</div>
                        </div>
                    </div>
                `).join('');
                
                const unreadCount = notifications.filter(n => !n.read).length;
                if (unreadCount > 0) {
                    notificationBadge.textContent = unreadCount;
                    notificationBadge.classList.remove('d-none');
                }
            }
        } catch (error) {
            console.error('Failed to load notifications:', error);
        }
    }
};

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    // Only initialize if user is logged in and not on auth pages
    const authPages = ['login.html', 'register.html', 'forgot-password.html'];
    const currentPage = window.location.pathname.split('/').pop();
    
    if (!authPages.includes(currentPage) && API.isLoggedIn()) {
        App.init();
    }
});