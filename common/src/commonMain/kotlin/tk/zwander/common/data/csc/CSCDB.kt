package tk.zwander.common.data.csc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.fluidsonic.country.Country
import io.fluidsonic.i18n.name

object CSCDB {
    // Most of these are from:
    // https://tsar3000.com/list-of-samsung-csc-codes-samsung-firmware-csc-codes/
    private val items = arrayOf(
        CSCItem("AFG", "AF"),
        CSCItem("TMC", arrayOf("DZ", "MK")),
        CSCItem("ALG", "DZ"),
        CSCItem("ALR", "DZ"),
        CSCItem("AVF", "AL", "Vodafone"),
        CSCItem("ANC", "AR"),
        CSCItem("ARO", "AR"),
        CSCItem("CTI", "AR", "Claro"),
        CSCItem("UFN", "AR", "Movistar"),
        CSCItem("PSN", "AR", "Personal"),
        CSCItem("ARU", "AW"),
        CSCItem("XSA", "AU"),
        CSCItem("OPP", "AU"),
        CSCItem("OPS", "AU", "Optus"),
        CSCItem("VAU", "AU", "Vodafone"),
        CSCItem("TEL", "AU", "Telus"),
        CSCItem("HUT", "AU", arrayOf("Three", "Vodafone")),
        CSCItem("ATO", "AT", "Open Austria"),
        CSCItem("AOM", "AT"),
        CSCItem("DRE", "AT", "3 Hutchinson"),
        CSCItem("MAX", "AT", "T-Mobile"),
        CSCItem("MOB", "AT", "A1"),
        CSCItem("MOK", "AT"),
        CSCItem("ONE", "AT", "Orange"),
        CSCItem("TRG", "AT", "Telering"),
        CSCItem("ARB", arrayOf("BH", "AE")),
        CSCItem("SEB", arrayOf("EE", "LV", "LT")),
        CSCItem("MTB", "BY"),
        CSCItem("VEL", "BY"),
        CSCItem("BSE", "BE"),
        CSCItem("BAE", "BE"),
        CSCItem("PRO", "BE", "Proximus"),
        CSCItem("XEB", "BE"),
        CSCItem("BNG", "BD"),
        CSCItem("TML", "BD"),
        CSCItem("ETR", "BD"),
        CSCItem("ERO", "BA"),
        CSCItem("BHO", "BA"),
        CSCItem("BHT", "BA", "BH Telecom"),
        CSCItem("TEB", "BA"),
        CSCItem("ZTO", "BR"),
        CSCItem("BTA", "BR"),
        CSCItem("BTM", "BR"),
        CSCItem("TMR", "BR"),
        CSCItem("ZTA", "BR", "Claro"),
        CSCItem("ZVV", "BR", "Vivo"),
        CSCItem("ZTM", "BR", "TIM"),
        CSCItem("BGL", "BG"),
        CSCItem("CMF", "BG"),
        CSCItem("GBL", "BG"),
        CSCItem("MTE", "BG"),
        CSCItem("MTL", "BG", "MTL"),
        CSCItem("OMX", "BG"),
        CSCItem("PLX", "BG"),
        CSCItem("VVT", "BG", "VVT"),
        CSCItem("CAM", "KH"),
        CSCItem("RCG", "KH"),
        CSCItem("BMC", "CA", "Bell"),
        CSCItem("RWC", "CA", "Rogers"),
        CSCItem("TLS", "CA", "Telus"),
        CSCItem("KDO", "CA", "Koodo"),
        CSCItem("CHO", "CL"),
        CSCItem("CHB", "CL"),
        CSCItem("CHE", "CL", "Entel PCS"),
        CSCItem("CHL", "CL", "Claro"),
        CSCItem("CHT", "CL", "Telefonica"),
        CSCItem("CHN", "CN"),
        CSCItem("CMC", "CN"),
        CSCItem("CUH", "CN"),
        CSCItem("INT", "CN"),
        CSCItem("M00", "CN"),
        CSCItem("TEC", "CN"),
        CSCItem("TIY", "CN"),
        CSCItem("COO", "CO"),
        CSCItem("CGU", arrayOf("CO", "GT"), "Tigo"),
        CSCItem("COB", "CO", "Movistar"),
        CSCItem("COL", "CO"),
        CSCItem("COM", "CO", "Comcel"),
        CSCItem("COE", "CO", "ETB"),
        CSCItem("ICE", "CR"),
        CSCItem("CRO", "HR", "T-Mobile"),
        CSCItem("TRA", "HR"),
        CSCItem("TWO", "HR", "TELE2"),
        CSCItem("VIP", "HR", "VIP-Net"),
        CSCItem("CYV", "CY", "Vodafone"),
        CSCItem("ETL", "CZ"),
        CSCItem("KBN", "CZ"),
        CSCItem("O2C", "CZ", "O2C"),
        CSCItem("OSK", "CZ"),
        CSCItem("TMZ", "CZ", "T-Mobile"),
        CSCItem("VDC", "CZ", "Vodafone"),
        CSCItem("XCS", "CZ"),
        CSCItem("XEZ", "CZ"),
        CSCItem("DTL", "DK"),
        CSCItem("CDR", "DO"),
        CSCItem("TDR", "DO"),
        CSCItem("CST", "DM"),
        CSCItem("DCN", "DM"),
        CSCItem("DOR", arrayOf("DM", "DO"), "Orange"),
        CSCItem("BBR", "EC"),
        CSCItem("EGY", "EG"),
        CSCItem("DGC", "SV"),
        CSCItem("TBS", "SV"),
        CSCItem("ELS", "FI"),
        CSCItem("SAU", "FI"),
        CSCItem("XEF", "FR"),
        CSCItem("AUC", "FR"),
        CSCItem("BOG", "FR", "Bouygues"),
        CSCItem("COR", "FR"),
        CSCItem("DIX", "FR"),
        CSCItem("FTM", "FR", "Orange"),
        CSCItem("NRJ", "FR"),
        CSCItem("OFR", "FR"),
        CSCItem("ORC", "FR"),
        CSCItem("ORF", "FR"),
        CSCItem("OXA", arrayOf("FR", "DE", "IT", "PL", "GB"), "Multi-CSC"),
        CSCItem("SFR", "FR", "SFR"),
        CSCItem("UNI", "FR"),
        CSCItem("VGF", "FR"),
        CSCItem("DBT", "DE"),
        CSCItem("DTM", "DE", "T-Mobile"),
        CSCItem("DUT", "DE"),
        CSCItem("EPL", "DE", "E-Plus"),
        CSCItem("MAN", "DE"),
        CSCItem("MBC", "DE"),
        CSCItem("VD2", "DE", "Vodafone"),
        CSCItem("VIA", "DE", "O2"),
        CSCItem("XEG", "DE", "1&1"),
        CSCItem("SPN", "GH"),
        CSCItem("ACR", "SA"),
        CSCItem("EUR", "GR"),
        CSCItem("AOC", "GR"),
        CSCItem("COS", "GR", "Cosmote"),
        CSCItem("CYO", "GR", "Cytamobile Vodafone"),
        CSCItem("GER", "GR"),
        CSCItem("OXX", arrayOf("GR", "PL", "PT", "RO", "RS", "ME", "SK", "ZA", "CH"), "Multi-CSC"),
        CSCItem("TGR", "GR"),
        CSCItem("VGR", "GR", "Vodafone"),
        CSCItem("ILO", arrayOf("GR", "IL"), arrayOf("HotMobile")),
        CSCItem("PCS", "GT"),
        CSCItem("TGY", "HK"),
        CSCItem("XEH", "HU"),
        CSCItem("PAN", "HU", "Telenor"),
        CSCItem("VDH", "HU", "Vodafone"),
        CSCItem("WST", "HU"),
        CSCItem("TMO", "HU"),
        CSCItem("TMH", "HU", "T-Mobile"),
        CSCItem("INU", "IN"),
        CSCItem("IND", "IN"),
        CSCItem("INA", "IN"),
        CSCItem("INS", "IN"),
        CSCItem("IMS", "IN"),
        CSCItem("REL", "IN"),
        CSCItem("AXI", "ID"),
        CSCItem("SAR", "ID"),
        CSCItem("XSE", "ID"),
        CSCItem("THR", "IR"),
        CSCItem("MID", arrayOf("IQ", "AE")),
        CSCItem("3IE", "IE", "Three"),
        CSCItem("EIR", "IE", "Eir"),
        CSCItem("VDI", "IE", "Vodafone"),
        CSCItem("CEL", "IL", "Cellcom"),
        CSCItem("PCL", "IL", "Pelephone"),
        CSCItem("PTR", "IL", arrayOf("Orange", "Partner")),
        CSCItem("ITV", "IT"),
        CSCItem("FWB", "IT"),
        CSCItem("GOM", "IT"),
        CSCItem("HUI", "IT", "H3G"),
        CSCItem("OMN", "IT", "Vodafone"),
        CSCItem("TIM", "IT", "TIM"),
        CSCItem("VOM", "IT"),
        CSCItem("WIN", "IT", "Wind"),
        CSCItem("XET", "IT"),
        CSCItem("IRS", "CI"),
        CSCItem("SIE", "CI"),
        CSCItem("JBS", "JM"),
        CSCItem("JCN", "JM"),
        CSCItem("JCW", "JM"),
        CSCItem("DCM", "JP"),
        CSCItem("SBM", "JP"),
        CSCItem("VFK", "JP"),
        CSCItem("LEV", "JO"),
        CSCItem("EST", "KZ"),
        CSCItem("KCL", "KZ"),
        CSCItem("KMB", "KZ"),
        CSCItem("KZK", "KZ"),
        CSCItem("OXE", arrayOf("KZ", "RU", "AE"), "Multi-CSC"),
        CSCItem("SKZ", "KZ"),
        CSCItem("KEN", "KE"),
        CSCItem("KEL", "KE"),
        CSCItem("AFR", "KE"),
        CSCItem("SKT", "KR"),
        CSCItem("MMC", "LY"),
        CSCItem("TLT", "LT"),
        CSCItem("LUX", "LU"),
        CSCItem("VTN", "MO"),
        CSCItem("CCM", "MY"),
        CSCItem("FME", "MY"),
        CSCItem("FMG", "MY"),
        CSCItem("MXS", "MY"),
        CSCItem("OLB", arrayOf("MY", "TH", "VN", "PH"), "Multi-CSC"),
        CSCItem("XME", "MY"),
        CSCItem("SEM", "MX"),
        CSCItem("TCE", "MX", "Telcel"),
        CSCItem("TMM", "MX", "Movistar"),
        CSCItem("UNE", "MX"),
        CSCItem("MPC", "MN"),
        CSCItem("FWD", "MA"),
        CSCItem("MAT", "MA", "MAT"),
        CSCItem("MED", "MA"),
        CSCItem("MWD", "MA", "MWD"),
        CSCItem("SNI", "MA"),
        CSCItem("WAN", "TW"),
        CSCItem("NPL", "NP"),
        CSCItem("PHN", "NL"),
        CSCItem("BEN", "NL"),
        CSCItem("KPN", "NL", "KPN"),
        CSCItem("MMO", "NL"),
        CSCItem("ONL", "NL"),
        CSCItem("QIC", "NL"),
        CSCItem("TFT", "NL"),
        CSCItem("TNL", "NL", "T-Mobile"),
        CSCItem("VDF", "NL", "Vodafone"),
        CSCItem("VDP", "NL"),
        CSCItem("XEN", "NL"),
        CSCItem("VNZ", "NZ"),
        CSCItem("ECT", "NG"),
        CSCItem("GCR", "NG"),
        CSCItem("MML", "NG"),
        CSCItem("NEE", arrayOf("DK", "FI", "IS", "NO", "SE", "GL")),
        CSCItem("TEN", "NO", "Telenor"),
        CSCItem("PAK", "PK"),
        CSCItem("WDC", "PK"),
        CSCItem("TPA", "PA"),
        CSCItem("BPC", "PA"),
        CSCItem("PCW", "PA", "Cable & Wireless"),
        CSCItem("PBS", "PA"),
        CSCItem("PEB", "PE"),
        CSCItem("PET", "PE"),
        CSCItem("FAM", "PH"),
        CSCItem("GLB", "PH", "Globe"),
        CSCItem("SMA", "PH", "Smart"),
        CSCItem("XTC", "PH", "Open Line"),
        CSCItem("XTE", "PH", "Sun Cellular"),
        CSCItem("ERA", "PL", "T-Mobile"),
        CSCItem("IDE", "PL", "Orange"),
        CSCItem("PLS", "PL", "PLUS"),
        CSCItem("PRT", "PL", "Play"),
        CSCItem("XEO", "PL"),
        CSCItem("OPT", "PT", "Optimus"),
        CSCItem("TCL", "PT", "Vodafone"),
        CSCItem("TMN", "PT", "TMN"),
        CSCItem("TPH", "PT", "TPH"),
        CSCItem("XEP", "PT"),
        CSCItem("CEN", "PR"),
        CSCItem("PCI", "PR"),
        CSCItem("TPR", "PR"),
        CSCItem("ROM", "RO"),
        CSCItem("CNX", "RO", "Vodafone"),
        CSCItem("COA", "RO", "Cosmote"),
        CSCItem("HAT", "RO"),
        CSCItem("ORO", "RO", "Orange"),
        CSCItem("AZC", "RU"),
        CSCItem("BLN", "RU"),
        CSCItem("EMT", "RU"),
        CSCItem("ERS", "RU"),
        CSCItem("GEO", "RU"),
        CSCItem("MTV", "RU"),
        CSCItem("SER", "RU"),
        CSCItem("SNT", "RU"),
        CSCItem("KSA", "SA"),
        CSCItem("JED", "SA"),
        CSCItem("DKR", "SN"),
        CSCItem("MDR", arrayOf("RS", "ME")),
        CSCItem("PNM", arrayOf("RS", "ME")),
        CSCItem("SMO", arrayOf("RS", "ME")),
        CSCItem("TOP", arrayOf("RS", "ME"), "VIP"),
        CSCItem("TSR", arrayOf("RS", "ME"), "Telekom"),
        CSCItem("MM1", "SG"),
        CSCItem("XSP", "SG"),
        CSCItem("SIN", "SG", "Singtel"),
        CSCItem("STG", "SG", "Starhub"),
        CSCItem("BGD", "SG"),
        CSCItem("XSO", "SG", "Singtel"),
        CSCItem("MOT", "SI", "Mobitel"),
        CSCItem("SIM", "SI", "Si.mobile"),
        CSCItem("ORX", "SK"),
        CSCItem("GTL", "SK"),
        CSCItem("IRD", "SK", "Orange"),
        CSCItem("ORS", "SK"),
        CSCItem("TMS", "SK"),
        CSCItem("XFA", "ZA"),
        CSCItem("XFE", "ZA"),
        CSCItem("XFC", "ZA"),
        CSCItem("XFM", "ZA"),
        CSCItem("XFV", "ZA", "Vodafone"),
        CSCItem("SEE", arrayOf("AL", "BA", "BG", "HR", "GR", "Kosovo", "ME", "MK", "RO", "RS", "TR", "MD", "SI")),
        CSCItem("SWA", arrayOf("BH", "CY", "IR", "IQ", "IL", "JO", "KW", "LB", "OM", "QA", "SA", "SY", "TR", "YE", "AE")),
        CSCItem("PHE", "ES"),
        CSCItem("FOP", "ES"),
        CSCItem("AMN", "ES", "Orange"),
        CSCItem("ATL", "ES", "Vodafone"),
        CSCItem("EUS", "ES"),
        CSCItem("XEC", "ES", "Movistar"),
        CSCItem("YOG", "ES", "Yoigo"),
        CSCItem("SLK", "LK"),
        CSCItem("BAU", "SE"),
        CSCItem("BCN", "SE"),
        CSCItem("BME", "SE"),
        CSCItem("BSG", "SE"),
        CSCItem("BTH", "SE"),
        CSCItem("COV", "SE"),
        CSCItem("HTS", "SE", "Tre"),
        CSCItem("SEN", "SE"),
        CSCItem("TET", "SE"),
        CSCItem("TLA", "SE"),
        CSCItem("TNO", "SE"),
        CSCItem("VDS", "SE"),
        CSCItem("XEE", "SE"),
        CSCItem("AUT", "CH"),
        CSCItem("MOZ", "CH"),
        CSCItem("ORG", "CH"),
        CSCItem("SUN", "CH"),
        CSCItem("SWC", "CH", "Swisscom"),
        CSCItem("BRI", "TW"),
        CSCItem("CWT", "TW"),
        CSCItem("TCC", "TW"),
        CSCItem("TCI", "TW"),
        CSCItem("TWM", "TW"),
        CSCItem("CAT", "TH"),
        CSCItem("THE", "TH"),
        CSCItem("THL", "TH"),
        CSCItem("THO", "TH"),
        CSCItem("THS", "TH"),
        CSCItem("LAO", "TH"),
        CSCItem("MYM", "TH"),
        CSCItem("SOL", "TZ"),
        CSCItem("EON", "TT"),
        CSCItem("TTT", "TT"),
        CSCItem("TUN", "TN"),
        CSCItem("ABD", "TN"),
        CSCItem("RNG", "TN"),
        CSCItem("TUR", "TR"),
        CSCItem("BAS", "TR"),
        CSCItem("KVK", "TR"),
        CSCItem("TLP", "TR"),
        CSCItem("TRC", "TR"),
        CSCItem("KVR", "UA"),
        CSCItem("SEK", "UA", "Kyivstar"),
        CSCItem("UMC", "UA"),
        CSCItem("ITO", "AE"),
        CSCItem("XSG", "AE"),
        CSCItem("BTU", "GB"),
        CSCItem("EVR", "GB", "EE"),
        CSCItem("BTC", "LY"),
        CSCItem("CPW", "GB", "Carphone Warehouse"),
        CSCItem("H3G", "GB", "Three"),
        CSCItem("O2U", "GB", "O2"),
        CSCItem("ORA", "GB", "Orange"),
        CSCItem("TMU", "GB", "T-Mobile"),
        CSCItem("TSC", "GB"),
        CSCItem("VIR", "GB"),
        CSCItem("VOD", "GB", "Vodafone"),
        CSCItem("XEU", arrayOf("GB", "IE")),
        CSCItem("ACG", "US", arrayOf("Nextech", "C-Spire")),
        CSCItem("ATT", "US", "AT&T"),
        CSCItem("BST", "US", "Boost Mobile"),
        CSCItem("CCT", "US", "Xfinity Mobile"),
        CSCItem("GCF", "US", "Global Certification Forum"),
        CSCItem("LRA", "US", "Bluegrass Cellular"),
        CSCItem("SPR", "US", "Sprint"),
        CSCItem("TFN", "US", "TracFone"),
        CSCItem("TMB", "US", "T-Mobile"),
        CSCItem("USC", "US", "US Cellular"),
        CSCItem("VMU", "US", "Virgin Mobile USA"),
        CSCItem("VZW", "US", "Verizon Wireless"),
        CSCItem("XAA", "US"),
        CSCItem("XAS", "US"),
        CSCItem("CSC", "UZ"),
        CSCItem("UZB", "UZ"),
        CSCItem("VMT", "VE", "Movistar"),
        CSCItem("DGT", "VE"),
        CSCItem("MVL", "VE"),
        CSCItem("DNA", "VN"),
        CSCItem("FPT", "VN"),
        CSCItem("PHU", "VN"),
        CSCItem("SPT", "VN"),
        CSCItem("TLC", "VN"),
        CSCItem("VTC", "VN"),
        CSCItem("VTL", "VN"),
        CSCItem("XEV", "VN"),
        CSCItem("XXV", "VN"),
        CSCItem("XAU", "US", "T-Mobile"),
        CSCItem("TMK", "US", "MetroPCS"),
        CSCItem("ANP", "IE"),
        CSCItem("TSI", "IE"),
        CSCItem("O2I", "IE", "O2"),
        CSCItem("MET", "IE", "Meteor"),
        CSCItem("TTR", "AT"),
        CSCItem("PHB", "BE"),
        CSCItem("BVO", "BO"),
        CSCItem("BVT", "BO"),
        CSCItem("BVV", "BO"),
        CSCItem("ZTR", "BR", "Oi"),
        CSCItem("XAC", "CA"),
        CSCItem("CHR", "CA", "Chatr"),
        CSCItem("ESK", "CA", "EastLink"),
        CSCItem("FMC", "CA", "Fido"),
        CSCItem("GLW", "CA", "Globalive Wind"),
        CSCItem("BWA", "CA", "SaskTel"),
        CSCItem("VTR", "CA", "Vidéotron"),
        CSCItem("VMC", "CA", "Virgin"),
        CSCItem("CAU", arrayOf("AM", "GE", "AZ", "RU")),
        CSCItem("CRC", "CL"),
        CSCItem("CHX", "CL", "Nextel"),
        CSCItem("CHV", "CL", "VTR"),
        CSCItem("CHM", "CN", "China Mobile"),
        CSCItem("CTC", "CN", "China Telecom"),
        CSCItem("CHC", "CN"),
        CSCItem("CRG", "HR"),
        CSCItem("DHR", "HR", "Bonbon"),
        CSCItem("TDC", "DK"),
        CSCItem("DOO", "DO"),
        CSCItem("ALE", "EC"),
        CSCItem("EBE", "EC"),
        CSCItem("ECO", "EC"),
        CSCItem("ETE", "SV"),
        CSCItem("VFJ", "FJ", "Vodafone"),
        CSCItem("FTB", "FR"),
        CSCItem("ORN", "FR"),
        CSCItem("DDE", "DE", "Congstar"),
        CSCItem("PGU", "GT"),
        CSCItem("TGU", "GT"),
        CSCItem("CTE", "HN"),
        CSCItem("XID", "ID"),
        CSCItem("CWW", "JM"),
        CSCItem("JDI", "JM"),
        CSCItem("KTC", "KR", "KT Corporation"),
        CSCItem("LUC", "KR", "LG Uplus"),
        CSCItem("SKC", "KR", "SK Telecom"),
        CSCItem("LYS", "LY"),
        CSCItem("VIM", "MK"),
        CSCItem("MBM", "MK", "T-Mobile"),
        CSCItem("MRU", "MU"),
        CSCItem("IUS", "MX"),
        CSCItem("BAT", "MX"),
        CSCItem("MXO", "MX"),
        CSCItem("TMT", "ME"),
        CSCItem("DNL", "NL", "Ben NL"),
        CSCItem("TPD", "NL"),
        CSCItem("NZC", "NZ"),
        CSCItem("TNZ", "NZ"),
        CSCItem("VNZ", "NZ", "Vodafone"),
        CSCItem("CPA", "PA", "Claro"),
        CSCItem("PNG", "PG"),
        CSCItem("CTP", "PY", "Claro"),
        CSCItem("PSP", "PY", "Personal"),
        CSCItem("TGP", "PY", "Tigo"),
        CSCItem("PNT", "PE", "Nextel"),
        CSCItem("SAM", "PE", "SAM"),
        CSCItem("PVT", "PE", "Viettel"),
        CSCItem("DPL", "PL", "Heyah"),
        CSCItem("TPL", "PL", "T-Mobile"),
        CSCItem("MEO", "PT"),
        CSCItem("PCT", "PR"),
        CSCItem("WTL", "SA"),
        CSCItem("XFU", "SA", "STC"),
        CSCItem("MSR", arrayOf("RS", "ME"), "Telenor"),
        CSCItem("STH", "SG", "StarHub"),
        CSCItem("SIO", "SK"),
        CSCItem("CRM", arrayOf("AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PY", "PE", "SR", "UY", "VE"), "Movistar"),
        CSCItem("NBS", arrayOf("AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PY", "PE", "SR", "UY", "VE")),
        CSCItem("AMO", "ES", "Orange"),
        CSCItem("XSS", "AE"),
        CSCItem("XAR", "US", "Cellular South"),
        CSCItem("AIO", "US", "Cricket Wireless"),
        CSCItem("CHA", "US", "Spectrum Mobile"),
        CSCItem("UFU", "UY"),
        CSCItem("UPO", "UY"),
        CSCItem("UYO", "UY"),
        CSCItem("CTU", "UY", "Claro"),
        CSCItem("CAC", "UZ"),
        CSCItem("MTZ", "ZM", "MTN Zambia"),
    ).sortedBy { it.code }

    fun getAll(): List<CSCItem> {
        return items.toList()
    }

    fun findForCountryQuery(query: String): List<CSCItem> {
        if (query.isBlank()) return items.toList()

        return items.filter { item -> item.countries.any {
            getCountryName(it).contains(query, true)
        } }
    }

    fun findForCscQuery(query: String): List<CSCItem> {
        if (query.isBlank()) return items.toList()

        return items.filter { it.code.contains(query, true) }
    }

    fun findForCarrierQuery(query: String): List<CSCItem> {
        if (query.isBlank()) return items.toList()

        return items.filter { item -> item.carriers != null
                && item.carriers.any { it.contains(query, true) } }
    }

    fun findForGeneralQuery(query: String): List<CSCItem> {
        if (query.isBlank()) return items.toList()

        return items.filter { item ->
            item.countries.any { getCountryName(it).contains(query, true) }
                    || item.code.contains(query, true)
                    || (item.carriers != null
                        && item.carriers.any { it.contains(query, true) })
        }
    }

    @Composable
    fun rememberFilteredItems(query: String, sortBy: SortBy): List<CSCItem> {
        var items by remember {
            mutableStateOf<List<CSCItem>>(listOf())
        }

        LaunchedEffect(query, sortBy) {
            items = findForGeneralQuery(query).run {
                if (sortBy.ascending) {
                    sortedBy { sortBy.sortKey(it) }
                } else {
                    sortedByDescending { sortBy.sortKey(it) }
                }
            }
        }

        return items
    }

    fun getCountryName(code: String): String {
        return try {
            Country.forCode(code).name
        } catch (e: Exception) {
            code
        }
    }

    sealed class SortBy(val ascending: Boolean) {
        abstract fun sortKey(item: CSCItem): String

        class Code(ascending: Boolean) : SortBy(ascending) {
            override fun sortKey(item: CSCItem): String {
                return item.code
            }
        }

        class Country(ascending: Boolean) : SortBy(ascending) {
            override fun sortKey(item: CSCItem): String {
                return item.countries.run {
                    if (ascending) {
                        minOf { it }
                    } else {
                        maxOf { it }
                    }
                }
            }
        }

        class Carrier(ascending: Boolean) : SortBy(ascending) {
            override fun sortKey(item: CSCItem): String {
                return item.carriers?.run {
                    if (ascending) {
                        minOf { it }
                    } else {
                        maxOf { it }
                    }
                } ?: ""
            }
        }
    }
}