/**
 * ============================================
 * FLASHCARD APP - API SERVICE
 * ============================================
 */

const API = {
    /**
     * Get auth token from localStorage
     */
    getToken() {
        return localStorage.getItem(CONFIG.STORAGE.TOKEN);
    },

    /**
     * Set auth token to localStorage
     */
    setToken(token) {
        localStorage.setItem(CONFIG.STORAGE.TOKEN, token);
    },

    /**
     * Remove auth token from localStorage
     */
    removeToken() {
        localStorage.removeItem(CONFIG.STORAGE.TOKEN);
        localStorage.removeItem(CONFIG.STORAGE.USER);
    },

    /**
     * Check if user is logged in
     */
    isLoggedIn() {
        return !!this.getToken();
    },

    /**
     * Get headers for API requests
     */
    getHeaders(isFormData = false) {
        const headers = {};
        
        if (!isFormData) {
            headers['Content-Type'] = 'application/json';
        }
        
        const token = this.getToken();
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        
        return headers;
    },

    /**
     * Handle API response
     */
    async handleResponse(response) {
        if (response.status === 401) {
            // Unauthorized - redirect to login
            this.removeToken();
            window.location.href = '/pages/login.html';
            throw new Error('Session expired. Please login again.');
        }
        
        const contentType = response.headers.get('content-type');
        
        if (contentType && contentType.includes('application/json')) {
            const data = await response.json();
            if (!response.ok) {
                throw new Error(data.message || data || 'Something went wrong');
            }
            return data;
        } else {
            const text = await response.text();
            if (!response.ok) {
                throw new Error(text || 'Something went wrong');
            }
            return text;
        }
    },

    /**
     * GET request
     */
    async get(endpoint, params = {}) {
        const url = new URL(CONFIG.API_BASE_URL + endpoint);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null) {
                url.searchParams.append(key, params[key]);
            }
        });
        
        const response = await fetch(url, {
            method: 'GET',
            headers: this.getHeaders()
        });
        
        return this.handleResponse(response);
    },

    /**
     * POST request (JSON)
     */
    async post(endpoint, data = {}) {
        const response = await fetch(CONFIG.API_BASE_URL + endpoint, {
            method: 'POST',
            headers: this.getHeaders(),
            body: JSON.stringify(data)
        });
        
        return this.handleResponse(response);
    },

    /**
     * POST request with URL params
     */
    async postParams(endpoint, params = {}) {
        const url = new URL(CONFIG.API_BASE_URL + endpoint);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null) {
                url.searchParams.append(key, params[key]);
            }
        });
        
        const response = await fetch(url, {
            method: 'POST',
            headers: this.getHeaders()
        });
        
        return this.handleResponse(response);
    },

    /**
     * POST request with FormData
     */
    async postFormData(endpoint, formData, params = {}) {
        const url = new URL(CONFIG.API_BASE_URL + endpoint);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null) {
                url.searchParams.append(key, params[key]);
            }
        });
        
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${this.getToken()}`
            },
            body: formData
        });
        
        return this.handleResponse(response);
    },

    /**
     * PUT request (JSON)
     */
    async put(endpoint, data = {}) {
        const response = await fetch(CONFIG.API_BASE_URL + endpoint, {
            method: 'PUT',
            headers: this.getHeaders(),
            body: JSON.stringify(data)
        });
        
        return this.handleResponse(response);
    },

    /**
     * PUT request with URL params
     */
    async putParams(endpoint, params = {}) {
        const url = new URL(CONFIG.API_BASE_URL + endpoint);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null) {
                url.searchParams.append(key, params[key]);
            }
        });
        
        const response = await fetch(url, {
            method: 'PUT',
            headers: this.getHeaders()
        });
        
        return this.handleResponse(response);
    },

    /**
     * DELETE request
     */
    async delete(endpoint, params = {}) {
        const url = new URL(CONFIG.API_BASE_URL + endpoint);
        Object.keys(params).forEach(key => {
            if (params[key] !== undefined && params[key] !== null) {
                url.searchParams.append(key, params[key]);
            }
        });
        
        const response = await fetch(url, {
            method: 'DELETE',
            headers: this.getHeaders()
        });
        
        return this.handleResponse(response);
    },

    // ==================== AUTH ====================
    
    async login(username, password) {
        const response = await this.post(CONFIG.API.LOGIN, { username, password });
        if (response) {
            this.setToken(response);
            // Get user profile after login
            const user = await this.getProfile();
            localStorage.setItem(CONFIG.STORAGE.USER, JSON.stringify(user));
        }
        return response;
    },

    async register(username, password, email) {
        return this.post(CONFIG.API.REGISTER, { username, password, email });
    },

    async confirmRegister(username, registerCode) {
        return this.putParams(CONFIG.API.CONFIRM_REGISTER, { username, registerCode });
    },

    async resetPassword(username) {
        return this.putParams(CONFIG.API.RESET_PASSWORD, { username });
    },

    async confirmResetPassword(username, code, newPassword) {
        return this.putParams(CONFIG.API.CONFIRM_RESET, { username, code, newPassword });
    },

    async getProfile() {
        return this.get(CONFIG.API.YOUR_PROFILE);
    },

    async updateProfile(data) {
        return this.put(CONFIG.API.UPDATE_PROFILE, data);
    },

    logout() {
        this.removeToken();
        window.location.href = '/pages/login.html';
    },

    // ==================== FOLDER ====================

    async createPersonalFolder(name, isPrivate) {
        return this.postParams(CONFIG.API.FOLDER_SAVE_PERSONAL, { name, isPrivate });
    },

    async createClassFolder(name, classId) {
        return this.postParams(CONFIG.API.FOLDER_SAVE_CLASS, { name, classId });
    },

    async updateFolder(id, name, isPrivate) {
        return this.putParams(CONFIG.API.FOLDER_UPDATE, { id, name, isPrivate });
    },

    async getFolderById(id) {
        return this.get(CONFIG.API.FOLDER_FIND_BY_ID, { id });
    },

    async searchFolders(name) {
        return this.get(CONFIG.API.FOLDER_SEARCH, { name });
    },

    async getClassFolders(classId, page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.FOLDER_FIND_CLASS, { classId, page, size });
    },

    async getYourFolders(page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.ACCESS_FOLDER_YOUR, { page, size });
    },

    async inviteToFolder(userId, folderId) {
        return this.postParams(CONFIG.API.ACCESS_FOLDER_INVITE, { userId, folderId });
    },

    async deleteFolder(id) {
        return this.delete(CONFIG.API.FOLDER_DELETE, { id });
    },

    // ==================== STUDY SET ====================

    async createStudySet(folderId, name) {
        return this.postParams(CONFIG.API.STUDYSET_CREATE, { folderId, name });
    },

    async getStudySetById(id) {
        return this.get(CONFIG.API.STUDYSET_FIND_BY_ID, { id });
    },

    async getStudySetsByFolder(folderId, page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.STUDYSET_FIND_BY_FOLDER, { folderId, page, size });
    },

    async searchStudySets(name) {
        return this.get(CONFIG.API.STUDYSET_SEARCH, { name });
    },

    async deleteStudySet(id) {
        return this.delete(CONFIG.API.STUDYSET_DELETE, { id });
    },

    // ==================== FLASHCARD (STUDY SET ITEM) ====================
    
    async addFlashcard(studySetId, concept, define, image = null) {
        const formData = new FormData();
        formData.append('concept', concept);
        formData.append('define', define);
        if (image) {
            formData.append('image', image);
        }
        return this.postFormData(CONFIG.API.ITEM_ADD, formData, { studySetId });
    },

    async updateFlashcard(id, concept, define, image = null) {
        const formData = new FormData();
        formData.append('concept', concept);
        formData.append('define', define);
        if (image && image instanceof File) {
            formData.append('image', image);
        }
        
        const response = await fetch(`${CONFIG.API_BASE_URL}${CONFIG.API.ITEM_UPDATE}?id=${id}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${this.getToken()}`
            },
            body: formData
        });
        
        return this.handleResponse(response);
    },

    async deleteFlashcard(studySetId) {
        return this.delete(CONFIG.API.ITEM_DELETE, { studySetId });
    },

    async getFlashcardsByStudySet(studySetId, page = 0, size = 100) {
        return this.get(CONFIG.API.ITEM_FIND_BY_STUDYSET, { studySetId, page, size });
    },

    async getFlashcardById(id) {
        return this.get(CONFIG.API.ITEM_FIND_BY_ID, { id });
    },

    // ==================== TEST/QUIZ ====================
    
    async getQuiz(studySetId) {
        return this.get(CONFIG.API.TEST_GET, { studySetId });
    },

    async submitQuiz(studySetId, name, answers) {
        const url = new URL(CONFIG.API_BASE_URL + CONFIG.API.TEST_SUBMIT);
        url.searchParams.append('studySetId', studySetId);
        url.searchParams.append('name', name);
        
        const response = await fetch(url, {
            method: 'POST',
            headers: this.getHeaders(),
            body: JSON.stringify(answers)
        });
        
        return this.handleResponse(response);
    },

    async getTestById(id) {
        return this.get(CONFIG.API.TEST_FIND_BY_ID, { id });
    },

    async getTestsByStudySet(studySetId, page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.TEST_FIND_BY_STUDYSET, { studySetId, page, size });
    },

    // ==================== CLASS ====================

    async createClass(name) {
        return this.postParams(CONFIG.API.CLASS_CREATE, { name });
    },

    async getClassById(id) {
        return this.get(CONFIG.API.CLASS_FIND_BY_ID, { id });
    },

    async getYourClasses(page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.USER_IN_CLASS_YOUR, { page, size });
    },

    async getUsersInClass(classId, page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.USER_IN_CLASS_LIST, { classId, page, size });
    },

    async addStudentToClass(userId, classId) {
        return this.postParams(CONFIG.API.USER_IN_CLASS_ADD, { userId, classId });
    },

    async removeStudentFromClass(userId, classId) {
        return this.delete(CONFIG.API.USER_IN_CLASS_DELETE, { userId, classId });
    },

    async searchClasses(name) {
        return this.get(CONFIG.API.CLASS_SEARCH, { name });
    },

    async deleteClass(id) {
        return this.delete(CONFIG.API.CLASS_DELETE, { id });
    },

    // ==================== NOTIFICATION ====================
    
    async getNotifications(page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.NOTIFICATION_YOUR, { page, size });
    },

    // ==================== VIP ====================
    
    async createVipPayment(bankCode = '', locale = 'vn') {
        return this.post(CONFIG.API.VIP_PAY, { bankCode, locale });
    },

    async confirmVipPayment(result) {
        return this.put(CONFIG.API.VIP_RESULT, result);
    },

    async getYourVipHistory(page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.VIP_YOUR, { page, size });
    },

    // ==================== TEXTBOOK ====================
    
    async addTextbook(name) {
        return this.postParams(CONFIG.API.TEXTBOOK_ADD, { name });
    },

    async getTextbookById(id) {
        return this.get(CONFIG.API.TEXTBOOK_FIND_BY_ID, { id });
    },

    async getAllTextbooks(page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.TEXTBOOK_FIND_ALL, { page, size });
    },

    // ==================== CHAPTER ====================
    
    async saveChapter(name, solve, textBookId) {
        const formData = new FormData();
        formData.append('name', name);
        formData.append('solve', solve);
        formData.append('textBookId', textBookId);
        return this.postFormData(CONFIG.API.CHAPTER_SAVE, formData);
    },

    async getChapterById(id) {
        return this.get(CONFIG.API.CHAPTER_FIND_BY_ID, { id });
    },

    async getChaptersByTextbook(textBookId, page = 0, size = CONFIG.DEFAULT_PAGE_SIZE) {
        return this.get(CONFIG.API.CHAPTER_FIND_BY_TEXTBOOK, { textBookId, page, size });
    }
};