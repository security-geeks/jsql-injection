package com.jsql.util;

import java.net.HttpURLConnection;
import java.util.prefs.Preferences;

import com.jsql.model.InjectionModel;

/**
 * Utility class to manage JVM preferences previously saved into the system.
 * Only general settings are processed by this utility, other specific preferences
 * like those for proxy are defined from specific utility classes.
 */
public class PreferencesUtil {
    
    // File path saved in preference.
    private String pathFile;

    // True if updates are checked on startup.
    private boolean isCheckingUpdate = true;

    // True if bugs are sent to Github.
    private boolean isReportingBugs = true;
    
    private boolean is4K = true;
    
    // True if HTTP 302 redirection are followed to the new URL.
    private boolean isFollowingRedirection = false;
    
    private boolean isNotInjectingMetadata = false;
    
    private boolean isCheckingAllParam = false;
    private boolean isCheckingAllURLParam = false;
    private boolean isCheckingAllRequestParam = false;
    private boolean isCheckingAllHeaderParam = false;
    private boolean isCheckingAllJSONParam = false;
    private boolean isCheckingAllCookieParam = false;
    private boolean isCheckingAllSOAPParam = false;
    
    private boolean isPerfIndexDisabled = false;
    private boolean isZippedStrategy = false;
    private boolean isUrlEncodingDisabled = false;
    
    private boolean isParsingForm = false;
    private boolean isNotTestingConnection = false;
    private boolean isNotProcessingCookies = false;
    private boolean isProcessingCsrf = false;
    
    private boolean isTamperingBase64 = false;
    private boolean isTamperingFunctionComment = false;
    private boolean isTamperingVersionComment = false;
    private boolean isTamperingEqualToLike = false;
    private boolean isTamperingRandomCase = false;
    private boolean isTamperingEval = false;
    private boolean isTamperingSpaceToMultilineComment = false;
    private boolean isTamperingSpaceToDashComment = false;
    private boolean isTamperingSpaceToSharpComment = false;

    private String csrfUserTag = "";
    private boolean isCsrfUserTag = false;
    private boolean isLimitingThreads = false;
    private int countLimitingThreads = 10;
    private boolean isUnicodeDecodeDisabled = false;

    private boolean isLimitingNormalIndex = false;
    private int countNormalIndex = 50;

    /**
     * Initialize the utility class with previously saved JVM preferences and apply
     * loaded settings to the system.
     */
    public void loadSavedPreferences() {
        
        // Use Preferences API to persist proxy configuration
        Preferences prefs = Preferences.userRoot().node(InjectionModel.class.getName());
        
        this.pathFile = prefs.get("pathFile", System.getProperty("user.dir"));
        
        this.isCheckingUpdate = prefs.getBoolean("isCheckingUpdate", true);
        this.isReportingBugs = prefs.getBoolean("isReportingBugs", true);
        
        this.isFollowingRedirection = prefs.getBoolean("isFollowingRedirection", false);
        this.isNotInjectingMetadata = prefs.getBoolean("isNotInjectingMetadata", false);
        
        this.isCheckingAllParam = prefs.getBoolean("isCheckingAllParam", false);
        this.isCheckingAllURLParam = prefs.getBoolean("isCheckingAllURLParam", false);
        this.isCheckingAllRequestParam = prefs.getBoolean("isCheckingAllRequestParam", false);
        this.isCheckingAllHeaderParam = prefs.getBoolean("isCheckingAllHeaderParam", false);
        this.isCheckingAllJSONParam = prefs.getBoolean("isCheckingAllJSONParam", false);
        this.isCheckingAllCookieParam = prefs.getBoolean("isCheckingAllCookieParam", false);
        this.isCheckingAllSOAPParam = prefs.getBoolean("isCheckingAllSOAPParam", false);
        
        this.isPerfIndexDisabled = prefs.getBoolean("isPerfIndexDisabled", false);
        this.isZippedStrategy = prefs.getBoolean("isZippedStrategy", false);
        this.isUrlEncodingDisabled = prefs.getBoolean("isUrlEncodingDisabled", false);
        
        this.isParsingForm = prefs.getBoolean("isParsingForm", false);
        this.isNotTestingConnection = prefs.getBoolean("isNotTestingConnection", false);
        this.isNotProcessingCookies = prefs.getBoolean("isNotProcessingCookies", false);
        this.isProcessingCsrf = prefs.getBoolean("isProcessingCsrf", false);
        
        this.isTamperingBase64 = prefs.getBoolean("isTamperingBase64", false);
        this.isTamperingEqualToLike = prefs.getBoolean("isTamperingEqualToLike", false);
        this.isTamperingFunctionComment = prefs.getBoolean("isTamperingFunctionComment", false);
        this.isTamperingVersionComment = prefs.getBoolean("isTamperingVersionComment", false);
        this.isTamperingRandomCase = prefs.getBoolean("isTamperingRandomCase", false);
        this.isTamperingEval = prefs.getBoolean("isTamperingEval", false);
        this.isTamperingSpaceToDashComment = prefs.getBoolean("isTamperingSpaceToDashComment", false);
        this.isTamperingSpaceToMultilineComment = prefs.getBoolean("isTamperingSpaceToMultilineComment", false);
        this.isTamperingSpaceToSharpComment = prefs.getBoolean("isTamperingSpaceToSharpComment", false);
        
        this.is4K = prefs.getBoolean("is4K", false);
        this.isCsrfUserTag = prefs.getBoolean("isCsrfUserTag", false);
        this.csrfUserTag = prefs.get("csrfUserTag", "");
        this.isLimitingThreads = prefs.getBoolean("isLimitingThreads", false);
        this.countLimitingThreads = prefs.getInt("countLimitingThreads", 10);
        this.isUnicodeDecodeDisabled = prefs.getBoolean("isUnicodeDecodeDisabled", false);
        this.countNormalIndex = prefs.getInt("countNormalIndex", 50);
        this.isLimitingNormalIndex = prefs.getBoolean("isLimitingNormalIndex", false);
        
        HttpURLConnection.setFollowRedirects(this.isFollowingRedirection);
    }
    
    /**
     * Initialize the utility class, persist preferences and
     * apply change to the JVM.
     */
    public void persist() {
        
        Preferences preferences = Preferences.userRoot().node(InjectionModel.class.getName());

        preferences.putBoolean("isCheckingUpdate", this.isCheckingUpdate);
        preferences.putBoolean("isReportingBugs", this.isReportingBugs);
        preferences.putBoolean("is4K", this.is4K);
        preferences.putBoolean("isLimitingThreads", this.isLimitingThreads);
        preferences.putBoolean("isUnicodeDecodeDisabled", this.isUnicodeDecodeDisabled);
        preferences.putInt("countLimitingThreads", this.countLimitingThreads);
        preferences.putBoolean("isLimitingNormalIndex", this.isLimitingNormalIndex);
        preferences.putInt("countNormalIndex", this.countNormalIndex);
        preferences.putBoolean("isCsrfUserTag", this.isCsrfUserTag);
        preferences.put("csrfUserTag", this.csrfUserTag);
        
        preferences.putBoolean("isFollowingRedirection", this.isFollowingRedirection);
        preferences.putBoolean("isNotInjectingMetadata", this.isNotInjectingMetadata);
        preferences.putBoolean("isCheckingAllParam", this.isCheckingAllParam);
        preferences.putBoolean("isCheckingAllURLParam", this.isCheckingAllURLParam);
        preferences.putBoolean("isCheckingAllRequestParam", this.isCheckingAllRequestParam);
        preferences.putBoolean("isCheckingAllHeaderParam", this.isCheckingAllHeaderParam);
        preferences.putBoolean("isCheckingAllJSONParam", this.isCheckingAllJSONParam);
        preferences.putBoolean("isCheckingAllCookieParam", this.isCheckingAllCookieParam);
        preferences.putBoolean("isCheckingAllSOAPParam", this.isCheckingAllSOAPParam);
        preferences.putBoolean("isParsingForm", this.isParsingForm);
        preferences.putBoolean("isNotTestingConnection", this.isNotTestingConnection);
        preferences.putBoolean("isNotProcessingCookies", this.isNotProcessingCookies);
        preferences.putBoolean("isProcessingCsrf", this.isProcessingCsrf);
        
        preferences.putBoolean("isPerfIndexDisabled", this.isPerfIndexDisabled);
        preferences.putBoolean("isZippedStrategy", this.isZippedStrategy);
        preferences.putBoolean("isUrlEncodingDisabled", this.isUrlEncodingDisabled);
        
        preferences.putBoolean("isTamperingBase64", this.isTamperingBase64);
        preferences.putBoolean("isTamperingEqualToLike", this.isTamperingEqualToLike);
        preferences.putBoolean("isTamperingVersionComment", this.isTamperingVersionComment);
        preferences.putBoolean("isTamperingFunctionComment", this.isTamperingFunctionComment);
        preferences.putBoolean("isTamperingRandomCase", this.isTamperingRandomCase);
        preferences.putBoolean("isTamperingEval", this.isTamperingEval);
        preferences.putBoolean("isTamperingSpaceToDashComment", this.isTamperingSpaceToDashComment);
        preferences.putBoolean("isTamperingSpaceToMultilineComment", this.isTamperingSpaceToMultilineComment);
        preferences.putBoolean("isTamperingSpaceToSharpComment", this.isTamperingSpaceToSharpComment);
        
        HttpURLConnection.setFollowRedirects(this.isFollowingRedirection);
    }
    
    /**
     * Set the general file path to the utility class and persist to JVM preferences.
     * @param path folder path to persist
     */
    public void set(String path) {
        
        this.pathFile = path;
        
        Preferences prefs = Preferences.userRoot().node(InjectionModel.class.getName());
        prefs.put("pathFile", this.pathFile);
    }
    
    
    // Getters and setters
    
    public String getPathFile() {
        return this.pathFile;
    }
    
    public boolean isCheckingUpdate() {
        return this.isCheckingUpdate;
    }
    
    public boolean isFollowingRedirection() {
        return this.isFollowingRedirection;
    }
    
    public boolean isReportingBugs() {
        return this.isReportingBugs;
    }

    public boolean isNotInjectingMetadata() {
        return this.isNotInjectingMetadata;
    }

    public boolean isCheckingAllURLParam() {
        return this.isCheckingAllURLParam;
    }

    public boolean isCheckingAllRequestParam() {
        return this.isCheckingAllRequestParam;
    }

    public boolean isCheckingAllHeaderParam() {
        return this.isCheckingAllHeaderParam;
    }

    public boolean isCheckingAllJsonParam() {
        return this.isCheckingAllJSONParam;
    }

    public boolean isParsingForm() {
        return this.isParsingForm;
    }

    public boolean isNotTestingConnection() {
        return this.isNotTestingConnection;
    }

    public boolean isNotProcessingCookies() {
        return this.isNotProcessingCookies;
    }

    public boolean isCheckingAllParam() {
        return this.isCheckingAllParam;
    }

    public boolean isProcessingCsrf() {
        return this.isProcessingCsrf;
    }

    public boolean isCheckingAllCookieParam() {
        return this.isCheckingAllCookieParam;
    }

    public boolean isTamperingBase64() {
        return this.isTamperingBase64;
    }

    public boolean isTamperingFunctionComment() {
        return this.isTamperingFunctionComment;
    }

    public boolean isTamperingEqualToLike() {
        return this.isTamperingEqualToLike;
    }

    public boolean isTamperingRandomCase() {
        return this.isTamperingRandomCase;
    }

    public boolean isTamperingSpaceToMultilineComment() {
        return this.isTamperingSpaceToMultilineComment;
    }

    public boolean isTamperingSpaceToDashComment() {
        return this.isTamperingSpaceToDashComment;
    }

    public boolean isTamperingSpaceToSharpComment() {
        return this.isTamperingSpaceToSharpComment;
    }

    public boolean isTamperingVersionComment() {
        return this.isTamperingVersionComment;
    }

    public boolean isTamperingEval() {
        return this.isTamperingEval;
    }

    public boolean isCheckingAllSOAPParam() {
        return this.isCheckingAllSOAPParam;
    }

    public boolean is4K() {
        return this.is4K;
    }

    public boolean isLimitingThreads() {
        return this.isLimitingThreads;
    }
    
    public boolean isUnicodeDecodeDisabled() {
        return this.isUnicodeDecodeDisabled;
    }
    
    public int countLimitingThreads() {
        return this.countLimitingThreads;
    }
    
    public int countNormalIndex() {
        return this.countNormalIndex;
    }
    
    public boolean isLimitingNormalIndex() {
        return this.isLimitingNormalIndex;
    }
    
    public boolean isCsrfUserTag() {
        return this.isCsrfUserTag;
    }
    
    public String csrfUserTag() {
        return this.csrfUserTag;
    }
    
    public boolean isPerfIndexDisabled() {
        return this.isPerfIndexDisabled;
    }
    
    public boolean isZippedStrategy() {
        return this.isZippedStrategy;
    }
    
    public boolean isUrlEncodingDisabled() {
        return this.isUrlEncodingDisabled;
    }
    
    
    // Builder true

    public PreferencesUtil withNotTestingConnection() {
        this.isNotTestingConnection = true;
        return this;
    }
    
    public PreferencesUtil withCheckingAllHeaderParam() {
        this.isCheckingAllHeaderParam = true;
        return this;
    }
    
    public PreferencesUtil withIsNotProcessingCookies() {
        this.isNotProcessingCookies = true;
        return this;
    }
    
    public PreferencesUtil withProcessingCsrf() {
        this.isProcessingCsrf = true;
        return this;
    }
    
    public PreferencesUtil withCheckingAllURLParam() {
        this.isCheckingAllURLParam = true;
        return this;
    }
    
    public PreferencesUtil withCheckingAllRequestParam() {
        this.isCheckingAllRequestParam = true;
        return this;
    }
    
    public PreferencesUtil withCheckingAllJSONParam() {
        this.isCheckingAllJSONParam = true;
        return this;
    }
    
    public PreferencesUtil withCheckingAllSOAPParam() {
        this.isCheckingAllSOAPParam = true;
        return this;
    }
    
    public PreferencesUtil withIsCheckingUpdate() {
        this.isCheckingUpdate = true;
        return this;
    }
    
    public PreferencesUtil withIsReportingBugs() {
        this.isReportingBugs = true;
        return this;
    }
    
    public PreferencesUtil withIsCheckingAllURLParam() {
        this.isCheckingAllURLParam = true;
        return this;
    }
    
    
    // Builder

    public PreferencesUtil withIsCheckingUpdate(boolean isCheckingUpdate) {
        this.isCheckingUpdate = isCheckingUpdate;
        return this;
    }

    public PreferencesUtil withIsReportingBugs(boolean isReportingBugs) {
        this.isReportingBugs = isReportingBugs;
        return this;
    }

    public PreferencesUtil withIs4K(boolean is4K) {
        this.is4K = is4K;
        return this;
    }

    public PreferencesUtil withIsFollowingRedirection(boolean isFollowingRedirection) {
        this.isFollowingRedirection = isFollowingRedirection;
        return this;
    }
    
    public PreferencesUtil withIsUnicodeDecodeDisabled(boolean isUnicodeDecodeDisabled) {
        this.isUnicodeDecodeDisabled = isUnicodeDecodeDisabled;
        return this;
    }

    public PreferencesUtil withIsNotInjectingMetadata(boolean isNotInjectingMetadata) {
        this.isNotInjectingMetadata = isNotInjectingMetadata;
        return this;
    }

    public PreferencesUtil withIsCheckingAllParam(boolean isCheckingAllParam) {
        this.isCheckingAllParam = isCheckingAllParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllURLParam(boolean isCheckingAllURLParam) {
        this.isCheckingAllURLParam = isCheckingAllURLParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllRequestParam(boolean isCheckingAllRequestParam) {
        this.isCheckingAllRequestParam = isCheckingAllRequestParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllHeaderParam(boolean isCheckingAllHeaderParam) {
        this.isCheckingAllHeaderParam = isCheckingAllHeaderParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllJSONParam(boolean isCheckingAllJSONParam) {
        this.isCheckingAllJSONParam = isCheckingAllJSONParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllCookieParam(boolean isCheckingAllCookieParam) {
        this.isCheckingAllCookieParam = isCheckingAllCookieParam;
        return this;
    }

    public PreferencesUtil withIsCheckingAllSOAPParam(boolean isCheckingAllSOAPParam) {
        this.isCheckingAllSOAPParam = isCheckingAllSOAPParam;
        return this;
    }

    public PreferencesUtil withIsParsingForm(boolean isParsingForm) {
        this.isParsingForm = isParsingForm;
        return this;
    }

    public PreferencesUtil withIsNotTestingConnection(boolean isNotTestingConnection) {
        this.isNotTestingConnection = isNotTestingConnection;
        return this;
    }

    public PreferencesUtil withIsNotProcessingCookies(boolean isNotProcessingCookies) {
        this.isNotProcessingCookies = isNotProcessingCookies;
        return this;
    }

    public PreferencesUtil withIsProcessingCsrf(boolean isProcessingCsrf) {
        this.isProcessingCsrf = isProcessingCsrf;
        return this;
    }

    public PreferencesUtil withIsTamperingBase64(boolean isTamperingBase64) {
        this.isTamperingBase64 = isTamperingBase64;
        return this;
    }

    public PreferencesUtil withIsTamperingFunctionComment(boolean isTamperingFunctionComment) {
        this.isTamperingFunctionComment = isTamperingFunctionComment;
        return this;
    }

    public PreferencesUtil withIsTamperingVersionComment(boolean isTamperingVersionComment) {
        this.isTamperingVersionComment = isTamperingVersionComment;
        return this;
    }

    public PreferencesUtil withIsTamperingEqualToLike(boolean isTamperingEqualToLike) {
        this.isTamperingEqualToLike = isTamperingEqualToLike;
        return this;
    }

    public PreferencesUtil withIsTamperingRandomCase(boolean isTamperingRandomCase) {
        this.isTamperingRandomCase = isTamperingRandomCase;
        return this;
    }

    public PreferencesUtil withIsTamperingEval(boolean isTamperingEval) {
        this.isTamperingEval = isTamperingEval;
        return this;
    }

    public PreferencesUtil withIsTamperingSpaceToMultilineComment(boolean isTamperingSpaceToMultilineComment) {
        this.isTamperingSpaceToMultilineComment = isTamperingSpaceToMultilineComment;
        return this;
    }

    public PreferencesUtil withIsTamperingSpaceToDashComment(boolean isTamperingSpaceToDashComment) {
        this.isTamperingSpaceToDashComment = isTamperingSpaceToDashComment;
        return this;
    }

    public PreferencesUtil withIsTamperingSpaceToSharpComment(boolean isTamperingSpaceToSharpComment) {
        this.isTamperingSpaceToSharpComment = isTamperingSpaceToSharpComment;
        return this;
    }

    public PreferencesUtil withCsrfUserTag(String csrfUserTag) {
        this.csrfUserTag = csrfUserTag;
        return this;
    }

    public PreferencesUtil withIsCsrfUserTag(boolean isCsrfUserTag) {
        this.isCsrfUserTag = isCsrfUserTag;
        return this;
    }

    public PreferencesUtil withIsLimitingThreads(boolean isLimitingThreads) {
        this.isLimitingThreads = isLimitingThreads;
        return this;
    }

    public PreferencesUtil withCountLimitingThreads(int countLimitingThreads) {
        this.countLimitingThreads = countLimitingThreads;
        return this;
    }
    
    public PreferencesUtil withIsZippedStrategy(boolean isZippedStrategy) {
        this.isZippedStrategy = isZippedStrategy;
        return this;
    }
    
    public PreferencesUtil withIsPerfIndexDisabled(boolean isPerfIndexDisabled) {
        this.isPerfIndexDisabled = isPerfIndexDisabled;
        return this;
    }
    
    public PreferencesUtil withIsUrlEncodingDisabled(boolean isUrlEncodingDisabled) {
        this.isUrlEncodingDisabled = isUrlEncodingDisabled;
        return this;
    }

    public PreferencesUtil withIsLimitingNormalIndex(boolean isLimitingNormalIndex) {
        this.isLimitingNormalIndex = isLimitingNormalIndex;
        return this;
    }

    public PreferencesUtil withCountNormalIndex(int countNormalIndex) {
        this.countNormalIndex = countNormalIndex;
        return this;
    }
}
