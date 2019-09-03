package pub.devrel.easypermissions.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import pub.devrel.easypermissions.dialogs.rationale.RationaleDialogFragment
import pub.devrel.easypermissions.helpers.base.PermissionsHelper
import pub.devrel.easypermissions.models.PermissionRequest

private const val TAG = "ActivityPH"

/**
 * Permissions helper for [Activity].
 */
internal class ActivityPermissionsHelper(
    host: Activity
): PermissionsHelper<Activity>(host) {

    override var context: Context? = host

    override fun directRequestPermissions(requestCode: Int, perms: List<String>) {
        ActivityCompat.requestPermissions(host, perms.toTypedArray(), requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(host, perm)
    }

    override fun showRequestPermissionRationale(permissionRequest: PermissionRequest) {
        val fm = host.fragmentManager

        // Check if fragment is already showing
        val fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG)
        if (fragment is RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.")
            return
        }

        RationaleDialogFragment
            .newInstance(permissionRequest)
            .showAllowingStateLoss(fm, RationaleDialogFragment.TAG)
    }
}