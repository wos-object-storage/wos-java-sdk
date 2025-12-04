package com.chinanetcenter.api.entity;

/**
 * Upload policy object<br>
 * <!--
 * <table border="1px solid">
 * 	<tr>
 * 		<th>Field Name</th>
 * 		<th width="60px">Required</th>
 * 		<th>Description</th>
 * 	</tr>
 * 	<tr>
 * 		<td>scope</td>
 * 		<td>Yes</td>
 * 		<td>Specifies the target resource space (Bucket) and resource name (Key) for upload. Two formats: 1. <bucket>, allowing users to upload files to the specified bucket. 2. <bucket>:<filename>, allowing users to upload files with the specified filename.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>deadline</td>
 * 		<td>Yes</td>
 * 		<td>Expiration time for upload request authorization; UNIX timestamp in milliseconds. Example: 1398916800000, representing the time 2014-05-01 12:00:00.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>returnUrl</td>
 * 		<td>No</td>
 * 		<td>The URL where the browser will perform a 303 redirect after successful or failed file upload on the web; typically used for HTML Form uploads. (1) After successful file upload, it will redirect to <returnUrl>?upload_ret=<queryString>, where <queryString> contains the returnBody content. (2) After failed file upload, it will redirect to <returnUrl>?code=<code>&message=<message>, where <code> is the error code and <message> is the detailed error information. If returnUrl is not set, the content of returnBody will be directly returned to the client.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>returnBody</td>
 * 		<td>No</td>
 * 		<td>
 * 			Custom data returned to the uploader after successful upload (this field is used with returnUrl). If you only need to return the file size and file address, just set returnBody to fname=$(fname)&fsize=$(fsize)&url=$(url).
 * 			<ul>
 * 				<li>
 * 					Custom replacement variables, format: $(x:variable), example: position=$(x:position)&message=$(x:message)
 * 				</li>
 * 				<li>
 * 					Special replacement variables
 * 					<table border="1px solid">
 * 						<tr>
 * 							<th>Parameter Value</th>
 * 							<th>Description</th>
 * 						</tr>
 * 						<tr>
 * 							<td>$(bucket)</td>
 * 							<td>Get the target space name for upload</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(key)</td>
 * 							<td>Get the resource name of the file saved in the space</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(fname)</td>
 * 							<td>Original filename of the upload</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(hash)</td>
 * 							<td>Unique resource identifier (hash(bucket:fname))</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(fsize)</td>
 * 							<td>Resource size in bytes</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(url)</td>
 * 							<td>Actual path to access the resource, URL-safe Base64 encoded, needs to be decoded when used</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(costTime)</td>
 * 							<td>Time consumed by this request</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(ip)</td>
 * 							<td>Source IP of this request</td>
 * 						</tr>
 * 					</table>
 * 				</li>
 * 			</ul>
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td>fsizeLimit</td>
 * 		<td>No</td>
 * 		<td>Limits the size of the uploaded file in bytes (Byte); upload content exceeding the limit will be judged as upload failure, returning 413 status code.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>overwrite</td>
 * 		<td>No</td>
 * 		<td>Specifies whether to overwrite files that already exist on the server; 0: do not overwrite; 1: overwrite</td>
 * 	</tr>
 * 	<tr>
 * 		<td>callbackUrl</td>
 * 		<td>No</td>
 * 		<td>After successful upload, WCS will request this callbackUrl via POST (must be a public network URL that can properly respond to HTTP/1.1 200 OK). The Response returned by callbackUrl is required to be in JSON format, i.e., Content-Type is "application/json".</td>
 * 	</tr>
 * 	<tr>
 * 		<td>callbackBody</td>
 * 		<td>No</td>
 * 		<td>
 * 			Data submitted by WCS via POST after successful upload. callbackBody is required to be a valid URL query string. Example: key=$(key) &fsize=$(fsize)
 *			<ul>
 * 				<li>
 * 					Custom replacement variables, format: $(x:variable), example: position=$(x:position)&message=$(x:message)
 * 				</li>
 * 				<li>
 * 					Special replacement variables
 * 					<table border="1px solid">
 * 						<tr>
 * 							<th>Parameter Value</th>
 * 							<th>Description</th>
 * 						</tr>
 * 						<tr>
 * 							<td>$(bucket)</td>
 * 							<td>Get the target space name for upload</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(key)</td>
 * 							<td>Get the resource name of the file saved in the space</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(fname)</td>
 * 							<td>Original filename of the upload</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(hash)</td>
 * 							<td>Unique resource identifier (hash(bucket:fname))</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(fsize)</td>
 * 							<td>Resource size in bytes</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(url)</td>
 * 							<td>Actual path to access the resource, URL-safe Base64 encoded, needs to be decoded when used</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(costTime)</td>
 * 							<td>Time consumed by this request</td>
 * 						</tr>
 * 						<tr>
 * 							<td>$(ip)</td>
 * 							<td>Source IP of this request</td>
 * 						</tr>
 * 					</table>
 * 				</li>
 * 			</ul>
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td>persistentOps</td>
 * 		<td>No</td>
 * 		<td>
 * 			List of processing instructions to be executed after successful upload. Each instruction is a standard string, multiple instructions are separated by ";".
 * 			<table border="1px solid">
 * 				<tr>
 * 					<th>Instruction</th>
 * 					<th>Description</th>
 * 				</tr>
 * 				<tr>
 * 					<td>avthumb/Format</td> 
 * 					<td>Format (required) - target video format (supports flv), example: avthumb/flv</td>
 * 				</tr>
 * 				<tr>
 * 					<td>vframe/<Format></td> 
 * 					<td>Format (required) - target image format, jpg, etc. Example: vframe/jpg</td>
 * 				</tr>
 * 				<tr>
 * 					<td>vframe/offset/<Second></td> 
 * 					<td>offset/<Second> (required) - specifies the moment of the video frame in seconds, example: vframe/offset/7</td>
 * 				</tr>
 * 				<tr>
 * 					<td>vframe/w/<Width></td> 
 * 					<td>w/<Width> (optional) - specifies the width of the captured image in px (if not specified, the default video width will be used)</td>
 * 				</tr>
 * 				<tr>
 * 					<td>vframe/h/<Height></td> 
 * 					<td>h/<Height> (optional) - specifies the height of the captured image in px (if not specified, the default video height will be used)</td>
 * 				</tr>
 * 			</table>
 *		</td>
 * 	</tr>
 * 	<tr>
 * 		<td>persistentNotifyUrl</td>
 * 		<td>No</td>
 * 		<td>URL to receive preprocessing result notifications (must be a public network URL that can properly respond to HTTP/1.1 200 OK). Note: When setting the persistenOps field, please complete the persistentNotifyUrl field setting, and the platform will notify you of the instruction processing results by calling the URL set in the persistentNotifyUrl field.</td>
 * 	</tr>
 * </table>
 * -->
 * @author zouhao
 * @version 1.0
 * @since 2014/02/14
 */
public class PutPolicy {

    /**
     * Specifies the target resource space (bucketName) and resource name (fileName)
     * Format: bucketName:fileName
     */
    private String scope;
    /**
     * Valid time in Long type, unit: milliseconds
     */
    private String deadline;
    /**
     * Custom data returned to the uploader after successful upload (this field is used with returnUrl).<br />
     * Returned content<br />
     * Format example: $(bucket)&$(fsize)&$(hash)&$(key)<br />
     */
    private String returnBody;
    /**
     * Specifies whether to overwrite files that already exist on the server<br />
     * 1-allow overwrite, 0-not allow
     */
    private int overwrite;
    /**
     * Limits the size of the uploaded file
     */
    private long fsizeLimit;
    /**
     * The URL where the browser performs a 303 redirect after successful web file upload
     */
    private String returnUrl;
    /**
     * Callback URL
     */
    private String callbackUrl;
    /**
     * Callback content<br />
     * Format example: <keyName>=(keyValue)&<keyName>=(keyValue)<br />
     * Must be in key-value format
     */
    private String callbackBody;
    /**
     * List of persistent operation instructions<br />
     * Convert to flv instruction: avthumb/flv/vb/1.25m<br />
     * Video screenshot instruction: vframe/jpg/offset/1<br />
     * Separated by semicolon ";"
     */
    private String persistentOps;
    /**
     * Persistent operation notification URL
     */
    private String persistentNotifyUrl;

    private String lastModifiedTime;
    private Integer instant;
    private String saveKey;
    private Long separate;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(String returnBody) {
        this.returnBody = returnBody;
    }

    public int getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(int overwrite) {
        this.overwrite = overwrite;
    }

    public long getFsizeLimit() {
        return fsizeLimit;
    }

    public void setFsizeLimit(long fsizeLimit) {
        this.fsizeLimit = fsizeLimit;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getPersistentOps() {
        return persistentOps;
    }

    public void setPersistentOps(String persistentOps) {
        this.persistentOps = persistentOps;
    }

    public String getPersistentNotifyUrl() {
        return persistentNotifyUrl;
    }

    public void setPersistentNotifyUrl(String persistentNotifyUrl) {
        this.persistentNotifyUrl = persistentNotifyUrl;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getInstant() {
        return instant;
    }

    public void setInstant(Integer instant) {
        this.instant = instant;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

    public Long getSeparate() {
        return separate;
    }

    public void setSeparate(Long separate) {
        this.separate = separate;
    }

    @Override
    public String toString() {
        return "PutPolicy{" +
                "scope='" + scope + '\'' +
                ", deadline='" + deadline + '\'' +
                ", returnBody='" + returnBody + '\'' +
                ", overwrite=" + overwrite +
                ", fsizeLimit=" + fsizeLimit +
                ", returnUrl='" + returnUrl + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", callbackBody='" + callbackBody + '\'' +
                ", persistentOps='" + persistentOps + '\'' +
                ", persistentNotifyUrl='" + persistentNotifyUrl + '\'' +
                ", instant='" + instant + '\'' +
                ", saveKey='" + saveKey + '\'' +
                ", separate='" + separate + '\'' +
                '}';
    }

}
