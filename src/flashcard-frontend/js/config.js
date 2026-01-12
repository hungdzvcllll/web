/**
 * ============================================
 * FLASHCARD APP - CONFIGURATION
 * ============================================
 */

const CONFIG = {
    // API Base URL - thay đổi khi deploy
    //API_BASE_URL: 'http://localhost:8081',
    //API_BASE_URL: 'https://flashcard-backend-app.onrender.com',
    API_BASE_URL: 'https://server1-production-6bff.up.railway.app',  
    // API Endpoints
    API: {
        // Auth
        LOGIN: '/user/signin',
        REGISTER: '/user/register',
        CONFIRM_REGISTER: '/user/confirmRegister',
        RESET_PASSWORD: '/user/resetPassword',
        CONFIRM_RESET: '/user/confirmResetPassword',
        UPDATE_PROFILE: '/user/update',
        YOUR_PROFILE: '/user/yourProfile',
        
        // Folder
        FOLDER_SAVE_PERSONAL: '/folder/savePersonalFolder',
        FOLDER_SAVE_CLASS: '/folder/saveClassFolder',
        FOLDER_UPDATE: '/folder/update',
        FOLDER_DELETE: '/folder/delete',
        FOLDER_FIND_BY_ID: '/folder/findById',
        FOLDER_FIND_BY_NAME: '/folder/findByName',
        FOLDER_FIND_CLASS: '/folder/findClassFolder',
        FOLDER_SEARCH: '/folder/search',

        // Access Folder
        ACCESS_FOLDER_INVITE: '/accessFolder/invite',
        ACCESS_FOLDER_YOUR: '/accessFolder/yourFolders',
        
        // Study Set
        STUDYSET_CREATE: '/studySet/create',
        STUDYSET_DELETE: '/studySet/delete',
        STUDYSET_FIND_BY_ID: '/studySet/findById',
        STUDYSET_FIND_BY_FOLDER: '/studySet/findByFolderId',
        STUDYSET_SEARCH: '/studySet/search',

        // Study Set Item (Flashcard)
        ITEM_ADD: '/item/add',
        ITEM_DELETE: '/item/delete',
        ITEM_UPDATE: '/item/update',
        ITEM_FIND_BY_STUDYSET: '/item/findByStudySet',
        ITEM_FIND_BY_ID: '/item/findById',
        
        // Test/Quiz
        TEST_GET: '/Test/getTest',
        TEST_SUBMIT: '/Test/submit',
        TEST_FIND_BY_ID: '/Test/findById',
        TEST_FIND_BY_STUDYSET: '/Test/findByStudySet',
        
        // Class
        CLASS_CREATE: '/class/create',
        CLASS_DELETE: '/class/delete',
        CLASS_FIND_BY_ID: '/class/findById',
        CLASS_SEARCH: '/class/search',
        
        // User In Class
        USER_IN_CLASS_YOUR: '/userInClass/yourClass',
        USER_IN_CLASS_DELETE: '/userInClass/deleteStudent',
        USER_IN_CLASS_ADD: '/userInClass/addStudent',
        USER_IN_CLASS_LIST: '/userInClass/userInClass',
        
        // Notification
        NOTIFICATION_YOUR: '/notification/yourNoti',
        
        // VIP
        VIP_PAY: '/VnPay/pay',
        VIP_RESULT: '/VnPay/paymentResult',
        VIP_YOUR: '/vipUser/getYourVipRegister',
        
        // TextBook
        TEXTBOOK_ADD: '/textBook/add',
        TEXTBOOK_FIND_BY_ID: '/textBook/findById',
        TEXTBOOK_FIND_ALL: '/textBook/findAll',
        
        // Chapter
        CHAPTER_SAVE: '/Chapter/save',
        CHAPTER_UPDATE: '/Chapter/update',
        CHAPTER_FIND_BY_ID: '/Chapter/findById',
        CHAPTER_FIND_BY_TEXTBOOK: '/Chapter/findByTextBook'
    },
    
    // Local Storage Keys
    STORAGE: {
        TOKEN: 'flashcard_token',
        USER: 'flashcard_user',
        THEME: 'flashcard_theme'
    },
    
    // Pagination
    DEFAULT_PAGE_SIZE: 10,
    
    // Toast duration (ms)
    TOAST_DURATION: 3000
};

// Freeze config to prevent modifications
Object.freeze(CONFIG);
Object.freeze(CONFIG.API);
Object.freeze(CONFIG.STORAGE);