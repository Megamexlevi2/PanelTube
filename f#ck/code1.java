AlertDialog.Builder aB = new AlertDialog.Builder(Znyxus1Activity.this);
aB.setTitle("Choose an app");
final List<String> l = new ArrayList<>();
final PackageManager pM = getPackageManager();
final List<ApplicationInfo> a = pM.getInstalledApplications(0);
for (ApplicationInfo app : a) {
    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
        String n = app.loadLabel(pM).toString();
        l.add(n);
    }
}
aB.setItems(l.toArray(new String[0]), new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface d, int w) {
        final String sA = l.get(w);
        AlertDialog.Builder dB = new AlertDialog.Builder(Znyxus1Activity.this);
        dB.setTitle("Choose a downscale factor");
        final String[] o = {"0.3", "0.35", "0.4", "0.45", "0.5", "0.55", "0.6", "0.65", "0.7", "0.75", "0.8", "0.85", "0.9"};
        dB.setItems(o, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int w) {
                String sD = o[w];
                String pN = null;
                for (ApplicationInfo app : a) {
                    if (app.loadLabel(pM).toString().equals(sA)) {
                        pN = app.packageName;
                    }
                }
                if (pN != null && !pN.isEmpty() && !sD.isEmpty()) {
                    String c = "device_config put game_overlay \"" + pN + "\" mode=2,downscaleFactor=" + sD + ",fps=60:mode=2,downscaleFactor=" + sD + ",fps=50";
                    try {
                        java.lang.Process pr = Runtime.getRuntime().exec(c);
                        pr.waitFor();
                        showMessage("Success");
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        showMessage("Error executing command");
                    }
                } else {
                    showMessage("No package name or downscale factor found");
                }
            }
        });
        dB.show();
    }
});
aB.show();