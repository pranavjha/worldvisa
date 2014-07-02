/******************************************************************************
 * Name : LookupConstants.java Author : Administrator Date : 15_May_2010,11:58:48 AM Description : Implementation for LookupConstants
 *****************************************************************************/
package com.worldvisa.business.infra.constants;

public interface LookupConstants {
    public interface ACTION_TYPE {
        public static final Long ACTION_TYPE   = 121L;
        public static final Long GeneralUpdate = 12103L;
        public static final Long Mailer        = 12101L;
        public static final Long SMS           = 12102L;
    }

    public interface ACTIVITY_TYPE {
        public static final Long ACTIVITY_TYPE     = 117L;
        public static final Long Collection        = 11705L;
        public static final Long FilesHandled      = 11706L;
        public static final Long Miscelleneous     = 11707L;
        public static final Long Phoneenquiries    = 11702L;
        public static final Long Registrations     = 11704L;
        public static final Long Tele_callingDone  = 11703L;
        public static final Long WalkInsCounselled = 11701L;
    }

    public interface BASE_LOCATION {
        public static final Long Bangalore     = 11901L;
        public static final Long BASE_LOCATION = 119L;
        public static final Long Chennai       = 11902L;
    }

    public interface CASE_SUBSTATUS {
        public static final Long CASE_SUBSTATUS          = 106L;
        public static final Long Collection              = 10611L;
        public static final Long FileNumberObtained      = 10603L;
        public static final Long FilesHandled            = 10612L;
        public static final Long Miscellaneous           = 10601L;
        public static final Long Phoneenquiries          = 10608L;
        public static final Long RequestAbandoned        = 10606L;
        public static final Long RequestCompleted        = 10605L;
        public static final Long RetainerAgreementSigned = 10602L;
        public static final Long Tele_calling            = 10609L;
        public static final Long VisaGrantLetterObtained = 10604L;
        public static final Long WalkInsCounselled       = 10607L;
    }

    public interface CLIENT_REG_STATUS {
        public static final Long CLIENT_REG_STATUS = 103L;
        public static final Long Registerd         = 10302L;
        public static final Long Unregisterd       = 10301L;
    }

    public interface COUNTRY_MASTER_LIST {
        public static final Long Australia           = 11801L;
        public static final Long Canada              = 11802L;
        public static final Long COUNTRY_MASTER_LIST = 118L;
        public static final Long Malaysia            = 11805L;
        public static final Long Singapore           = 11806L;
        public static final Long UnitedKingdom       = 11803L;
        public static final Long UnitedStates        = 11804L;
    }

    public interface COURSE {
        public static final Long BachelorDegree            = 10805L;
        public static final Long CertificateCourse         = 10801L;
        public static final Long COURSE                    = 108L;
        public static final Long Diploma1Year              = 10802L;
        public static final Long Diploma2Years             = 10803L;
        public static final Long Diploma3Years             = 10804L;
        public static final Long Masterdegree              = 10808L;
        public static final Long OneyearPostGraduateDegree = 10806L;
        public static final Long TwoyearPostGraduateDegree = 10807L;
    }

    public interface FAMILY_RELATIONS {
        public static final Long Daughter         = 12003L;
        public static final Long FAMILY_RELATIONS = 120L;
        public static final Long Son              = 12002L;
        public static final Long Spouse           = 12001L;
    }

    public interface INDICES {
        public static final Long FALSE = 0L;
        public static final Long TRUE  = 1L;
    }

    public interface LANGUAGE_PROFECIENCY {
        public static final Long Competent            = 11103L;
        public static final Long Fluent               = 11101L;
        public static final Long Good                 = 11102L;
        public static final Long LANGUAGE_PROFECIENCY = 111L;
        public static final Long None                 = 11104L;
    }

    public interface LANGUAGES {
        public static final Long English   = 11201L;
        public static final Long French    = 11202L;
        public static final Long LANGUAGES = 112L;
    }

    public interface MARITAL_STATUS {
        public static final Long Divorced       = 10703L;
        public static final Long MARITAL_STATUS = 107L;
        public static final Long Married        = 10702L;
        public static final Long Single         = 10701L;
    }

    public interface NOTIFICATION_STATUS {
        public static final Long Abandoned           = 10004L;
        public static final Long Completed           = 10003L;
        public static final Long Created             = 10001L;
        public static final Long NOTIFICATION_STATUS = 100L;
        public static final Long Rescheduled         = 10002L;
    }

    public interface OTHER_SERVICES {
        public static final Long OTHER_SERVICES                               = 122L;
        public static final Long ResumeDesign_InterviewPreparationorJobSearch = 12201L;
    }

    public interface PAYMENT_MODE {
        public static final Long Cash         = 11601L;
        public static final Long Cheque       = 11602L;
        public static final Long CreditCard   = 11605L;
        public static final Long DemandDraft  = 11603L;
        public static final Long Online       = 11604L;
        public static final Long PAYMENT_MODE = 116L;
    }

    public interface PAYMENT_OPTION {
        public static final Long CustomizedInstallmentPlan = 10504L;
        public static final Long OneTimePaymentPlan        = 10501L;
        public static final Long PAYMENT_OPTION            = 105L;
        public static final Long QuickInstallmentPlan      = 10502L;
        public static final Long StandardInstallmentPlan   = 10503L;
    }

    public interface RELATIONSHIP {
        public static final Long Aunt         = 10906L;
        public static final Long Brother      = 10909L;
        public static final Long Cousin       = 10907L;
        public static final Long Daughter     = 10910L;
        public static final Long Father       = 10902L;
        public static final Long GrandFather  = 10904L;
        public static final Long GrandMother  = 10903L;
        public static final Long Husband_Wife = 10912L;
        public static final Long Mother       = 10901L;
        public static final Long RELATIONSHIP = 109L;
        public static final Long Sister       = 10908L;
        public static final Long Son          = 10911L;
        public static final Long Uncle        = 10905L;
    }

    public interface REQUEST_MODE {
        public static final Long ClientWalkIn = 10203L;
        public static final Long Online       = 10201L;
        public static final Long REQUEST_MODE = 102L;
        public static final Long Telephone    = 10202L;
    }

    public interface REQUEST_SLA_SATUS {
        public static final Long Normal            = 10403L;
        public static final Long REQUEST_SLA_SATUS = 104L;
        public static final Long SLAAtRisk         = 10402L;
        public static final Long SLAExpired        = 10401L;
    }

    public interface REQUEST_TEST_SCORE {
        public static final Long GMAT               = 11304L;
        public static final Long GRE                = 11303L;
        public static final Long IELTS              = 11301L;
        public static final Long REQUEST_TEST_SCORE = 113L;
        public static final Long SAT                = 11305L;
        public static final Long TOEFL              = 11302L;
    }

    public interface REQUEST_TYPE {
        public static final Long BusinessVisa                       = 10112L;
        public static final Long DependantVisa                      = 10104L;
        public static final Long DomesticWorkerVisa                 = 10105L;
        public static final Long EPEC                               = 10108L;
        public static final Long Immigration                        = 10101L;
        public static final Long MigrationReviews                   = 10103L;
        public static final Long Others                             = 10114L;
        public static final Long OverseasEducation_AdmissionandVisa = 10109L;
        public static final Long OverseasEducation_VisaOnly         = 10110L;
        public static final Long PostStudyVisa                      = 10106L;
        public static final Long REQUEST_TYPE                       = 101L;
        public static final Long VisaReviews                        = 10111L;
        public static final Long VisitVisa                          = 10102L;
        public static final Long WorkHolidayPass                    = 10107L;
        public static final Long WorkPermit                         = 10113L;
    }

    public interface USER_FEEDBACK {
        public static final Long AdsinNewspapers = 11502L;
        public static final Long Friends         = 11504L;
        public static final Long Google          = 11503L;
        public static final Long TheWebsite      = 11501L;
        public static final Long USER_FEEDBACK   = 115L;
        public static final Long WalkIn          = 11505L;
    }

    public interface USER_ROLE {
        public static final Long Customer   = 11004L;
        public static final Long Employee   = 11003L;
        public static final Long Manager    = 11002L;
        public static final Long SuperAdmin = 11001L;
        public static final Long USER_ROLE  = 110L;
    }

    public interface YES_NO {
        public static final Long No     = 11401L;
        public static final Long Yes    = 11402L;
        public static final Long YES_NO = 114L;
    }
}
